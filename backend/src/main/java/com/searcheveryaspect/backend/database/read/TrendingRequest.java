package com.searcheveryaspect.backend.database.read;

import org.joda.time.DateTime;

/**
 * Request for the top n trending categories.
 */
public class TrendingRequest {
  // The number of top trending categories to return.
  private final int top;
  private final DateTime ts;

  public TrendingRequest(DateTime ts, int top) {
    this.top = top;
    this.ts = ts;
  }

  public int getTop() {
    return top;
  }

  public DateTime getTs() {
    return ts;
  }


}
