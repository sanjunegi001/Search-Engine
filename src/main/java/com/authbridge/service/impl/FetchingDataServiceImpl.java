package com.authbridge.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.authbridge.DTO.CalcScoreInputDTO;
import com.authbridge.DTO.CalcWeightageInputDTO;
import com.authbridge.DTO.CaseDetailDTO;
import com.authbridge.DTO.MatchedStatusUpdateDTO;
import com.authbridge.DTO.SearchCaseDetailDTO;
import com.authbridge.DTO.SearchResultDTO;
import com.authbridge.DTO.SolrStautusDTO;
import com.authbridge.DTO.WeightageDTO;
import com.authbridge.config.ABStringUtil;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.model.CheckIdPartyIdMapper;
import com.authbridge.model.Modification;
import com.authbridge.service.CheckPartyMappingService;
import com.authbridge.service.ConfigService;
import com.authbridge.service.FetchingDataService;
import com.authbridge.service.StopWordsService;
import com.authbridge.service.WeightageService;
import com.authbridge.utility.SimilarityCalculator;
import com.authbridge.utility.Utility;

@Component
public class FetchingDataServiceImpl implements FetchingDataService {
	private static Logger LOG = org.slf4j.LoggerFactory
			.getLogger(FetchingDataServiceImpl.class);
	
	@Autowired
	private WeightageService weightageService;

	@Autowired
	private CheckPartyMappingService checkPartyMappingService;
	
	@Autowired
	private StopWordsService stopWordsService;
	
	@Autowired
	private ConfigService configService;

	@Autowired
	private Utility utility;
	
	@Autowired
	private SimilarityCalculator similarityCalculator;

	@Value("${solr.url}")
	private String solrURL;

	@Value("${solr.autocomplete}")
	private String solrAutoCompelte;

	@Value("${solr.autocomplete.restres}")
	private String autoCompleteResponseType;

	@Value("${solr.zkHostString}")
	private String zkHostString;

	@Value("${solr.collection}")
	private String collection;

	@Override
	public SearchResultDTO getAllResults() throws Exception {
		return this.getAllResults(null);
	}

