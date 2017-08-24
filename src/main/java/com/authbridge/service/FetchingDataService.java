package com.authbridge.service;

import java.util.List;
import java.util.Map;

import com.authbridge.DTO.MatchedStatusUpdateDTO;
import com.authbridge.DTO.SearchCaseDetailDTO;
import com.authbridge.DTO.SearchResultDTO;
import com.authbridge.DTO.SolrStautusDTO;


public interface FetchingDataService {
	/*
	 * Below method is used to get the Solr results for given name and address
	 */
	public SearchResultDTO getAllResults() throws Exception;
	
	public SearchResultDTO getAllResults(SearchCaseDetailDTO searchCaseDetailDTO) throws Exception;

	public Object getAutoComplete(String name);
	
	public Map<String, List<String>> getSuggestions(String name);

	public List<SolrStautusDTO> statusOfSolr();

	public void recordCheckIDMappings(Integer checkId, Object data);
	
	public void setMatchedStatus(MatchedStatusUpdateDTO matchedStatusUpdateDTO);
	


}
