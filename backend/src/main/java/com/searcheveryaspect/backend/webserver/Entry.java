package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Objects;

/**
 * 
 */
public class Entry {
  private final int data;
  private final ImmutableList<Document> docs;

  public Entry(int data, ImmutableList<Document> docs) {
    this.data = data;
    this.docs = docs;
  }

  public int getData() {
    return data;
  }

  public ImmutableList<Document> getDocs() {
    return docs;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Entry) {
      Entry that = (Entry) o;
      return data == that.data && docs.equals(that.docs);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, docs);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("data", data).add("docs", docs).toString();
  }
}