package com.searcheveryaspect.backend.database.update.language;


import com.beust.jcommander.JCommander;
import com.searcheveryaspect.backend.database.update.ESDocument;
import com.searcheveryaspect.backend.database.update.ESDocumentBuilder;
import com.searcheveryaspect.backend.database.update.GovClient;
import com.searcheveryaspect.backend.database.update.GovDocumentList;
import com.searcheveryaspect.backend.database.update.GovDocumentLite;
import com.searcheveryaspect.backend.database.update.GovFetchRequest;
import com.searcheveryaspect.backend.database.update.WordCountCategoriser;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) 
  {
	  
	  DateTime startDate = new DateTime(2010, 1, 1, 1, 1);
	  DateTime endDate = new DateTime(2011, 1, 1, 1, 1);
	  
	  System.out.println("Initialising builder");
	  ESDocumentBuilder.initBuilder();
	  System.out.println("Done.");
	  
	  Interval interval = new Interval(startDate, endDate);
	  System.out.println("Creting new request");
	  GovFetchRequest request = new GovFetchRequest("skatt", "", interval, "","", "", "", "", new ArrayList<String>());
	  System.out.println("Done.");
	  
	  ArrayList<GovDocumentList> listList = null;
	  ArrayList<GovDocumentLite> list;
	  ArrayList<ESDocument> esList = new ArrayList<>();
	  
	  
	  try 
	  {
		  System.out.println("Fetching documents");
		  listList = GovClient.fetchDocs(request);
		  System.out.println("Done.");
	  } 
	  catch (Exception e) 
	  {
		e.printStackTrace();
	  }
	  
	  System.out.println("Converting documents");
	  list = GovClient.documentConverter(listList);
	  System.out.println("Done.");
	  
	  System.out.println("Adding esDocuments");
	  for(GovDocumentLite d : list)
	  {
		  esList.add(ESDocumentBuilder.createESDocument(d));
	  }
	  System.out.println("Done");
	  
	  for(ESDocument d : esList)
	  {
		  System.out.println("Title :" + d.getTitle());
		  System.out.print("Categories: ");
		  for(String s : d.getCategory())
		  {
			  System.out.println(s + " ");
		  }
	  }
	  
  }
}

