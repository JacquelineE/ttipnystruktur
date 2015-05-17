package com.searcheveryaspect.backend.database.update;


import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Create an ESDocument
 * 
 * @author Jacqueline Eriksson
 * 
 */

public class ESDocumentBuilder {
  // Pattern to match multiple parenthesis undertitles.
  static Pattern partyPattern = Pattern.compile("\\(.\\)");
  static WordCountCategoriser wcc;
  private static boolean initialised = false;

  public static void initBuilder() {
    wcc = new WordCountCategoriser();

    initialised = true;
  }

  public static boolean isInitialised() {
    return initialised;
  }

  public static ESDocument createESDocument(GovDocumentLite doc) {

    if (!isInitialised()) {
      System.out.println("Error: ESDocumentbuilder is not initialised, cant build documents.");
      return null;
    }

    String docId = doc.getId();

    DateTime dt = new DateTime(doc.getDatum());
    long publishedTimestamp = dt.getMillis() / 1000;
    long fetchedTimestamp = new DateTime().getMillis() / 1000;

    String title = doc.getTitel();

    // Party
    String[] party;
    if (doc.getUndertitel() == null) {
      throw new NullPointerException("Motion " + doc.getId()
          + ": undertitle is null, can't specify party");
    } else {
      try {
        party = createParty(doc.getUndertitel());
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Motion " + doc.getId() + ": " + e.getMessage());
      }
    }

    String text = doc.getText();

    ESDocument eSDoc =
        new ESDocument(docId, publishedTimestamp, fetchedTimestamp, title, null, party, text);

    eSDoc.category = wcc.categorise(eSDoc);

    return eSDoc;
  }

  public static String[] createParty(String underTitle) {
    String[] splittedUnderTitle = underTitle.split(" \\(");

    // Handles cases where undertitle is of the format "av Jane Doe m.fl. (M, C, FP, KD)".
    if (splittedUnderTitle.length == 2) {
      String partiesUnformatted =
          splittedUnderTitle[1].substring(0, (splittedUnderTitle[1].length() - 1));
      String[] parties = partiesUnformatted.split(", ");
      return parties;
    }

    // Handles cases where the undertitle if of the format
    // "av Jane Doe (V) och John Doe (S)"
    Matcher m = partyPattern.matcher(underTitle);
    List<String> parties = new ArrayList<>();
    while (m.find()) {
      parties.add(m.group().substring(1, m.group().length() - 1));
    }
    return parties.toArray(new String[parties.size()]);
  }
}