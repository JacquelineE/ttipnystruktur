package com.searcheveryaspect.backend.database.update;

/**
 * A simple representation of a document. Contains links to external information.
 * 
 */
public class GovDocumentLite {
  private final int traff;
  // Date the motion was published.
  private final String datum;
  // Unique id for the document.
  private final String id;
  // Link to a text version of the motion.
  private final String dokument_url_text;
  // Motion text
  private final String text;
  // Document title.
  private final String titel;
  // Document subtitle, contains authors and their party in the format "av Jane Doe (c)".
  private final String undertitel;
  private final String organ;
  // Type of document, e.g. mot (motion) or prop (proposition).
  private final String doktyp;
  private final GovAppendix filbilaga;

  public GovDocumentLite(int traff, String datum, String id, String dokument_url_text, String text,
      String titel, String undertitel, String organ, String doktyp, GovAppendix filbilaga) {
    this.traff = traff;
    this.datum = datum;
    this.id = id;
    this.dokument_url_text = dokument_url_text;
    this.text = text;
    this.titel = titel;
    this.undertitel = undertitel;
    this.organ = organ;
    this.doktyp = doktyp;
    this.filbilaga = filbilaga;
  }

  public GovDocumentLite(GovDocument doc) {
    this.traff = doc.traff;
    this.datum = doc.datum;
    this.id = doc.id;
    this.dokument_url_text = doc.dokument_url_text;
    this.text = doc.text;
    this.titel = doc.titel;
    this.undertitel = doc.undertitel;
    this.organ = doc.organ;
    this.doktyp = doc.doktyp;
    this.filbilaga = doc.filbilaga;
  }

  public int getTraff() {
    return traff;
  }

  public String getDatum() {
    return datum;
  }

  public String getId() {
    return id;
  }

  public String getDokument_url_text() {
    return dokument_url_text;
  }
  
  public String getText()
  {
	  return text;
  }

  public String getTitel() {
    return titel;
  }

  public String getUndertitel() {
    return undertitel;
  }

  public String getOrgan() {
    return organ;
  }

  public String getDoktyp() {
    return doktyp;
  }

  public GovAppendix getFilbilaga() {
    return filbilaga;
  }

  public String toString() {
    return "Träff: " + traff + " Datum: " + datum + " Id: " + id + " Dokument_url_text: "
        + dokument_url_text + " Titel: " + titel + " Undertitel: " + undertitel + " Organ: "
        + organ + " Doktyp: " + doktyp;
  }
}