	/**
	 * Returns results with result records always sorted in descending order of provided sort field.
	 */
	public SearchResultDTO getAllResults(SearchCaseDetailDTO searchCaseDetailDTO) throws Exception {

		if (searchCaseDetailDTO == null || utility.checkEmpty(searchCaseDetailDTO.getName())
				|| utility.checkEmpty(searchCaseDetailDTO.getAddress())) {
			throw new Exception("Name and Address Mandatory");
		}

	//	HttpSolrClient solr = new HttpSolrClient(solrURL);

		CloudSolrClient solr = new CloudSolrClient(zkHostString);
		solr.setDefaultCollection(collection);
		solr.connect();

		SolrQuery query = null;
		query = new SolrQuery();
		query.setRequestHandler("/select");
		
		String name = searchCaseDetailDTO.getName().toLowerCase().trim();

		String address = searchCaseDetailDTO.getAddress().toLowerCase();
		address = ABStringUtil.removeMatch(address, configService.getAddressStopwords());
		address = ABStringUtil.clean(address);
		address = address.replace("  ", " ").trim();

		// build a query of the format: <name> addr:<address word 1> addr:<address word 2> ...
		StringBuilder queryBuilder = new StringBuilder(name);
		
		String [] addressWords = address.split(" ");
		for(String addressWord: addressWords) {
			if(addressWord != null && addressWord.trim().length() >= 2) {
				queryBuilder.append(" addr:").append(addressWord.trim());
			}
		}

		String fatherName = null;
		if (!utility.checkEmpty(searchCaseDetailDTO.getFatherName())) {
			fatherName = ABStringUtil.removeDigits(searchCaseDetailDTO.getFatherName());
			fatherName = ABStringUtil.removeMatch(fatherName, configService.getNameStopwords());
			fatherName = ABStringUtil.clean(fatherName).trim();

			queryBuilder.append(" father:").append(fatherName);
			query.set("f.father.qf", "partyfather");
			LOG.info("Father's name entered by the user." + fatherName);
		} else {
			// do nothing
			LOG.info("Father's name not entered by the user.");
		}

		query.setQuery(queryBuilder.toString());
		LOG.info("Searching SOLR with name: " + name + "and address:"
				+ address);
		LOG.info("Solr Query:->" + query.getQuery());

		query.setRows(100);

		query.setFields("id", "partyname", "partyfather", "case_id", "address",
				"cleaned_address","cleaned_name","alias_name","address_district_id","address_state_id", "score", "maxScore");
		query.set("defType", "edismax");
		query.set("qf", "cleaned_name alias_name");
		query.set("f.addr.qf", "cleaned_address");
		query.set("mm",1);
		QueryResponse response;
		List<CaseDetailDTO> search = new ArrayList<CaseDetailDTO>();

		SearchResultDTO searchResultDTO = new SearchResultDTO();

		try {
			long startTime = System.currentTimeMillis();
			LOG.trace("Time while hitting the solr server: {}", startTime);

			response = solr.query(query);
			SolrDocumentList results = response.getResults();

			long resultsReceivedAt = System.currentTimeMillis();
			LOG.debug("Result size: {} with max score: {}. Time taken to fetch results: {}ms.",
					results.size(), results.getMaxScore(), (resultsReceivedAt - startTime));

			CalcScoreInputDTO calcScoreInputDTO = new CalcScoreInputDTO.Builder(name, address)
								.withFatherName(fatherName).withStateId(searchCaseDetailDTO.getStateId())
								.withDistrictId(searchCaseDetailDTO.getDistrictId())
								.withNameThreshold(searchCaseDetailDTO.getNameThreshold())
								.withAddrThreshold(searchCaseDetailDTO.getAddrThreshold())
								.build();
			search = calculateScore(results, calcScoreInputDTO);

			LOG.trace("Time taken for calculating score : {}ms", 
					(System.currentTimeMillis() - resultsReceivedAt));

			// based upon given sort field, sort in descending order
			if (!utility.checkEmpty(searchCaseDetailDTO.getSortField())) {
				String sortField = searchCaseDetailDTO.getSortField();
				if (sortField.equals(AUTHBRIDGECONSTANT.SORT_FIELD.NAME_MATCH)) {
					Collections.sort(search, CaseDetailDTO.NAME_MATCH);
				}
				if (sortField
						.equals(AUTHBRIDGECONSTANT.SORT_FIELD.ADDRESS_MATCH)) {
					Collections.sort(search, CaseDetailDTO.ADDRESS_MATCH);
				}
				if (sortField.equals(AUTHBRIDGECONSTANT.SORT_FIELD.BOTH_MATCH)) {
					Collections.sort(search, CaseDetailDTO.NAME_ADDRESS_MATCH);
				}
				if (sortField
						.equals(AUTHBRIDGECONSTANT.SORT_FIELD.WEIGHT_MATCH)) {
					Collections.sort(search, CaseDetailDTO.WEIGHT_MATCH);
				}
				if (sortField
						.equals(AUTHBRIDGECONSTANT.SORT_FIELD.FATHER_MATCH)) {
					Collections.sort(search, CaseDetailDTO.FATHER_MATCH);
				}
			}
			Collections.reverse(search);

			int size = 1000;
			if(search.size() < 1000){
				size=search.size();
			}
			List<CaseDetailDTO> searchResult = search.subList(0, size);
			for(int i = 0; i < size; i++) {
				searchResult.get(i).setRank(Integer.valueOf(i + 1));
			}

			searchResultDTO.setData(searchResult);
			searchResultDTO.setTotalResult(searchResult.size());
			searchResultDTO.setNumFound(Long.valueOf(searchResult.size()));
			searchResultDTO.setMaxScore(results.getMaxScore());
			searchResultDTO.setStart(results.getStart());
			LOG.info("Search completed.!Closing solr connection.!");
			solr.close();
		} catch (SolrServerException e) {
			LOG.info("solr server connection exception");
		} catch (IOException e) {
			LOG.info("IO  exception");
			e.printStackTrace();
		} finally {
			solr.close();
		}
		LOG.info("Setting the results after all calculations..");
		return searchResultDTO;
	}

	@Override
	public Object getAutoComplete(String name) {
		RestTemplate rest = new RestTemplate();
		String url = solrURL + solrAutoCompelte + name
				+ autoCompleteResponseType;
		LOG.info("Auto complete URL " + url);
		return rest.getForEntity(url, String.class);

	}

	@Override
	public Map<String, List<String>> getSuggestions(String name) {
		CloudSolrClient solr = new CloudSolrClient(zkHostString);
		solr.setDefaultCollection(collection);
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/suggest");
		query.setParam("suggest.q", name);
		QueryResponse response;
		SuggesterResponse resp = null;
		try {
			response = solr.query(query);
			resp = response.getSuggesterResponse();
			resp.getSuggestedTerms();
			solr.close();
		} catch (SolrServerException | IOException e) {
			LOG.error("Exception in getting suggessions!", e);
		} finally {
			try {
				solr.close();
			} catch (IOException e) {
				LOG.error("Exception in getting suggessions!", e);
			}
		}

		return resp.getSuggestedTerms();
	}

