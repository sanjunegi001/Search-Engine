package com.authbridge.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.authbridge.DTO.ManageResourceResponse;
import com.authbridge.DTO.SolrStautusDTO;
import com.authbridge.service.AbbreviationStopwordService;
import com.authbridge.service.StatusInfoService;
import com.authbridge.utility.Utility;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AbbreviationStopwordServiceImpl implements AbbreviationStopwordService {

	
	@Value("${solr.url}")
	private String solrURL;
	
	@Value("${solr.abbriviation}")
	private String abbriviation;
	
	@Value("${solr.alias}")
	private String alias;
	
	@Value("${solr.stopword}")
	private String stopword;
	
	@Value("${solr.collection}")
	private String collection;
	
	@Value("${solr.zkHostString}")
	private String zkHostString;
	
	@Autowired
	private Utility utility;
	
	@Autowired
	private StatusInfoService statusInfoService;
	
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(AbbreviationStopwordServiceImpl.class);
	
	//String baseURl = solrURL;
	/**
	 * To get all the abbreviations. 
	 * This method will check for the active node and get all the 
	 * abbreviation details to be displayed to the user.
	 */
	@Override
	public ManageResourceResponse getAbbrivaitions(){
		
	//	statusInfoService.reloadCollection();
		
	//	CloudSolrClient solr = new CloudSolrClient(zkHostString); 
	//	solr.setDefaultCollection("authbridge");
	//	solr.connect();
		LOG.info("Connected to active node of solr to get all abbreviations..!"+getActiveNode());
	//	HttpSolrClient solr = new HttpSolrClient(solrURL);
		HttpSolrClient solr = new HttpSolrClient(getActiveNode());
		  
		SolrQuery query = new SolrQuery();
		query.setRequestHandler(abbriviation);
		ManageResourceResponse manageResourceResponse = new ManageResourceResponse();
		QueryResponse response=null;
		try{
			 response = solr.query(query);
			 LOG.info("Response header status : "+response.getResponseHeader());
			 ObjectMapper mapper =  new ObjectMapper();
			 LOG.info("Response header : "+response.getResponseHeader());
			 manageResourceResponse.setStatus(String.valueOf(response.getResponseHeader().get("status")));
			 manageResourceResponse.setQtime(String.valueOf(response.getResponseHeader().get("QTime")));
			 manageResourceResponse.setSynonymMappings(mapper.writeValueAsString(response.getResponse().get("synonymMappings")));
			
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try {
				solr.close();
			} catch (IOException e) {
				LOG.info("Excepion in closing solr connection");
			}
		}
		
		
		return manageResourceResponse ;
	}
	

	/**
	 * To set the abbreviations from UI. Admin user can create abbreviations.
	 * This method will save the newly added abbreviations and reload the collection.
	 */
	public String setAbbrivaitions(Map<String , List<String>> map){
		String baseURl = utility.manageResourceURL();
		RestTemplate restService = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(map, headers);
	    ResponseEntity<String> response  = restService.exchange(baseURl+abbriviation, HttpMethod.PUT ,requestEntity , String.class);
	    response.getHeaders().getLocation();
	    response.getStatusCode();
	    LOG.info("Abbreviations are set. Reloading collections.");
	    statusInfoService.reloadCollection();
	    String responseBody = response.getBody();
	  
	    return responseBody;
	}
	/**
	 * To delete the abbreviations by the admin user.
	 */
	@Override
	public void deleteAbbrivaitions(String key) {
		String baseURl = utility.manageResourceURL();
		RestTemplate restService = new RestTemplate();
		restService.delete(baseURl+abbriviation+"/"+key);
	    LOG.info("Delete Abbreviations. Reloading collections.");
		statusInfoService.reloadCollection();
	}
	/**
	 * To get the aliases by admin user.
	 * Request will check the active node and get all the aliases
	 */
	@Override
	public ManageResourceResponse getAliases(){
	//	statusInfoService.reloadCollection();
	//	CloudSolrClient solr = new CloudSolrClient(zkHostString); 
	//	solr.setDefaultCollection("authbridge");
	//	solr.connect();
		LOG.info("Connected to active node of solr to get all alias..!"+getActiveNode());

	//	HttpSolrClient solr = new HttpSolrClient(solrURL);
		HttpSolrClient solr = new HttpSolrClient(getActiveNode());

		SolrQuery query = new SolrQuery();
		query.setRequestHandler(alias);
		ManageResourceResponse manageResourceResponse = new ManageResourceResponse();
		QueryResponse response=null;
		try{
			 response = solr.query(query);
			 LOG.info("Response header status : "+response.getResponseHeader());
			 ObjectMapper mapper =  new ObjectMapper();
			 LOG.info("Response header : "+response.getResponseHeader());
			 manageResourceResponse.setStatus(String.valueOf(response.getResponseHeader().get("status")));
			 manageResourceResponse.setQtime(String.valueOf(response.getResponseHeader().get("QTime")));
			 manageResourceResponse.setSynonymMappings(mapper.writeValueAsString(response.getResponse().get("synonymMappings")));
			
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try {
				solr.close();
			} catch (IOException e) {
				LOG.info("Excepion in closing solr connection");
			}
		}
		
		
		return manageResourceResponse ;
	}
	/**
	 * To set the aliases from UI by the admin user.
	 * Request is sent to solr and collection is reloaded once aliases is persisted.
	 */
	@Override
	public String setAliases(String[] list){
		String baseURl = utility.manageResourceURL();
		RestTemplate restService = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(list, headers);
	    ResponseEntity<String> response  = restService.exchange(baseURl+alias, HttpMethod.PUT ,requestEntity , String.class);
	    response.getHeaders().getLocation();
	    response.getStatusCode();
	    LOG.info("Aliases are set. Reloading collections.");
	    statusInfoService.reloadCollection();
	    String responseBody = response.getBody();
	    return responseBody;
	}
	/**
	 * To delete the alias by the admin user.
	 */
	@Override
	public void deleteAlias(String value) {
		String baseURl = utility.manageResourceURL();
		List<String> tokens = Arrays.asList(value.split(","));
		for (String string : tokens) {
			RestTemplate restService = new RestTemplate();
			String url = baseURl+alias+"/"+string;
			restService.delete(url);
		}
	    LOG.info("Deleting alias. Reloading collections.");
		statusInfoService.reloadCollection();
		
	}
	/**
	 * To get all the stop words. Will get the active node and get all the stopwords
	 */
	@Override
	public ManageResourceResponse getStopWords() {
	//	statusInfoService.reloadCollection();
		
	//	CloudSolrClient solr = new CloudSolrClient(zkHostString); 
	//	solr.setDefaultCollection("authbridge");
	//	solr.connect();
		LOG.info("Connected to active node of solr to get all stopwords..!"+getActiveNode());

		
		//HttpSolrClient solr = new HttpSolrClient("http://61.16.238.187:8985/solr/authbridge");
		//System.out.println(solr.getBaseURL()+"-------------");
		HttpSolrClient solr = new HttpSolrClient(getActiveNode());
	  
		SolrQuery query = new SolrQuery();
		query.setRequestHandler(stopword);
		ManageResourceResponse manageResourceResponse = new ManageResourceResponse();
		QueryResponse response=null;
		try{
			 response = solr.query(query);
			 LOG.info("Response header status : "+response.getResponseHeader());
			 ObjectMapper mapper =  new ObjectMapper();
			 LOG.info("Response header : "+response.getResponseHeader());
			 manageResourceResponse.setStatus(String.valueOf(response.getResponseHeader().get("status")));
			 manageResourceResponse.setQtime(String.valueOf(response.getResponseHeader().get("QTime")));
			 manageResourceResponse.setWordSet(mapper.writeValueAsString(response.getResponse().get("wordSet")));
			
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try {
				solr.close();
			} catch (IOException e) {
				LOG.info("Excepion in closing solr connection");
			}
		}
		return manageResourceResponse;
	}

	/**
	 * To set the stopwords by the admin user from admin portal UI.
	 */
	@Override
	public String setStopWords(String stopWord) {
		String baseURl = utility.manageResourceURL();
		RestTemplate restService = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    String[] array = stopWord.split(",");
	    HttpEntity<?> requestEntity = new HttpEntity<Object>(array, headers);
	    ResponseEntity<String> response  = restService.exchange(baseURl+stopword, HttpMethod.PUT ,requestEntity , String.class);
	    response.getHeaders().getLocation();
	    response.getStatusCode();
	    LOG.info("Stopwords are set. Reloading collections.");
	    statusInfoService.reloadCollection();
	    String responseBody = response.getBody();
	    return responseBody;
	}

	/**
	 * To delete the stopwords
	 */
	@Override
	public void deleteStopWords(String stopWord) {
		String baseURl = utility.manageResourceURL();
		RestTemplate restService = new RestTemplate();
		restService.delete(baseURl+stopword+"/"+stopWord);
	    LOG.info("Deleting stopwords. Reloading collections.");
		statusInfoService.reloadCollection();
	}
	
	/**
	 * To get the active solr node.
	 * @return
	 */
	private String getActiveNode(){
		SolrStautusDTO status = utility.getFirstActiveNode();
		return status.getBaseURl()+"/"+ collection;
	}
	

}
