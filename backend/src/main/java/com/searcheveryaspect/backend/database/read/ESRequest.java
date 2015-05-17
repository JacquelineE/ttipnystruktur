/**
 * 
 */
package com.searcheveryaspect.backend.database.read;

import java.util.Objects;

import org.joda.time.Interval;

import com.google.common.base.MoreObjects;
import com.searcheveryaspect.backend.shared.Category;

/**
 * Contains information about what the ESQuerier will search for. Get search info from
 * frontend. Creates a querier depending on information in fields and querier returns the
 * result. Then creates a Response depending on the search result.
 * 
 * @author Mitra
 * 
 */
public class ESRequest {
  private final Interval interval;
  private final String period;
  private final Category category;


  public ESRequest(Interval interval, Category category, String period) {
    this.interval = interval;
    this.category = category;
    this.period = period;
  }

  /**
  public ESRequest(List<Interval> intervals, Category category, String period) {
    this.intervals = intervals;
    this.category = category;
    this.party = new ImmutableList.Builder();
    this.period = period;
  }
  */

  public Interval getInterval() {
    return interval;
  }

  public Category getCategory() {
    return category;
  }

  public String getPeriod() {
    return period;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ESRequest) {
      ESRequest that = (ESRequest) o;
      return interval.equals(that.interval)
          && period.equals(that.period) && category.equals(that.category);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(interval, period, category);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("interval", interval)
        .add("period", period).add("category", category).toString();
  }
}
