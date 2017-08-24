package com.authbridge.service;

import java.util.List;
import java.util.Map;

import com.authbridge.DTO.ManageResourceResponse;

public interface AbbreviationStopwordService {

	public ManageResourceResponse getAbbrivaitions();
	
	public String setAbbrivaitions(Map<String, List<String>> map);
	
	public void deleteAbbrivaitions(String key);
	
	public ManageResourceResponse getStopWords();
	
	public String setStopWords(String stopWord);
	
	public void deleteStopWords(String stopWord);
	
	public ManageResourceResponse getAliases();
	
	public String setAliases(String[] list);
	
	void deleteAlias(String value);
}
