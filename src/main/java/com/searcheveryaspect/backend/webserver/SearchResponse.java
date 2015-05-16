package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Objects;


/**
 * SearchAggregateResponse represent the result of a search for documents within a
 * specific period of time.
 */
public class SearchResponse {
  private final String category;
  private final ImmutableList<String> labels;
  private final ImmutableList<PartyData> datasets;

  SearchResponse(Builder builder) {
    this.category = builder.category;
    this.labels = builder.labels;
    this.datasets = builder.datasets;
  }

  public String getCategory() {
    return category;
  }

  public ImmutableList<String> getLabels() {
    return labels;
  }

  public ImmutableList<PartyData> getDatasets() {
    return datasets;
  }

  /**
   * The number of documents included in this SearchResponse.
   * 
   * @return sum of all hits
   */
  public int getSumHits() {
    int sum = 0;
    for (PartyData d : datasets) {
      sum += d.getSumHits();
    }
    return sum;
  }


  public static class Builder {
    private String category;
    private ImmutableList<String> labels;
    private ImmutableList<PartyData> datasets;

    public Builder category(String s) {
      category = s;
      return this;
    }

    public Builder labels(ImmutableList<String> l) {
      labels = l;
      return this;
    }

    public Builder datasets(ImmutableList<PartyData> l) {
      datasets = l;
      return this;
    }

    public SearchResponse build() {
      return new SearchResponse(this);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SearchResponse) {
      SearchResponse that = (SearchResponse) o;
      return category.equals(that.category) && labels.equals(that.labels)
          && datasets.equals(that.datasets);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, labels, datasets);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("category", category).add("labels", labels)
        .add("datasets", datasets).toString();
  }

  public static Builder newSearchResponse() {
    return new Builder();
  }

  public Builder toBuilder() {
    Builder builder = new Builder();
    builder.category = category;
    builder.datasets = datasets;
    builder.labels = labels;
    return builder;
  }
}
