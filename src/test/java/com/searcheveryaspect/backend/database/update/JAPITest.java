/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import static org.junit.Assert.assertTrue;

import com.searcheveryaspect.backend.database.update.GovDocumentList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author dajmmannen
 *
 */
public class JAPITest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	/* Tests that the query system returns
	 * no results where it should.
	 */
	@Test
	public void testEmptyResult()
	{
		ArrayList<GovDocumentList> t = null;
		try {
			t = APITest.QueryAPI("thisshouldreturnnothing", "1900-01-01", "2015-12-31", new ArrayList<String>());
		} catch (Exception e) { }
		
		assertTrue(t != null);
		String s = t.toString();
		
		String dataS = s.substring(45, s.length() - 1);
		assertTrue(dataS.equals("Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]"));
		

		try{
			t = APITest.QueryAPI("", "1000-01-01", "1800-12-31", new ArrayList<String>());
		} catch (Exception e) {
		}
		assertTrue(t != null);
		s = t.toString();
		
		dataS = s.substring(45, s.length() - 1);
		assertTrue(dataS.equals("Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]"));
		
		try{
			t = APITest.QueryAPI("", "2100-01-01", "2200-12-31", new ArrayList<String>());
		} catch (Exception e) {	}
		assertTrue(t != null);
		s = t.toString();
		
		dataS = s.substring(45, s.length() - 1);
		assertTrue(dataS.equals("Sida: 1 Sidor: 0 Traff Från: 1 Träffar: 0]"));
		
	}
	
	// Tests that the system can handle only one result properly
	//WILL RETURN AN ERROR CURRENTLY
	// TODO: Test is currently failing, fix it!
    @Ignore
	@Test
	public void testOneResult()
	{
		ArrayList<GovDocumentList> t = null;
		try {
			System.out.println("test!\n");
			t = APITest.QueryAPI("Svanskupering p� hundar", "1990-01-01", "1990-12-31", new ArrayList<String>());
			System.out.println("\n Tested! \n");
		} catch (Exception e) {	}
		assertTrue(t != null);
		assertTrue(t.toString().equals( "GovDocumentList [Datum: 2015-04-10 12:37:27 Sida: 1 Sidor: 0 Traff Fr�n: 1 Tr�ffar: 0]"));
		
		try {
			t = APITest.QueryAPI("kaffe te matte", "1975-01-01", "1975-12-31", new ArrayList<String>());
		} catch (Exception e) {	}
		assertTrue(t != null);
		
		t.get(0).dokument[0].titel.equals("om slopande av merv�rdeskatten p� livsmedel, m. m.");
		t.get(0).dokument[0].datum.equals("1975-01-25");
		
	}
	
	/* Tests that different years return different sizes of results */
	@Test
	public void testYears()
	{
		ArrayList<GovDocumentList> t1 = null;
		ArrayList<GovDocumentList> t2 = null;
		try {
			t1 = APITest.QueryAPI("skatt", "2010-01-01", "2010-12-31", new ArrayList<String>());
			t2 = APITest.QueryAPI("skatt", "2011-01-01", "2011-12-31", new ArrayList<String>());
		} catch (Exception e) {	}
		
		for(int i = 0; i < t1.size(); i++)
		{
			for(int j = 0; j < t2.size(); j++)
			{
				assertTrue(!t1.get(i).equals(t2.get(j)));
			}
		}	
	}
	
	/* Checks that party searches are different from each other */
	@Test
	public void testParties()
	{
		ArrayList<GovDocumentList> t1 = null;
		ArrayList<GovDocumentList> t2 = null;
		try {
			t1 = APITest.QueryAPI("", "2010-01-01", "2010-12-31", new ArrayList<String>());
			t2 = APITest.QueryAPI("", "2000-01-01", "2000-12-31", new ArrayList<String>(Arrays.asList(new String[]{"S"})));
		} catch (Exception e) {	}
		
		assertTrue( t1 != null);
		assertTrue(t2 != null);
		assertTrue(t1.size() != t2.size());
		
		try {
			t1 = APITest.QueryAPI("", "2000-01-01", "2000-12-31", new ArrayList<String>(Arrays.asList(new String[]{"S"})));
			t2 = APITest.QueryAPI("", "2000-01-01", "2000-12-31", new ArrayList<String>(Arrays.asList(new String[]{"KD"})));
		} catch (Exception e) {	}
		assertTrue(t1 != null);
		assertTrue(t2 != null);
		assertTrue(t1.size() != t2.size());
	}
	
	/* Tests rather large queries and their speed */
	@Test(timeout=600000)
	public void testManyResults()
	{
		ArrayList<GovDocumentList> t = null;
		try {
			t = APITest.QueryAPI("", "2009-01-01", "2011-12-31", new ArrayList<String>());
		} catch (Exception e) {	
		  e.printStackTrace();
		}
		assertTrue(t != null);

		
		try {
			t = APITest.QueryAPI("", "2002-01-01", "2004-12-31", new ArrayList<String>());
		} catch (Exception e) {
          e.printStackTrace();
        }
		assertTrue(t != null);
	}

}
