package com.searcheveryaspect.backend.database.update;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * 
 * Specifies the ESDocument object
 * 
 * @author Jacqueline Eriksson
 * 
 */

public class ESDocument {
  String docId;
  long publishedTimestamp; // in unix
  long fetchedTimestamp; // in unix
  String title;
  String[] category;
  String[] party;
  String text;


  public ESDocument(String docId, long publishedTimestamp, long fetchedTimestamp, String title,
      String[] category, String[] party, String text) {
    this.docId = docId;
    this.publishedTimestamp = publishedTimestamp;
    this.fetchedTimestamp = fetchedTimestamp;
    this.title = title;
    this.category = category;
    this.party = party;
    this.text = text;
  }


  public String getDocId() {
    return docId;
  }

  public String getTitle() {
    return title;
  }

  public String[] getCategory() {
    return category;
  }

  public String getText() {
    return text;
  }

  public long getFetchedTimestamp() {
    return fetchedTimestamp;
  }

  public long getPublishedTimestamp() {
    return publishedTimestamp;
  }

  public String[] getParty() {
    return party;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ESDocument) {
      ESDocument that = (ESDocument) o;
      return docId.equals(that.docId) && publishedTimestamp == that.publishedTimestamp
          && fetchedTimestamp == that.fetchedTimestamp && title.equals(that.title)
          && category.equals(that.category) && party.equals(that.party);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(docId, publishedTimestamp, fetchedTimestamp, title, category, party);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("docID", docId)
        .add("publishedTimestamp", publishedTimestamp).add("fetchedTimestamp", fetchedTimestamp)
        .add("title", title).add("category", category).add("party", party).toString();
  }
}
