package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.searcheveryaspect.backend.database.read.QuerierUtil.categorySearch;

import com.google.common.collect.ImmutableList;

import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.webserver.PartyData;
import com.searcheveryaspect.backend.webserver.SearchResponse;
import com.searcheveryaspect.backend.webserver.TrendingSearchResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TrendingQuerier implements the DatabaseReader interface and performs database searches
 * for the top n trending categories for the last month.
 */
public final class TrendingQuerier implements
    DatabaseReader<TrendingRequest, TrendingSearchResponse> {
  private final Client client;
  // Within a trending categories, how many parties should be labeled interesting.
  private static final int INTERESTING_PARTIES = 3;
  // Number of months that should be included in the response.
  private static final int MONTHS = 3;
  private static int DAY = 1;
  private static int HOUR = 0;
  private static int MINUTE = 0;

  private TrendingQuerier(Client client) {
    this.client = checkNotNull(client);
  }

  /**
   * Use to get a new TrendingQuerier. TrendingQuerier should only be used through the
   * DatabaseReader interface.
   * 
   * @param client
   * @return
   */
  public static DatabaseReader<TrendingRequest, TrendingSearchResponse> newReader(Client client) {
    return new TrendingQuerier(client);
  }

  public TrendingSearchResponse read(TrendingRequest input) {
    Interval lastMonth;
    Interval lastYear;
    List<Interval> responseIntervals;
    ImmutableList<String> labels;

    // Set up the intervals.
    responseIntervals = responseIntervals(input.getTs(), MONTHS);
    lastMonth = responseIntervals.get(responseIntervals.size() - 1);
    DateTime end = responseIntervals.get(responseIntervals.size() - 1).getEnd();
    lastYear = new Interval(end.minusYears(1), end);

    // Create response labels
    List<String> temp = new ArrayList<>();
    DateTimeFormatter formater = DateTimeFormat.forPattern("yyyy-MM-dd");
    for (Interval interval : responseIntervals) {
      temp.add(formater.print(interval.getStart()));
    }
    labels = ImmutableList.copyOf(temp);


    Set<Category> trendingCategories = getTopCategories(lastYear, lastMonth, input.getTop());
    return getTrendingSearchResponse(trendingCategories, responseIntervals, labels);
  }

  /**
   * Creates a set of intervals to use for the response and search. Each interval is one
   * whole month. The number of months is decided by quantity. The last interval will be
   * the month before the month specified in parameter today.
   * 
   * @param today the start of today's month is the end of the last interval
   * @param quantity number of months in the list
   * @return
   */
  private List<Interval> responseIntervals(DateTime today, int quantity) {
    // end is the start of the current month.
    DateTime end = new DateTime(today.getYear(), today.getMonthOfYear(), DAY, HOUR, MINUTE);
    List<Interval> responseIntervals = new ArrayList<>();
    // Create the lastMonth
    Interval lastMonth = new Interval(end.minusMonths(1), end);
    // Add the last month of the interval.
    responseIntervals.add(lastMonth);
    // Adds new intervals for the previous months, last month is already added.
    for (int i = 1; i < quantity; i++) {
      responseIntervals.add(new Interval(responseIntervals.get(i - 1).getStart().minusMonths(1),
          responseIntervals.get(i - 1).getStart()));
      // i == ri.Size()
    }
    Collections.reverse(responseIntervals);

    return responseIntervals;
  }

  private TrendingSearchResponse getTrendingSearchResponse(Set<Category> categories,
      List<Interval> intervals, ImmutableList<String> labels) {
    List<SearchResponse> responses = new ArrayList<>();
    for (Category category : categories) {
      responses.add(categorySearch(client, category, intervals, labels));
    }
    List<SearchResponse> interestingResponses = new ArrayList<>();
    for (SearchResponse r : responses) {
      interestingResponses.add(makeInteresting(r));
    }
    return new TrendingSearchResponse(ImmutableList.copyOf(interestingResponses));
  }

  private SearchResponse makeInteresting(SearchResponse r) {
    List<PartyData> partyData = new ArrayList<>();
    partyData.addAll(r.getDatasets());
    Collections.sort(partyData);
    List<PartyData> labeledPartyData = new ArrayList<>();
    for (int i = 0; i < partyData.size(); i++) {
      if (i < INTERESTING_PARTIES) {
        labeledPartyData.add(partyData.get(i).toBuilder().isInteresting(true).build());
      } else {
        labeledPartyData.add(partyData.get(i).toBuilder().isInteresting(false).build());
      }
    }
    return r.toBuilder().datasets(ImmutableList.copyOf(labeledPartyData)).build();
  }

  private Set<Category> getTopCategories(Interval lastYear, Interval lastMonth, int n) {
    List<WeightedCategory> weighted = new ArrayList<>();
    for (Category category : Category.values()) {
      // Last months hits divided by last 12 months hits gives the interesting raiting.
      long quota = numberOfHits(category, lastMonth);
      if (numberOfHits(category, lastYear) > 0) {
        quota = quota / numberOfHits(category, lastYear);
      }
      weighted.add(new WeightedCategory(category, quota));
    }
    Collections.sort(weighted);
    Set<Category> trending = new HashSet<>();
    // Get the top N categories.
    for (int i = 0; i < n; i++) {
      trending.add((weighted.get(i).getCategory()));
    }
    return trending;
  }

  private long numberOfHits(Category category, Interval interval) {
    org.elasticsearch.action.search.SearchResponse response =
        client
            .prepareSearch("motions")
            .setTypes("motion")
            .setQuery(QueryBuilders.matchQuery("category", category.toString()))
            .setPostFilter(
                FilterBuilders.rangeFilter("publishedTimestamp").from(interval.getStartMillis())
                    .to(interval.getEndMillis())).execute().actionGet();
    return response.getHits().totalHits();
  }

  private class WeightedCategory implements Comparable<WeightedCategory> {
    private final long weight;
    private final Category category;

    WeightedCategory(Category category, long weight) {
      this.weight = weight;
      this.category = category;
    }

    public long getWeight() {
      return weight;
    }

    public Category getCategory() {
      return category;
    }

    @Override
    public int compareTo(WeightedCategory o) {
      if (weight < o.weight) {
        return -1;
      } else if (weight > o.weight) {
        return 1;
      }
      return 0;
    }
  }
}
