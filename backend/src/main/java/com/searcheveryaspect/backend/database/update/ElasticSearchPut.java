package com.searcheveryaspect.backend.database.update;

import com.google.gson.Gson;

import org.elasticsearch.client.Client;


/**
 * 
 * @author Jacqueline Eriksson
 * @version 2015-04-18
 */
public class ElasticSearchPut {

  private Client client;
  
  /**
   * Returns a new ElasticSearchPut instance containing a client reference.
   * 
   * @param client Expects a elastic search client wrapping a started node.  
   */
  public ElasticSearchPut(Client client) {
    this.client = client;
  }


  // TODO: For alla dokument{
  public void putDocument(ESDocument doc) {

    Gson gson = new Gson();
    String jsonString = gson.toJson(doc);
    client.prepareIndex("motions", "motion", doc.getDocId()).setSource(jsonString).execute()
        .actionGet();
  }

  public void close() {
    client.close();
  }
}