	@Override
	public List<SolrStautusDTO> statusOfSolr() {
		return utility.statusOfSolr();
	}

	/**
	 * Returns list of result records each with calculated fields (name match, address match,
	 * father match, total weightage percentage etc.) populated.
	 * 
	 * Those records which have name / address percentage below
	 * corresponding threshold are filtered out from the results list.
	 * 
	 * @param results SOLR results
	 * @param searchQueryInputs Inputs from search query
	 * @return
	 */
	private List<CaseDetailDTO> calculateScore(SolrDocumentList results,
			CalcScoreInputDTO searchQueryInputs) {

		// encode inputs (name, address & father name) from query
		String encodedQryAddr = doubleMetaphoneEncode(searchQueryInputs.getAddress(), true);

		// retrieve all weightages
		Map<String, WeightageDTO> weightages = weightageService.getAllWeightages();
		LOG.debug("All configured weightages:- {}.", weightages);

		ListIterator<SolrDocument> iter = results.listIterator();
		LOG.info("Result size: {} with max score: {}.", results.size(), results.getMaxScore());
		float max = results.getMaxScore();

		List<CaseDetailDTO> search = new ArrayList<CaseDetailDTO>();
		while (iter.hasNext()) {
			SolrDocument solrDocument = iter.next();
			
			String partyname = solrDocument.get("partyname") != null 
							? String.valueOf(solrDocument.get("partyname")) 
							: null;
			String resultName = solrDocument.get("cleaned_name") != null
							? String.valueOf(solrDocument.get("cleaned_name"))
							: null;
			String aliasName = solrDocument.get("alias_name") != null 
							? String.valueOf(solrDocument.get("alias_name"))
							: null;
			String resultFathersName = solrDocument.get("partyfather") != null 
							? String.valueOf(solrDocument.get("partyfather"))
							: null;
			String resultAddress = solrDocument.get("cleaned_address") != null
							? String.valueOf(solrDocument.get("cleaned_address"))
							: null;
			Integer resStateId = solrDocument.get("address_state_id") != null 
							? Integer.valueOf(solrDocument.get("address_state_id").toString())
							: null;
			Integer resDistrictId = solrDocument.get("address_district_id") != null
							? Integer.valueOf(solrDocument.get("address_district_id").toString())
							: null;

			CaseDetailDTO caseDetails = new CaseDetailDTO();
			caseDetails.setPartyId(Integer.valueOf(solrDocument.get("id").toString()));
			caseDetails.setCaseId(solrDocument.get("case_id") != null
							? Integer.valueOf(solrDocument.get("case_id").toString())
							: null);
			caseDetails.setPartyAddress(solrDocument.get("address") != null
							? String.valueOf(solrDocument.get("address"))
							: null);
			caseDetails.setPartyName(partyname);
			caseDetails.setPartyFather(resultFathersName);
			float f = Float.valueOf(solrDocument.get("score").toString());
			float answer = (f / max) * 100;
			String score = String.valueOf(utility.roundTwoDecimals(answer));
			caseDetails.setScore(score.toString());

			// calculate match percentage based upon both name & alias of the record
			// use the one which is heigher
			String finalName = resultName; 
			double nameMatchPercent = compareStrings(searchQueryInputs.getName(), resultName);
			if(aliasName != null && !aliasName.isEmpty()) {
				double aliasMatchPercent = compareStrings(searchQueryInputs.getName(), aliasName);
				if(aliasMatchPercent > nameMatchPercent) {
					finalName = aliasName;
					nameMatchPercent = aliasMatchPercent;
				}
			}
			caseDetails.setNameMatchPercentage(Double.valueOf(nameMatchPercent));

			resultAddress = resultAddress.toLowerCase();
			resultAddress = ABStringUtil.removeMatch(resultAddress, 
						configService.getAddressStopwords());
			String encodedResAddr = doubleMetaphoneEncode(resultAddress, true);
			caseDetails.setAddressMatchPercentage(Double.valueOf(compareStrings(
								encodedQryAddr, encodedResAddr)));

			// filter out records which have less match percentage
			boolean isValidMatch = isValidMatch(caseDetails, searchQueryInputs.getNameThreshold(), 
					searchQueryInputs.getAddrThreshold());
			if(!isValidMatch) {
				continue;
			}

			caseDetails.setBothMatchPercentage(Double.valueOf(compareStrings(
							searchQueryInputs.getName() + " " + encodedQryAddr,
							finalName + " " + encodedResAddr)));

			/**
			 * Check if father's name is empty or not. (In database) If empty:-
			 * Take party name entered and compare it with the party name in
			 * result set. Remove matching words in result name and compare the
			 * resultant string with searched father's name. Calculate the
			 * percentage between the two and display to the user.
			 */
			if (!utility.checkEmpty(searchQueryInputs.getFatherName())) {
				String[] qryFtrNameWords = searchQueryInputs.getFatherName().split("\\s");
				double fatherPer = 0.0;

				// father's name in db is empty
				if (utility.checkEmpty(resultFathersName)) {
					// find middle name by splitting the party name and remove words 
					// matching name in query; use phonetic encoding to match and remove phonetically
					// matching words
					String encodedQryName = doubleMetaphoneEncode(searchQueryInputs.getName(), true);
					List<String> qryNameWordsEncoded = new ArrayList<>(Arrays.asList(encodedQryName.split("\\s")));
					List<String> resultNameWordsEncoded = new ArrayList<>(Arrays.asList(doubleMetaphoneEncode(
							resultName, true).split("\\s")));
					List<Integer> matchingIndices = new ArrayList<>();
					for(int i = 0; i < resultNameWordsEncoded.size(); i++) {
						for (String qryNameWord : qryNameWordsEncoded) {
							if (qryNameWord.equals(resultNameWordsEncoded.get(i))) {
								matchingIndices.add(Integer.valueOf(i));
							}
						}
					}
					String [] resultNameWords = resultName.split("\\s");
					List<String> middleNameWords = new ArrayList<>();
					for (int i = 0; i < resultNameWords.length; i++) {
						if(!matchingIndices.contains(Integer.valueOf(i))) {
							middleNameWords.add(resultNameWords[i]);
						}
					}

					// match middle name words with father name given and calculate match percentage
					if (!middleNameWords.isEmpty()) {
						for (String qryFtrNameWord : qryFtrNameWords) {
							for (String middleNameWord : middleNameWords) {
								double res = compareStrings(middleNameWord, qryFtrNameWord);
								if (res > fatherPer) {
									fatherPer = res;
								}
							}
						}
					}
				} else {
					String[] splitFn = resultFathersName.split("\\s");
					for (String s : splitFn) {
						for (String qryFtrNameWord : qryFtrNameWords) {
							double res = compareStrings(s, qryFtrNameWord);
							if (res > fatherPer) {
								fatherPer = res;
							}
						}
					}
				}

				if (fatherPer < weightages.get(AUTHBRIDGECONSTANT.F_W_T).getWeightage()) {
					fatherPer = 0.0;
				}
				caseDetails.setFatherPercentage(Double.valueOf(fatherPer));
			} else {
				caseDetails.setFatherPercentage(Double.valueOf(0.0));
			}

			CalcWeightageInputDTO calcWeightageInputDTO = new CalcWeightageInputDTO.Builder(
					caseDetails.getNameMatchPercentage(), caseDetails.getAddressMatchPercentage())
					.withFatherPercentage(caseDetails.getFatherPercentage())
					.withStateId(resStateId).withDistriceId(resDistrictId)
					.build();
			caseDetails.setWeightedPercentage(Double.valueOf(getWeightedPercentage(calcWeightageInputDTO,
					 searchQueryInputs, weightages)));

			if(!search.contains(caseDetails)) {
				search.add(caseDetails);
			}
		}
		return search;
	}

