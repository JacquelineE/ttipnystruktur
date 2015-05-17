package com.searcheveryaspect.backend.webserver.controller;


import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;

import com.searcheveryaspect.backend.database.read.DatabaseReader;
import com.searcheveryaspect.backend.database.read.ESRequest;
import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.webserver.SearchResponse;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * MotionsController handles search requests for existing motions for a specified period
 * and category. It only offers reads.
 */
public class MotionsController extends ReadOnlyController {
  private final DatabaseReader<ESRequest, SearchResponse> reader;

  /**
   * Returns a new MotionsController. The DatabaseReader serves as a connection to the
   * database and is needed to process read requests.
   */
  public MotionsController(DatabaseReader<ESRequest, SearchResponse> reader) {
    this.reader = checkNotNull(reader);
  }

  /**
   * Implements the Read method for the motions search.
   * 
   * @param request
   * @param response
   * @return
   */
  public SearchResponse read(Request request, Response response) {
    recordForDebug(request, response);
    try {
      return reader.read(parseRequest(request));
    } catch (Exception e) {
      response.setException(e);
      return null;
    }
  }

  private ESRequest parseRequest(Request request) throws IllegalArgumentException {
    final String fromDate = (String) request.getHeader("from_date");
    final String toDate = (String) request.getHeader("to_date");
    final String category = (String) request.getHeader("category");
    String period = (String) request.getHeader("interval").toLowerCase();
    if (fromDate == null || toDate == null || category == null || category == null) {
      throw new IllegalArgumentException("All request parameters aren't specified");
    }

    DateTime start = DateTime.parse(fromDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
    DateTime end = DateTime.parse(toDate, DateTimeFormat.forPattern("yyyy-MM-dd"));

    if (start.compareTo(end) > 0) {
      throw new IllegalArgumentException("Request parameter from is before parameter to");
    }
    Interval interval = new Interval(start, end);

    if (!period.equals("month") && !period.equals("year") && !period.equals("automatic")) {
      throw new IllegalArgumentException(
          "Illegal request parameter period, only month, year and automatic is allowed.");
    }
    // If the period is 2 years or longer then yearly periods is used, otehrwise monthly
    // periods.
    if (period.equals("automatic")) {
      if (interval.toPeriod().getYears() >= 2) {
        period = "year";
      } else {
        period = "month";
      }
    }

    Category categoryEnum = Category.valueOf(category.toUpperCase());
    ESRequest esrequest = new ESRequest(interval, categoryEnum, period);
    return esrequest;
  }

  /**
   * A small debugging method that prints out incoming requests to stdout.
   * 
   * @param request
   * @param response
   */
  void recordForDebug(Request request, Response response) {
    if (request.getHeader("debug") != null) {
      try {
        boolean debug = Boolean.parseBoolean(request.getHeader("debug"));

        if (debug) {

          System.out.println(MoreObjects.toStringHelper(Request.class)
              .add("path", request.getPath()).toString()
              + " " + response);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
