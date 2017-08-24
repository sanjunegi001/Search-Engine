package com.authbridge.restcontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authbridge.DTO.SynonymMappingDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.service.AbbreviationStopwordService;
import com.authbridge.service.ModificationService;

@RestController
@RequestMapping(value = "/rest")
public class SynonymStopwordRestController {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SynonymStopwordRestController.class);
	
	@Autowired
	private AbbreviationStopwordService abbreviationStopwordService;
	
	@Autowired
	private ModificationService modificationService;
	
	/**
	 * Rest API method called to get abbreviations to be displayed to the admin user.
	 * @return response
	 */
	@RequestMapping(value="/synonym", method = RequestMethod.GET)
	public RestControllerResponse getAbbrivaitions(){
		LOG.info("Getting all the abbreviations!!");
		RestControllerResponse response = new RestControllerResponse();
		response.setData(abbreviationStopwordService.getAbbrivaitions());
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to set abbreviations which are created by the admin user.
	 * @param synonymMappingDTO
	 * @return response
	 */
	@RequestMapping(value="/synonym", method = RequestMethod.PUT)
	public RestControllerResponse setAbbrivaitions(@RequestBody SynonymMappingDTO synonymMappingDTO){
		
		LOG.info("calling to save Key : "+synonymMappingDTO.getKey() + " its value : "+synonymMappingDTO.getValue() );
		Map<String , List<String>> map = new HashMap<String , List<String>>();
		map.put(synonymMappingDTO.getKey() , Arrays.asList(synonymMappingDTO.getValue()));
		String solrResponse = abbreviationStopwordService.setAbbrivaitions(map);
		RestControllerResponse response = new RestControllerResponse();
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.ABBR, AUTHBRIDGECONSTANT.OPERATION.ADD);
		response.setData(solrResponse);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to set/update the existing abbreviations which are created by the admin user.
	 *  @param synonymMappingDTO
	 * @return response
	 */
	@RequestMapping(value="/synonym", method = RequestMethod.POST)
	public RestControllerResponse updateAbbrivaitions(@RequestBody SynonymMappingDTO synonymMappingDTO){
		
		LOG.info("calling to update  Key : "+synonymMappingDTO.getKey() + " its value : "+synonymMappingDTO.getValue() );
		
		LOG.info("deleting with   Key :"+synonymMappingDTO.getKey());
		abbreviationStopwordService.deleteAbbrivaitions(synonymMappingDTO.getKey());
		LOG.info("creating  with   Key :"+synonymMappingDTO.getKey());
		Map<String , List<String>> map = new HashMap<String , List<String>>();
		map.put(synonymMappingDTO.getKey() , Arrays.asList(synonymMappingDTO.getValue()));
		String solrResponse = abbreviationStopwordService.setAbbrivaitions(map);
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.ABBR, AUTHBRIDGECONSTANT.OPERATION.EDIT);
		RestControllerResponse response = new RestControllerResponse();
		response.setData(solrResponse);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to delete the abbreviations
	 * @param synonym
	 * @return response
	 */
	@RequestMapping(value="/synonym", method = RequestMethod.DELETE)
	public RestControllerResponse deleteAbbrivaitions(@RequestBody String synonym){
		RestControllerResponse response = new RestControllerResponse();
		abbreviationStopwordService.deleteAbbrivaitions(synonym);
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.ABBR, AUTHBRIDGECONSTANT.OPERATION.DELETE);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to get the aliases
	 * @return response
	 */
	@RequestMapping(value="/alias", method = RequestMethod.GET)
	public RestControllerResponse getAliases(){
		LOG.info("Getting all the abbreviations!!");
		RestControllerResponse response = new RestControllerResponse();
		response.setData(abbreviationStopwordService.getAliases());
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to set aliases which are created by the admin user.
	 * @param synonymMappingDTO
	 * @return response
	 */
	@RequestMapping(value="/alias", method = RequestMethod.PUT)
	public RestControllerResponse setAliases(@RequestBody SynonymMappingDTO synonymMappingDTO){
		
		LOG.info("creating Aliase with Key : "+synonymMappingDTO.getKey() + " its value : "+synonymMappingDTO.getValue() );
		String[] array = synonymMappingDTO.getValue().split(",");
		String solrResponse = abbreviationStopwordService.setAliases(array);
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.ALIAS, AUTHBRIDGECONSTANT.OPERATION.ADD);
		RestControllerResponse response = new RestControllerResponse();
		response.setData(solrResponse);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to update the aliases created by the admin user.
	 * @param synonymMappingDTO
	 * @return response
	 */
	@RequestMapping(value="/alias", method = RequestMethod.POST)
	public RestControllerResponse updateAliases(@RequestBody SynonymMappingDTO synonymMappingDTO){
		
		LOG.info("creating Aliase with  values : "+synonymMappingDTO.getKey() + " its value : "+synonymMappingDTO.getValue() );
		
		LOG.info("deleting Aliase with  values : ");
		abbreviationStopwordService.deleteAlias(synonymMappingDTO.getKey());
		LOG.info("creating Aliase with  values : ");
		String[] array = synonymMappingDTO.getValue().split(",");
		String solrResponse = abbreviationStopwordService.setAliases(array);
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.ALIAS, AUTHBRIDGECONSTANT.OPERATION.EDIT);
		RestControllerResponse response = new RestControllerResponse();
		response.setData(solrResponse);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to delete the aliases.
	 * @param alias
	 * @return response
	 */
	@RequestMapping(value="/alias", method = RequestMethod.DELETE)
	public RestControllerResponse deleteAlias(@RequestBody String alias){
		LOG.info("Deleting alias : "+alias);
		RestControllerResponse response = new RestControllerResponse();
		abbreviationStopwordService.deleteAlias(alias);
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.ALIAS, AUTHBRIDGECONSTANT.OPERATION.DELETE);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to get all the stopwords to be displayed to the admin user.
	 * @return response
	 */
	@RequestMapping(value="/stopword", method = RequestMethod.GET)
	public RestControllerResponse getStopWord(){
		LOG.info("Getting all the abbreviations!!");
		RestControllerResponse response = new RestControllerResponse();
		response.setData(abbreviationStopwordService.getStopWords());
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
	/**
	 * Rest API method called to set stopwords which are created by the admin user.
	 * @param stopword
	 * @return response
	 */
	@RequestMapping(value="/stopword/{stopword}", method = RequestMethod.POST)
	public RestControllerResponse setStopWord(@PathVariable("stopword") String stopword){
		RestControllerResponse response = new RestControllerResponse();
		response.setData(abbreviationStopwordService.setStopWords(stopword));
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.STOPWORD, AUTHBRIDGECONSTANT.OPERATION.ADD);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
		
	}
	/**
	 * Rest API method called to delete stopwords which are created by the admin user.
	 * @param stopword
	 * @return response
	 */
	@RequestMapping(value="/stopword/{stopword}", method = RequestMethod.DELETE)
	public RestControllerResponse deleteStopWord(@PathVariable("stopword") String stopword){
		RestControllerResponse response = new RestControllerResponse();
		abbreviationStopwordService.deleteStopWords(stopword);
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.STOPWORD, AUTHBRIDGECONSTANT.OPERATION.DELETE);
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		return response;
	}
}
