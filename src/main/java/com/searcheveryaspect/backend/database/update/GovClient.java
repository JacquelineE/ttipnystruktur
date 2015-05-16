package com.searcheveryaspect.backend.database.update;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.ArrayList;


public class GovClient {

  public static final int TRAFFAR_LIMIT = 15000;
  private static final String START_DATE = "1970-01-01";

  public static ArrayList<GovDocumentList> fetchDocs(GovFetchRequest request) throws Exception {
    GovDocumentList fetched;
    String json = null;
    Gson gson = new Gson();
    ArrayList<GovDocumentList> result;

    // Gets the initial request
    try {
      json = URLConnectionReader.getText(request.toString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

    gson = new Gson();
    result = new ArrayList<GovDocumentList>();
    fetched = gson.fromJson(json, GovSearchResult.class).dokumentlista;


    result.add(fetched);

    // Oppna data API occasionally fails for searches that yields to many hits. If the
    // limit is reached the request is split into two new ones.
    if (fetched.warning != null && fetched.traffar > TRAFFAR_LIMIT) {
      System.out.println(fetched.warning);

      // Create an interval with the same start as the original request but with an end
      // that's halfway through the interval.
      Interval i1 =
          request.getInterval().withDurationBeforeEnd(
              request.getInterval().toDuration().dividedBy(2));
      // Create an interval with the same end as the original request but with a start
      // that's halfway through the interval.
      Interval i2 = request.getInterval().withStart(i1.getEnd());

      GovFetchRequest req1 = request.toBuilder().interval(i1).build();
      GovFetchRequest req2 = request.toBuilder().interval(i2).build();

      result.addAll(fetchDocs(req1));
      result.addAll(fetchDocs(req2));
    } else {
      // Checks if there are more pages found
      while (fetched.existsNextPage()) {
        json = URLConnectionReader.getText(fetched.nextPage());
        fetched = gson.fromJson(json, GovSearchResult.class).dokumentlista;
        // Adds extra pages
        result.add(fetched);
      }
    }

    // Download all documents' motion texts
    if (result != null) {
      for (GovDocumentList l : result) {
        if (l.dokument != null) {
          for (int i = 0; i < l.dokument.length; i++) {
            l.dokument[i].text = URLConnectionReader.getText(l.dokument[i].dokument_url_text);
          }
        }
      }
    }
    return result;
  }

  public static ArrayList<GovDocumentLite> documentConverter(ArrayList<GovDocumentList> list) {
    ArrayList<GovDocumentLite> result = new ArrayList<>();

    for (GovDocumentList l : list) {
      for (int i = 0; i < l.dokument.length; i++) {
        result.add(new GovDocumentLite(l.dokument[i]));
      }
    }
    return result;
  }

  /**
   * Fetches all docs since the start date up till this now.
   * 
   * @return
   * @throws Exception
   */
  public static ArrayList<GovDocumentList> fetchAllDocs() throws Exception {
    Interval interval =
        new Interval(DateTime.parse(START_DATE, DateTimeFormat.forPattern("yyyy-MM-dd")),
            DateTime.now());
    return fetchDocs(GovFetchRequest.newGovFetchRequest().interval(interval).build());
  }
}
