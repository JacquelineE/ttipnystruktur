/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

/**
 * @author dajmmannen
 *
 */
public interface Categoriser 
{
	/**
	 * Categorises an ESDocument in some way.
	 * @param doc Document to be categorised
	 * @return All the categorises applicable to the document
	 */
	abstract String[] categorise(ESDocument doc);
}
