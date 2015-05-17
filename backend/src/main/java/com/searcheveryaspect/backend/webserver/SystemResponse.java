package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;

import org.joda.time.DateTime;

import java.util.Objects;

/**
 * SystemResponse is the response object for system read requests.
 */
public class SystemResponse {
  private final String databaseUpdated;

  public SystemResponse(DateTime databaseUpdated) {
    this.databaseUpdated = databaseUpdated.toString("yyyy-MM-dd");
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SystemResponse) {
      SystemResponse that = (SystemResponse) o;
      return databaseUpdated.equals(that.databaseUpdated);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(databaseUpdated);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("databaseUpdated", databaseUpdated).toString();
  }
}
