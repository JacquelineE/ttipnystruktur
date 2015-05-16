package com.searcheveryaspect.backend.database.update;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class URLConnectionReader {
    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();
        
        /*
        // Ska lägga in [] om det bara är 1 träff
        String docIsList = "\"dokument\": [";
        String responseString = response.toString();
        System.out.println(responseString);
        if(!responseString.contains(docIsList)) {
        	//First split
        	String[] parts = responseString.split("\"dokument\": {");
        	String part1 = parts[0]; // Before the document split
        	String part2 = parts[1]; // After the document split
        	StringBuilder sb = new StringBuilder();
        	sb.append(part1);
        	sb.append("\"dokument\": [ {");
        	sb.append(part2);
        	String doc = sb.toString();
        	
        	//Save the document (doc) in an arraylist (chars)
        	ArrayList<Character> chars = new ArrayList<Character>();
        	for(char ch : doc.toCharArray()) {
        		chars.add(ch);
        	}
        	//Remove five last elements in chars
        	for(int i=0; i<5; i++) {
        		chars.remove(chars.size()-1);
        	}
        	//Add new ending to chars
        	String newEndString = "      }    ]  }}";
        	ArrayList<Character> newEndChar = new ArrayList<Character>();
        	for(char ch : newEndString.toCharArray()) {
        		newEndChar.add(ch);
        	}
        	chars.addAll(newEndChar);
        	return chars.toString();
        }
        */
        
        return response.toString();
    }
}