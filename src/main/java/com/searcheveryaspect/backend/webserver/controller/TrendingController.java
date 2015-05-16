package com.searcheveryaspect.backend.webserver.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.database.read.DatabaseReader;
import com.searcheveryaspect.backend.database.read.TrendingRequest;
import com.searcheveryaspect.backend.shared.Category;
import com.searcheveryaspect.backend.webserver.TrendingSearchResponse;

import org.joda.time.DateTime;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * TrendingController handles search requests for the top n trending topics for motions.
 * It only offers reads.
 */
public class TrendingController extends ReadOnlyController {
  private final DatabaseReader<TrendingRequest, TrendingSearchResponse> reader;

  public TrendingController(DatabaseReader<TrendingRequest, TrendingSearchResponse> reader) {
    this.reader = checkNotNull(reader);
  }

  /**
   * Implements the read method for top trending categories.
   * 
   * @param request
   * @param response
   * @return
   */
  public TrendingSearchResponse read(Request request, Response response) {
    try {
      return reader.read(parseRequest(request));
    } catch (Exception e) {
      e.printStackTrace();
      response.setException(e);
      return null;
    }
  }

  /**
   * @param request
   * @return
   */
  private TrendingRequest parseRequest(Request request) {
    final int top = Integer.parseInt(request.getHeader("quantity"));
    if (top < 1 || top > Category.values().length) {
      throw new IllegalArgumentException("Can only create a request with between 1 and "
          + Category.values().length + " categories");
    }
    return new TrendingRequest(DateTime.now(), top);
  }
}
