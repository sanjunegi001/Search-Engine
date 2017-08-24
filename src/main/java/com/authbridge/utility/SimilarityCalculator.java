package com.authbridge.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Implements Dice Coefficient algorithm to find similarity percentage between two given strings. Reference:
 * http://www.catalysoft.com/articles/StrikeAMatch.html
 * https://en.wikipedia.org/wiki/S�rensen�Dice_coefficient
 */
@Component
public class SimilarityCalculator {
	
	/**
	 * Returns the similarity percentage between two given strings.
	 * 1) Finds list of adjacent character pairs in both the strings (removes all pairs with space in them)
	 * 		e.g. if FRANCE was given, FR, RA, AN, NC, CE are the pairs
	 * 2) Finds list of common pairs between the two strings
	 * 3) Returns match percentage as (2 * num. of common pairs / total num. of pairs) * 100.
	 * 
	 * If one of both the strings given are null or empty, returns 0.0 as the match percentage.
	 * 
	 * @param str1 String to compare
	 * @param str2 String to compare
	 * @return Percentage match between the two strings
	 */
	public double compareStrings(String str1, String str2) {
		if(str1 == null || str1.equals("")
				|| str2 == null || str2.equals("")) {
			return 0.0;
		}
		
		int intersection = 0;
		
		// finds list of adjacent character pairs in both the strings
		List<String> pairs1 = wordLetterPairs(str1.toUpperCase());
		List<String> pairs2 = wordLetterPairs(str2.toUpperCase());
		int union = pairs1.size() + pairs2.size();
		if(union == 0) {
			return 0.0;
		}

		// by comparing pairs from the first string with pairs from the second string,
		// find the number of common pairs between two strings
		for (int i = 0; i < pairs1.size(); i++) {
			String pair1 = pairs1.get(i);
			for(int j=0; j<pairs2.size(); j++) {
				String pair2 = pairs2.get(j);
				if (pair1.equals(pair2)) {
					intersection++;
					pairs2.remove(j);
					break;
				}
			}
		}

		return ((2.0 * intersection) / union) * 100.0;
	}

	/**
	 * Returns list of adjacent character pairs in the given strings (removes all pairs with space in them).
	 * 		e.g. if FRANCE was given, FR, RA, AN, NC, CE are the pairs.
	 * 
	 * First splits the given string into multiple words. Calculates character pairs in each word and combines
	 * them.
	 * 
	 * @param str Input string
	 * @return List of character pairs
	 */
	private List<String> wordLetterPairs(String str) {
		List<String> allPairs = new ArrayList<>();

		// Tokenize the string and put the tokens/words into an array
		String[] words = str.split("\\s");

		// For each word
		for (int w = 0; w < words.length; w++) {
			// ignore empty strings
			if(words[w] == null || words[w].equals("")) {
				continue;
			}

			// Find the pairs of characters
			String[] pairsInWord = letterPairs(words[w]);
			for (int p = 0; p < pairsInWord.length; p++) {
				allPairs.add(pairsInWord[p]);
			}
		}

		return allPairs;
	}

	/**
	 * Returns list of adjacent character pairs in the given word.
	 * 		e.g. if FRANCE was given, FR, RA, AN, NC, CE are the pairs.
	 * 
	 * If given word has only one letter, returns that letter alone.
	 * 
	 * @param str Word
	 * @return List of character pairs
	 */
	private String[] letterPairs(String str) {
		int numPairs = str.length() - 1;
		
		if(numPairs == 0) {
			return new String [] { str };
		}

		String[] pairs = new String[numPairs];
		for (int i = 0; i < numPairs; i++) {
			pairs[i] = str.substring(i,i+2);
		}

		return pairs;
	}
}
