package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Objects;

/**
 * 
 */
public class TrendingSearchResponse {
  private final ImmutableList<SearchResponse> topTrends;

  public TrendingSearchResponse(ImmutableList<SearchResponse> topTrends) {
    this.topTrends = topTrends;
  }

  public ImmutableList<SearchResponse> getTopTrends() {
    return topTrends;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof TrendingSearchResponse) {
      TrendingSearchResponse that = (TrendingSearchResponse) o;
      return topTrends.equals(that.topTrends);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(topTrends);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("topTrends", topTrends).toString();
  }
}
