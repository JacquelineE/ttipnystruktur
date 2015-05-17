package com.searcheveryaspect.backend.database.read;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import com.searcheveryaspect.backend.database.update.ESDocument;
import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.shared.Party;
import com.searcheveryaspect.backend.webserver.Document;
import com.searcheveryaspect.backend.webserver.Entry;
import com.searcheveryaspect.backend.webserver.PartyData;
import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */
class QuerierUtil {
  private static final int MILLIS_TO_SEC = 1000;
  private static final int HITS_SIZE = 5000;

  /**
   * Returns a SearchResponse for the specified category, interval and labels containing
   * all parties.
   * 
   * @param category
   * @param intervals
   * @param labels
   * @return
   */
  static SearchResponse categorySearch(Client client, Category category, List<Interval> intervals,
      ImmutableList<String> labels) {
    List<PartyData> parties = new ArrayList<>();
    for (Party party : Party.values()) {
      parties.add(processParty(client, category, party, intervals));
    }
    return SearchResponse.newSearchResponse().category(category.toString()).labels(labels)
        .datasets(ImmutableList.copyOf(parties)).build();
  }

  /**
   * Searches and aggregates the category and party data for the specified intervals.
   * 
   * @param category
   * @param party
   * @param intervals
   * @return
   */
  private static PartyData processParty(Client client, Category category, Party party,
      List<Interval> intervals) {
    List<Entry> entries = new ArrayList<>();
    for (Interval i : intervals) {
      entries.add(processInterval(client, category, party, i));
    }
    return new PartyData(party.toString(), ImmutableList.copyOf(entries), false);
  }

  /**
   * Queries the client for all documents with the specified category and party that was
   * published within the interval. If there are no hits it returns an Entry with an
   * initialised list and data of 0.
   * 
   * @param category
   * @param party
   * @param interval
   * @return
   */
  private static Entry processInterval(Client client, Category category, Party party,
      Interval interval) {
    org.elasticsearch.action.search.SearchResponse response =
        client
            .prepareSearch("motions")
            .setTypes("motion")
            .setQuery(
                QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("category", category.toString()))
                    .must(QueryBuilders.matchQuery("party", party.toString())))
            .setPostFilter(
                FilterBuilders.rangeFilter("publishedTimestamp")
                    .from(interval.getStartMillis() / MILLIS_TO_SEC)
                    .to(interval.getEndMillis() / MILLIS_TO_SEC)).setSize(HITS_SIZE).execute()
            .actionGet();

    // Get the hits as a response
    Iterator<SearchHit> iterator = response.getHits().iterator();
    List<Document> list = new ArrayList<>();
    Gson gson = new Gson();

    while (iterator.hasNext()) {
      list.add(new Document(gson.fromJson(iterator.next().getSourceAsString(), ESDocument.class)));
    }

    return new Entry(list.size(), ImmutableList.copyOf(list));
  }
}
