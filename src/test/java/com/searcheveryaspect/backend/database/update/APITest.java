/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dajmmannen
 * 
 */
public final class APITest {

  /**
   * 
   * @param search, string to be used for search.
   * @param startDate, start date of the search in format "yyyy-MM-dd".
   * @param endDate, end date of the search in format "yyyy-MM-dd".
   * @param parties, parties to be included in the search.
   * @return
   * @throws Exception
   */
  public static ArrayList<GovDocumentList> QueryAPI(String search, String startDate,
      String endDate, List<String> parties) throws Exception {
    DateTime start = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
    DateTime end = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
    Interval interval = new Interval(start, end);

    GovFetchRequest request =
        GovFetchRequest.newGovFetchRequest().searchString(search).interval(interval)
            .parties(parties).build();
    return GovClient.fetchDocs(request);
  }


}
