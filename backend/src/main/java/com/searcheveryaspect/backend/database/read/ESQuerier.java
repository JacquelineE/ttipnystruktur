/**
 * 
 */
package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.searcheveryaspect.backend.database.read.QuerierUtil.categorySearch;

import com.google.common.collect.ImmutableList;

import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.elasticsearch.client.Client;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Communicates with ES depending on what kind of information the ESRequest object
 * contains.
 * 
 * @author Mitra
 * 
 */
public final class ESQuerier implements DatabaseReader<ESRequest, SearchResponse> {
	private final Client client;

	private ESQuerier(Client client) {
		this.client = checkNotNull(client);
	};
	
	  /**
	   * Use to get a new ESQuerier. ESQuerier should only be used through the
	   * DatabaseReader interface.
	   * 
	   * @param client
	   * @return new ESQuerier
	   */
	  public static DatabaseReader<ESRequest, SearchResponse> newReader(Client client) {
	    return new ESQuerier(client);
	  }

	/**
	 * Queries the database as specified by the ESRequest and returns a SearchResponse.
	 */
	public SearchResponse read(ESRequest req) {
		List<Interval> intervals = new ArrayList<Interval>();
		if(req.getPeriod().equals("year")) {
			intervals = yearIntervals(req.getInterval());
		}
		else if(req.getPeriod().equals("month")) {
			intervals = monthIntervals(req.getInterval());
		} else {
			throw new IllegalArgumentException("The period must be month or year!");
		}
		
		// Create response labels
	    List<String> temp = new ArrayList<>();
	    DateTimeFormatter formater = DateTimeFormat.forPattern("yyyy-MM-dd");
	    for (Interval interval : intervals) {
	      temp.add(formater.print(interval.getStart()));
	    }
	    ImmutableList<String> labels = ImmutableList.copyOf(temp);
	    
		//Search for JSONs that match req.category and req.Interval
		SearchResponse response = categorySearch(client, req.getCategory(), intervals, labels);		
		return response;
	}
	
	/**
	 * Create an interval over e period of months
	 * @param interval The interval to parse
	 * @return A list of the months in the interval
	 */
	private List<Interval> monthIntervals(Interval interval) {
		List<Interval> intervals = new ArrayList<Interval>();
		DateTime start = new DateTime(interval.getEnd().getYear(), interval.getEnd().getMonthOfYear(), 1, 0, 0);
		//If start and end are in the same month
		if(start.getMillis() < interval.getStartMillis()) {
			intervals.add(interval);
			return intervals;
		}
		intervals.add(new Interval(start, interval.getEnd()));
		// while-loop compares startddate of the complete interval with the start date of the last element in intervals
		while(interval.getStartMillis() < intervals.get(intervals.size()-1).getStart().minusMonths(1).getMillis()) {
			//The last interval in intervals is following month
			Interval followingM =  intervals.get(intervals.size()-1);
			intervals.add(new Interval(followingM.getStart().minusMonths(1), followingM.getStart()));
		}
		//Add the first month
		intervals.add(new Interval(interval.getStart(), intervals.get(intervals.size()-1).getStart()));
		Collections.reverse(intervals);
		return intervals;
	}
	
	/**
	 * Create an interval over a period of years
	 * @param interval The interval to parse
	 * @return A list of the years in the interval
	 */
	private List<Interval> yearIntervals(Interval interval) {
		List<Interval> intervals = new ArrayList<Interval>();
		DateTime start = new DateTime(interval.getEnd().getYear(), 1, 1, 0, 0);
		//If start and end are in the same year
		if(start.getMillis() < interval.getStartMillis()) {
			intervals.add(interval);
			return intervals;
		}
		intervals.add(new Interval(start, interval.getEnd()));
		// while-loop compares startddate of the complete interval with the start date of the last element in intervals
		while(interval.getStartMillis() < intervals.get(intervals.size()-1).getStart().minusYears(1).getMillis()) {
			//The last interval in intervals is following year
			Interval followingY =  intervals.get(intervals.size()-1);
			intervals.add(new Interval(followingY.getStart().minusYears(1), followingY.getStart()));
		}
		//Add the first year
		intervals.add(new Interval(interval.getStart(), intervals.get(intervals.size()-1).getStart()));
		Collections.reverse(intervals);
		return intervals;
	}
}
