package com.authbridge.restcontroller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.service.ConfigService;

@RestController
@RequestMapping(value = "/rest")
public class FetchDistrictStateController {
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(FetchDistrictStateController.class);
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * To get the district and state related information using rest call.
	 * Fetch all the district and state results from the database lookup
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/districtState", method = RequestMethod.GET)
	public RestControllerResponse getAllResults() throws Exception{
		LOG.info("Rest to get all data");
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.SUCCESS_CODE);
		response.setStatus(AUTHBRIDGECONSTANT.REST.SUCCESS);
		response.setData(configService.getStateList());
		LOG.info("Getting all districts: "+configService.getStateList().size());
		return response;
	}
	
}
