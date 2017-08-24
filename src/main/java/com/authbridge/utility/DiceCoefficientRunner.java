package com.authbridge.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.authbridge.config.ABStringUtil;

/**
 * Runner class for {@link SimilarityCalculator}. Execute as below:
 * 
 * For executing it against a particular input pair, run
 * 
 * java org.demo.similarity.DiceCoefficientRunner "<String 1>" 	"<String 2>" 0
 * 
 * It will print out the match percentage.
 * 
 * For executing it for a file with inputs (Tilde(~)-separated strings in a file; one pair per line), run
 * 
 * java org.demo.similarity.DiceCoefficientRunner "<Input file path>" "<Output file path>" 1
 * 
 * It will print out the result in tht output file path.
 */
public class DiceCoefficientRunner {
	
	/**
	 * Result container for multiple pairs run.
	 */
	private static class MatchResult {
		private String stringPair;
		private Double matchPercentage;
		
		private MatchResult(String stringPair, Double matchPercentage) {
			super();
			this.stringPair = stringPair;
			this.matchPercentage = matchPercentage;
		}
		
		@Override
		public String toString() {
			return "[String Pair = " + stringPair
					+ ", Match Percentage = " + matchPercentage + "]";
		}
	}

	/**
	 * Static fields.
	 */
	private static List<MatchResult> MATCH_PERCENTAGE = new ArrayList<>();
	private static SimilarityCalculator SIMILARITY = new SimilarityCalculator();

	/**
	 * Entry method. Run it as 
	 * java org.demo.similarity.DiceCoefficientRunner "<String 1>" "<String 2>" 0 (or)
	 * java org.demo.similarity.DiceCoefficientRunner "<Input file path>" "<Output file path>" 1
	 * 
	 * @param args Program accepts three string arguments
	 * @throws IOException
	 */
	public static void main1(String [] args) throws IOException {
		
		
		String address = "15/63 Civil Lines Kanpur";
		address = address.toLowerCase();
		System.out.println(address);
		//address = ABStringUtil.removeMatch(address, configService.getAddressStopwords());
		address = ABStringUtil.clean(address).trim();
				System.out.println(address);
		
		System.out.println(SIMILARITY.compareStrings("Hareesh", "Kumar satheesh"));
		Utility x = new Utility();
		System.out.println(x.similarity("Satheesh Kumar", "Kumar, Satheesh"));
		System.exit(0);
		
		// single pair input
		if(args[2].equals("0")) {
			Double matchPercentage = SIMILARITY.compareStrings(args[0], args[1]);
			System.out.println(matchPercentage);
		
		// file input
		} else if(args[2].equals("1")) {
			System.out.println(new Date());
			
			// read file path; call similarity for each record
			String filePath = args[0];
			String outputFilePath = args[1];
			try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
				stream.forEach(DiceCoefficientRunner::compareAndPopulateMatch);
			}
			System.out.println(new Date());
			
			// convert match percentage result list to list of string and write to file
			List<String> resultsAsString = MATCH_PERCENTAGE.stream().map(Object::toString).collect(Collectors.toList());
			Files.write(Paths.get(outputFilePath), resultsAsString);
		}
	}

	/**
	 * Splits the given string pair and calls similarity compare method. With the result,
	 * creates an object of {@link MatchResult} object and puts it in static result list.s
	 * @param stringPair
	 */
	private static void compareAndPopulateMatch(String stringPair) {
		String [] stringsToCompare = stringPair.split("~");
		Double matchPercentage = SIMILARITY.compareStrings(stringsToCompare[0], stringsToCompare[1]);
		MATCH_PERCENTAGE.add(new MatchResult(stringPair, matchPercentage));
	}
}
