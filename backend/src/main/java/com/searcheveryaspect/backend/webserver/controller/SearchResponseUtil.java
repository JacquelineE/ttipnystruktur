package com.searcheveryaspect.backend.webserver.controller;

import com.google.common.collect.ImmutableList;

import com.searcheveryaspect.backend.webserver.Document;
import com.searcheveryaspect.backend.webserver.Entry;
import com.searcheveryaspect.backend.webserver.PartyData;
import com.searcheveryaspect.backend.webserver.SearchResponse;


/**
 * Provides some utils for mocking up search responses.
 */
public class SearchResponseUtil {
  /**
   * Only used for testing purposes.
   * 
   * @param category for the search.
   * @return
   */
  public static SearchResponse dummyMonthResponse(String category) {

    Document doc1 =
        new Document.Builder()
            .title("med anledning av prop. 2014/15:71 Förenklingar i anläggningslagen")
            .link("http://data.riksdagen.se/dokument/H2023059.html").date("2015-02-03").build();
    Document doc2 =
        new Document.Builder()
            .title(
                "med anledning av prop. 2014/15:85 Ökad individanpassning – en effektivare sfi och vuxenutbildning")
            .link("http://data.riksdagen.se/dokument/H2023051.html").date("2015-03-13").build();

    ImmutableList<Entry> interval1 =
        new ImmutableList.Builder<Entry>()
            .add(new Entry(2, new ImmutableList.Builder<Document>().add(doc1).add(doc2).build()))
            .add(
                new Entry(4, new ImmutableList.Builder<Document>().add(doc1).add(doc1).add(doc1)
                    .add(doc2).build()))
            .add(new Entry(1, new ImmutableList.Builder<Document>().add(doc2).build())).build();
    ImmutableList<Entry> interval2 =
        new ImmutableList.Builder<Entry>()
            .add(new Entry(0, new ImmutableList.Builder<Document>().build()))
            .add(new Entry(2, new ImmutableList.Builder<Document>().add(doc1).add(doc1).build()))
            .add(
                new Entry(3, new ImmutableList.Builder<Document>().add(doc2).add(doc1).add(doc2)
                    .build())).build();
    ImmutableList<Entry> interval3 =
        new ImmutableList.Builder<Entry>()
            .add(
                new Entry(5, new ImmutableList.Builder<Document>().add(doc1).add(doc2).add(doc1)
                    .add(doc2).add(doc2).build()))
            .add(new Entry(2, new ImmutableList.Builder<Document>().add(doc2).add(doc2).build()))
            .add(new Entry(2, new ImmutableList.Builder<Document>().add(doc2).add(doc1).build()))
            .build();
    ImmutableList<Entry> interval4 =
        new ImmutableList.Builder<Entry>()
            .add(new Entry(0, new ImmutableList.Builder<Document>().build()))
            .add(new Entry(0, new ImmutableList.Builder<Document>().build()))
            .add(new Entry(0, new ImmutableList.Builder<Document>().build())).build();

    ImmutableList<String> labels =
        new ImmutableList.Builder<String>().add("2015-02").add("2015-03").add("2015-04").build();
    ImmutableList<PartyData> dataset =
        new ImmutableList.Builder<PartyData>().add(new PartyData("V", interval1))
            .add(new PartyData("S", interval2)).add(new PartyData("MP", interval3))
            .add(new PartyData("SD", interval1)).add(new PartyData("C", interval3))
            .add(new PartyData("FP", interval1)).add(new PartyData("KD", interval2))
            .add(new PartyData("M", interval3)).build();
    return SearchResponse.newSearchResponse().category(category).labels(labels).datasets(dataset)
        .build();
  }
}
