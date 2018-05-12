package com.authbridge.config;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ABStringUtil {
	private static final Pattern NUMBERIC_STRING_PATTERN = Pattern.compile("[0-9]+");

	// Normal String TRIM ONLY
	public static String trimSpaces(String inputVal) {
		return inputVal.trim();
	}

	// Normal Tokenize Only
	public static String[] tokenize(String inputVal, String token) {
		String[] tokens = inputVal.split(token);
		return tokens;
	}

	// Remove only if matches as a word but not as a subpart
	// Will Remove if special chars are there before or after the word
	public static String removeMatch(String inputVal, List<String> tokenToMatchList) {
		// Remove the tokenToMatch from the input if only it occurs as a whole
		// word
		// should not a substring of a word
		// remove caste from ,caste, but not subcaste //
		// String inputVal = ",cat,";
		for (String token : tokenToMatchList) {
			inputVal = removeMatch(inputVal, token);
		}
		return inputVal;
	}

	// Remove only if matches as a word but not as a subpart
	// Will Remove if special chars are there before or after the word
	public static String removeMatch(String inputVal, String tokenToMatch) {
		// Remove the tokenToMatch from the input if only it occurs as a whole
		// word
		// should not a substring of a word
		// remove caste from ,caste, but not subcaste //
		// String inputVal = ",cat,";
		tokenToMatch = tokenToMatch.replace("\\", "\\\\");
		Pattern pattern = Pattern.compile("\\b" + tokenToMatch + "\\b", Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(inputVal);
		while (m.find()) {
			inputVal = m.replaceAll("");
		}
		return inputVal;
	}
	
	public static int match(String inputVal, String tokenToMatch) {
		// Match the tokenToMatch from the input if only it occurs as a whole
		// word
		// should not a substring of a word
		// Match caste from ,caste, but not subcaste //
		// String inputVal = ",cat,";
		int i=0;
		tokenToMatch = tokenToMatch.replace("\\", "\\\\");
		Pattern pattern = Pattern.compile("\\b" + tokenToMatch + "\\b", Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(inputVal);
		while (m.find()) {
			i++;
		}
		return i;
	}

	// Clean all special characaters except @/.
	public static String clean1(String inputVal) {
		// remove special chars
		// [ ](e.g., the characters ` ~ ! @ # $ % ^ & * ( ) - _ = + [ { ] } \ |
		// ; : ’ ” , < . >? and /)"
		// Take off @ / .
		// inputVal = inputVal.replaceAll("[`~!#$%&*()-_=+{}|;:’”,<>?/]", "");
		inputVal = inputVal.replaceAll("[` ~ \\] \\[  # $ % & * ( ) - _ = + { } | ; : ’ ” , . @ \\/ < > ? \\\\ ]", "");
		return inputVal.trim();

	}
	
	
	public static String clean(String inputVal){
		 Pattern pt = Pattern.compile("[^a-zA-Z0-9 ]");
	        Matcher match= pt.matcher(inputVal);
	        while(match.find())
	        {
	            String s= match.group();
	            inputVal=inputVal.replaceAll("\\"+s, " ");
	        }
	        return inputVal;
	}

	// DONE
	// Clean numerals
	public static String removeDigits(String inputVal) {
		// remove digits 0-9
		inputVal = inputVal.replaceAll("[0-9]", "");
		return inputVal.trim();
	}

	// Remove the specified character from the inputs
	public static String discardChar(String inputVal, String charToRemove) {
		//
		inputVal = inputVal.replace(charToRemove, "");
		inputVal = inputVal.replace(charToRemove.toUpperCase(), "");
		return inputVal;
	}

	public static String[] getBeforeAfterTokens(String inputVal, String tokenToMatch) {
		// S/O age R/O D/O
		// Of length 2
		// Eg : Rajeswari D/O Devadass Age : 33 : [Rajeswari] [Devadass Age :33]
		// Rajeswari D/O Expected /: [Rajeswari]
		// If D/0 not found : expected NULL, NULL.
		tokenToMatch = tokenToMatch.replace("\\", "\\\\");
		Pattern pattern = Pattern.compile("\\b" + tokenToMatch + "\\b", Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(inputVal);
		if (!m.find()) {

			return null;
		}
		String[] items = pattern.split(inputVal);
		String[] tokens = new String[2];
		if (items.length >= 1) {
			tokens[0] = items[0];
		}
		if (items.length >= 2) {
			tokens[1] = items[1];
		}

		return items;
	}

	/**
	 * Returns if the given string contains at least one numeric (0 - 9) digit.
	 * Returns false for empty or null string.
	 * 
	 * @param inputVal String to check for numeric
	 * @return True if at least one character is numeric; false otherwise
	 */
	public static boolean containsNumbers(String inputVal) {
		if(inputVal == null || inputVal.isEmpty()) {
			return false;
		}

		Matcher matcher = NUMBERIC_STRING_PATTERN.matcher(inputVal);
		return matcher.find();
	}

	
}
