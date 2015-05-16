package com.searcheveryaspect.backend.database.read;

import static com.google.common.base.Preconditions.checkNotNull;

import com.searcheveryaspect.backend.webserver.SystemResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.joda.time.DateTime;

/**
 * SystemQuerier performs the necessary database reads for system meta information
 * requests.
 */
public class SystemQuerier implements DatabaseReader<SystemRequest, SystemResponse> {
  private final Client client;

  private SystemQuerier(Client client) {
    this.client = checkNotNull(client);
  }

  public static DatabaseReader<SystemRequest, SystemResponse> newReader(Client client) {
    return new SystemQuerier(client);
  }

  public SystemResponse read(SystemRequest input) {
    return new SystemResponse(getLastUpdated());
  }

  private DateTime getLastUpdated() {
    final String lastUpdated = "update";
    MetricsAggregationBuilder aggregation =
        AggregationBuilders.max(lastUpdated).field("fetchedTimestamp");
    SearchResponse response =
        client.prepareSearch("motions").setTypes("motion").addAggregation(aggregation).execute()
            .actionGet();
    Max agg = response.getAggregations().get(lastUpdated);
    // TODO: Max aggregations for long fields can currently only return doubles and there
    // seems to be no intent on fixing this right now.
    DateTime ts = new DateTime((long) agg.getValue());
    return ts;
  }
}
