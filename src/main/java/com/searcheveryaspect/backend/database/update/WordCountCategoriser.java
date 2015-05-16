/**
 * 
 */
package com.searcheveryaspect.backend.database.update;

import org.elasticsearch.common.lang3.ArrayUtils;

import com.searcheveryaspect.backend.shared.*;

/**
 * @author lundblom
 * Identifies categories with word matching.
 */
public class WordCountCategoriser implements Categoriser {

	//If this number times the secondMax value is larger or equal
	//to the second category, it is accepted.
	private final float SECOND_CATEGORY_PERCENTAGE = 1.25f;

	@Override
	public String[] categorise(ESDocument doc) 
	{
		
		Category[] categories = com.searcheveryaspect.backend.shared.Category.values();
		
		int[] wordcounts;
		String[] splitText;
		
		
		wordcounts = new int[categories.length];
		
		//If there's no text we try to work with the title
		if(doc.text == null)
			splitText = ArrayUtils.addAll(doc.getTitle().split(" ")); 
		else
			splitText = doc.text.split(" ");
		
		for(String s : splitText)
		{
			for(int i = 0; i < categories.length; i++)
			{
				String[] keywords = categories[i].getKeywords();
				for(int j = 0; j < keywords.length; j++)
				{
					if(s.equals(keywords[j].toLowerCase()))
						wordcounts[i]++;
				}
			}
		}
		
		int firstMax = 0;
		int firstMaxIndex = 0;
		
		int secondMax = 0;
		int secondMaxIndex = 0;
		for(int i = 0; i < wordcounts.length; i++)
		{
			
			if(wordcounts[i] > firstMax)
			{
				secondMax = firstMax;
				secondMaxIndex = firstMaxIndex;
				firstMax = wordcounts[i];
				firstMaxIndex = i;
				continue;
			}
			if(wordcounts[i] > secondMax)
			{
				secondMax = wordcounts[i];
				secondMaxIndex = i;
			}
		}
		
		//If no word is found we have not been able to
		//define a category
		if(firstMax == 0)
			return new String[]{Category.UNKNOWN.toString()};
		
		if((float)secondMax * SECOND_CATEGORY_PERCENTAGE >= (float)firstMax)
			return new String[]{categories[firstMaxIndex].toString(), categories[secondMaxIndex].toString()};
		
		return new String[]{categories[firstMaxIndex].toString()};
		
		
	}

}
