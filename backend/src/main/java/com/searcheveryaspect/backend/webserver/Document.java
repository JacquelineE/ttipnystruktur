package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;

import com.searcheveryaspect.backend.database.update.ESDocument;

import org.joda.time.DateTime;

import java.util.Objects;

/**
 * 
 */
public class Document {
  private final String title;
  private final String link;
  private final String date;
  private final static String URL = "http://data.riksdagen.se/dokument/";

  public Document(Document.Builder b) {
    this.title = b.title;
    this.link = b.link;
    this.date = b.date;
  }

  public Document(ESDocument doc) {
    title = doc.getTitle();
    link = URL + doc.getDocId();
    date = (new DateTime(doc.getPublishedTimestamp() * 1000)).toString("yyyy-MM-dd");
  }

  public String getName() {
    return title;
  }

  public String getLink() {
    return link;
  }

  public String getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Document) {
      Document that = (Document) o;
      return title.equals(that.title) && link.equals(that.link) && date.equals(that.date);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, link, date);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("title", title).add("link", link).add("date", date)
        .toString();
  }

  public static class Builder {
    private String title;
    private String link;
    private String date;

    public Document.Builder title(String s) {
      this.title = s;
      return this;
    }

    public Document.Builder link(String s) {
      this.link = s;
      return this;
    }

    public Document.Builder date(String s) {
      this.date = s;
      return this;
    }

    public Document build() {
      return new Document(this);
    }
  }

  public static Document.Builder newDocument() {
    return new Builder();
  }
}
