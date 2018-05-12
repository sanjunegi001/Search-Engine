package com.authbridge.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authbridge.DTO.ManageResourceResponse;
import com.authbridge.DTO.MatchedStatusUpdateDTO;
import com.authbridge.DTO.SearchCaseDetailDTO;
import com.authbridge.DTO.SearchResultDTO;
import com.authbridge.DTO.WeightageDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.service.AbbreviationStopwordService;
import com.authbridge.service.FetchingDataService;
import com.authbridge.service.ModificationService;
import com.authbridge.service.SchedulingService;
import com.authbridge.service.WeightageService;

@RestController
@RequestMapping(value = "/rest")
public class FetchDataRestController {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(FetchDataRestController.class);
	
	@Autowired
	private FetchingDataService fetchingDataService;
	
	@Autowired
	private WeightageService weightageService;
	
	@Autowired
	private SchedulingService schedulingService;
	
	@Autowired
	private ModificationService modificationService;
	
	@Autowired
	private AbbreviationStopwordService abbreviationStopwordService;

	/**
	 * To get the search results by sending the necessary params set in searchCaseDetailDTO
	 * @param searchCaseDetailDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/party", method = RequestMethod.POST)
	public RestControllerResponse getAllResults(@RequestBody final SearchCaseDetailDTO searchCaseDetailDTO) throws Exception{
		LOG.info("Rest to get all data");
		LOG.info("search criteria : "+searchCaseDetailDTO.getAddress()
										+" name : "	+searchCaseDetailDTO.getName()
												+" : sstar : "+searchCaseDetailDTO.getStart()
													+ " count : "+searchCaseDetailDTO.getCount());
		
		
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(fetchingDataService.getAllResults(searchCaseDetailDTO));
		return response;
	}
	
	@RequestMapping(value = "/party", method = RequestMethod.GET)
	public RestControllerResponse getAllResults() throws Exception{
		LOG.info("Rest to get all data");
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(fetchingDataService.getAllResults());
		return response;
	}
	/**
	 * To get the case details using the REST API which is part of the requirement
	 * @param name
	 * @param addr
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/casedetails", method = RequestMethod.GET)
//	public RestControllerResponse getAllResultsNew(@RequestParam("name") String name,@RequestParam("address") String addr) throws Exception{
//		LOG.info("Rest to get all data when name and address parameters are passed.");
//		SearchCaseDetailDTO searchCaseDetailDTO = new SearchCaseDetailDTO();
//		searchCaseDetailDTO.setName(name);
//		searchCaseDetailDTO.setAddress(addr);
//		RestControllerResponse response = new RestControllerResponse();
//		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
//		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
//		response.setData(fetchingDataService.getAllResults(searchCaseDetailDTO));
//		return response;
//	}
	/**
	 * To get the case details using the REST API which is part of the requirement
	 * @param name
	 * @param addr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/casedetails", method = RequestMethod.POST)
	public RestControllerResponse getAllResultCase(@RequestBody SearchCaseDetailDTO searchCaseDetailDTO) throws Exception{
		LOG.info("Rest to get all data when name and address parameters are passed.");
		//SearchCaseDetailDTO searchCaseDetailDTO = new SearchCaseDetailDTO();
		//searchCaseDetailDTO.setName(name);
		//searchCaseDetailDTO.setAddress(addr);
		ManageResourceResponse manageResourceResponse =	abbreviationStopwordService.getAliases();
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setCheckId(searchCaseDetailDTO.getCheckId());
		response.setData(fetchingDataService.getAllResultsDemo(searchCaseDetailDTO,manageResourceResponse));
		fetchingDataService.recordCheckIDMappings(searchCaseDetailDTO.getCheckId(), ((SearchResultDTO)response.getData()).getData());
		return response;
	}
	/**
	 * To set matched status to 1 against partyId and checkId where match happened 100%
	 * @param matchedStatusUpdateDTO
	 * @return
	 */
	@RequestMapping(value = "/matchupdate", method = RequestMethod.POST)
	public RestControllerResponse setMatchedStatus(@RequestBody MatchedStatusUpdateDTO matchedStatusUpdateDTO) {
		fetchingDataService.setMatchedStatus(matchedStatusUpdateDTO);
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		
		return response;
	}
	
	/**
	 * To get the weightage information to be displayed to the admin user in dashboard
	 * @return weightage response
	 * @throws Exception
	 */
	@RequestMapping(value = "/weightage", method = RequestMethod.GET)
	public RestControllerResponse getWeightage() throws Exception{
		LOG.info("Rest to get all weightage");
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(weightageService.getWeightagesList());
		return response;
	}
	/**
	 * To set the weightage if there are any changes to the weightage details.
	 * @param weightageDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/weightage", method = RequestMethod.POST)
	public RestControllerResponse setWeightage(@RequestBody List<WeightageDTO> weightageDTO) throws Exception{
		LOG.info("Rest to get all weightage" + weightageDTO);
		for (WeightageDTO weightage : weightageDTO) {
			weightageService.setWeightage(weightage);
		}
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		//response.setData(weightageService.getNameAddressWeight());
		return response;
	}
	
	@RequestMapping(value= "/autocomplete/{name}" , method = RequestMethod.GET)
	public RestControllerResponse autocomplete(@PathVariable("name") String name){
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(fetchingDataService.getSuggestions(name));
		return response;
		
	}
	/**
	 * Scheduling details to be fetched from the database and displayed to the admin
	 * @return scheduling response
	 */
	@RequestMapping(value= "/scheculing" , method = RequestMethod.GET)
	public RestControllerResponse scheculing(){
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(schedulingService.getAllSchedulerTypes());
		return response;
	}
	
	@RequestMapping(value= "/modifications" , method = RequestMethod.GET)
	public RestControllerResponse modifications(){
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(modificationService.findAllDTO());
		return response;
	}
	/**
	 * To get the status of the solr server to be displayed as health info
	 * in the dashboard
	 * @return
	 */
	@RequestMapping(value= "/server/status" , method = RequestMethod.GET)
	public RestControllerResponse serverStatus(){
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(fetchingDataService.statusOfSolr());
		return response;
	}
}