	/**
	 * Returns if given case details record is a valid match or not, based upon given name and address match
	 * threshold. If name or address match of the given record are below corresponding threshold, it returns 
	 * false.  
	 * 
	 * @param caseDetailDTO Case details record
	 * @param nameThreshold Name threshold
	 * @param addrThreshold Address threshold
	 * @return True if given case details record is valid; false otherwise
	 */
	private boolean isValidMatch(CaseDetailDTO caseDetailDTO, Float nameThreshold, Float addrThreshold) {
		float nameThresholdVal = nameThreshold != null ? nameThreshold.floatValue() : 0.0F;
		float addrThresholdVal = addrThreshold != null ? addrThreshold.floatValue() : 0.0F;

		boolean isValid = true;
		if(caseDetailDTO.getNameMatchPercentage().floatValue() < nameThresholdVal
				|| caseDetailDTO.getAddressMatchPercentage().floatValue() < addrThresholdVal) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Calculates and returns weighted percentage match for given case details record inputs. Calculated as:
	 * 1) Name weighted percentage = (name match / 100) * name weightage
	 * 2) Address weighted percentage = (address match / 100) * address weightage
	 * 3) Calculate combined weighted percentage for name and address
	 * 4) If father name is provided in query, 
	 *		Father weighted percentage = (father match / 100) * father weightage
	 * 	  If not, add father weightage to "weightage of missing fields"
	 * 5) If state is provided in query and state is present in record,
	 * 		If both match, 
	 * 			State weighted percentage = state weightage
	 * 	  If not, add state weightage to "weightage of missing fields"
	 * 6) If district is provided in query and district is present in record,
	 * 		If both match,  
	 * 			District weighted percentage = district weightage
	 * 	  If not, add district weightage to "weightage of missing fields"
	 * 7) Revise combined weighted percentage for name and address to add weightage of missing fields to name 
	 * and address.
	 * 8) Add combined weighted percents of name and address with other weighted percentages to calculate 
	 * the total weighted percentage.
	 * 
	 * @param recordInputs Object with record level inputs required for weightage calculation
	 * @param searchQueryInputs Object with search query inputs required for weightage calculation
	 * @param weightages Configured weightages
	 * @return Weighted percentage
	 */
	private double getWeightedPercentage(CalcWeightageInputDTO recordInputs, CalcScoreInputDTO searchQueryInputs,
			Map<String, WeightageDTO> weightages) {
		double nameWtPercent = (recordInputs.getNamePercentage().doubleValue() / 100.0) 
				* weightages.get(AUTHBRIDGECONSTANT.N_W).getWeightage();
		double addrWtPercent = (recordInputs.getAddressPercentage().doubleValue() / 100.0) 
				* weightages.get(AUTHBRIDGECONSTANT.A_W).getWeightage(); 
		double nameAddrWeightedPercent = nameWtPercent + addrWtPercent;
		float nameAddrWeightage = weightages.get(AUTHBRIDGECONSTANT.N_W).getWeightage()
				+ weightages.get(AUTHBRIDGECONSTANT.A_W).getWeightage();

		// keep track of weightages of missing fields
		float weightageMissingFields = 0.0F;

		// if father name is not given in query, consider it missing
		double ftrWeightedPercent = 0.0;
		String fatherQuery = searchQueryInputs.getFatherName();
		if(fatherQuery != null && !fatherQuery.isEmpty()) {
			ftrWeightedPercent = (recordInputs.getFatherPercentage().doubleValue() / 100.0) 
					* weightages.get(AUTHBRIDGECONSTANT.F_W).getWeightage();
		} else {
			weightageMissingFields += weightages.get(AUTHBRIDGECONSTANT.F_W).getWeightage();
		}

		// if state is not given in query or not present in record, consider it missing
		double stateWeightedPercent = 0.0;
		Integer stateIdInQuery = searchQueryInputs.getStateId();
		Integer stateIdInRecord = recordInputs.getStateId();
		if(stateIdInQuery != null && stateIdInRecord != null && 
				!stateIdInRecord.equals(Integer.valueOf(0))) {
			if(stateIdInQuery.equals(stateIdInRecord)) {
				stateWeightedPercent = weightages.get(AUTHBRIDGECONSTANT.S_W).getWeightage();
			}
		} else {
			weightageMissingFields += weightages.get(AUTHBRIDGECONSTANT.S_W).getWeightage();
		}

		// if district is not given in query or not present in record, consider it missing
		double distWeightedPercent = 0.0;
		Integer districtIdInQuery = searchQueryInputs.getDistrictId();
		Integer districtIdInRecord = recordInputs.getDistrictId();
		if(districtIdInQuery != null && districtIdInRecord != null && 
				!districtIdInRecord.equals(Integer.valueOf(0))) {
			if(districtIdInQuery.equals(districtIdInRecord)) {
				distWeightedPercent = weightages.get(AUTHBRIDGECONSTANT.D_W).getWeightage();
			}
		} else {
			weightageMissingFields += weightages.get(AUTHBRIDGECONSTANT.D_W).getWeightage();
		}

		// calculate weighted percentage as: 
		// (
		//	(combined name address percentage / combined name address weightage) 
		//	* (combined name address weightage + weightage of missing fields)
		// ) + father weighted percentage + state weighted percentage + district weighted percentage
		double weightedPercent = (
					(nameAddrWeightedPercent / nameAddrWeightage) 
					* (nameAddrWeightage + weightageMissingFields)
				) + ftrWeightedPercent + stateWeightedPercent + distWeightedPercent;
		return utility.roundTwoDecimals(weightedPercent);
	}

	/**
	 * Compares strings using {@link SimilarityCalculator} and returns the similarity between two strings.
	 * 
	 * @param str1
	 * @param str2
	 * @return Similarity percentage between the strings
	 */
	private double compareStrings(String str1, String str2) {
		return utility.roundTwoDecimals(similarityCalculator.compareStrings(str1, str2));
	}

	/**
	 * Splits given string into an array of words (using space).
	 * After splitting, encodes each word using {@link DoubleMetaphone} encoding.
	 * If given string is null or empty, returns it as-is.
	 * 
	 * @param str Input string
	 * @param retainNumbers Flag to ensure that numbers in given string are maintained as-is in 
	 * encoded string
	 * @return Encoded string
	 */
	private String doubleMetaphoneEncode(String str, boolean retainNumbers) {
		final DoubleMetaphone encoder = new DoubleMetaphone();
		if(str == null || str.isEmpty()) {
			return str;
		}

		StringBuilder encodedStrBuilder = new StringBuilder();

		// Tokenize the string and put the tokens/words into an array
		String[] words = str.split("\\s");

		// For each word
		for (int w = 0; w < words.length; w++) {
			// ignore empty strings
			if(words[w] == null || words[w].isEmpty()) {
				continue;
			}

			String encodedWord;
			if (retainNumbers && ABStringUtil.containsNumbers(words[w])) {
				encodedWord = words[w];
			} else {
				// encode word; if encoding returns empty, skip the word
				encodedWord = encoder.doubleMetaphone(words[w]);
				if(encodedWord.isEmpty()) {
					continue;
				}
			}

			encodedStrBuilder.append(encodedWord);
			if(w < words.length - 1) {
				encodedStrBuilder.append(" ");
			}
		}

		String encodedString = encodedStrBuilder.toString();
		LOG.debug("Encoded word {} to {}.", str, encodedString);
		return encodedString;
	}

	/**
	 * 
	 */
	@Override
	public void recordCheckIDMappings(Integer checkId, Object data) {
		if(data instanceof List <?>) {                                                // List<CaseDetailDTO>
			List<Object> listOfObject = (List<Object>)data;
			if(listOfObject.size() > 0) {
				if(listOfObject.get(0) instanceof CaseDetailDTO) {
					List<CaseDetailDTO> listOfCaseDetailDTO = (List<CaseDetailDTO>)data;
					for(CaseDetailDTO caseDetailDTO : listOfCaseDetailDTO) {
						addCheckId(checkId, caseDetailDTO.getPartyId());
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param checkId
	 * @param integer
	 */
	private void addCheckId(Integer checkId, Integer integer) {
					
			CheckIdPartyIdMapper checkIdPartyIdMapper = new CheckIdPartyIdMapper();
			checkIdPartyIdMapper.setPartyId(integer);
			checkIdPartyIdMapper.setCheckId(checkId);
			checkPartyMappingService.setCheckPartyMapping(checkIdPartyIdMapper);
		
	}
	
	
//	@Override
//	public void setMatchedStatus(MatchedStatusUpdateDTO matchedStatusUpdateDTO) {
//		CheckIdPartyIdMapper checkIdPartyIdMapper = checkPartyMappingService.findByCheckIdAndPartyId(matchedStatusUpdateDTO.getCheckId(), matchedStatusUpdateDTO.getPartyId());
//		checkIdPartyIdMapper.setStatusMatched(1);
//		checkPartyMappingService.setCheckPartyMapping(checkIdPartyIdMapper);
//	}
	@Override
	public void setMatchedStatus(MatchedStatusUpdateDTO matchedStatusUpdateDTO) {
		for(Integer partyId : matchedStatusUpdateDTO.getPartyId()) {
			setStatus(matchedStatusUpdateDTO.getCheckId(),partyId);
		}
	}
	
	private void setStatus(Integer checkId, Integer partyId) {
		CheckIdPartyIdMapper checkIdPartyIdMapper = checkPartyMappingService.findByCheckIdAndPartyId(checkId, partyId);
		checkIdPartyIdMapper.setStatusMatched(1);
		checkPartyMappingService.setCheckPartyMapping(checkIdPartyIdMapper);
	}
	
}
