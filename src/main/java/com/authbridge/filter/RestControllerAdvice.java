package com.authbridge.filter;


import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.restcontroller.RestControllerResponse;


@ControllerAdvice
public class RestControllerAdvice {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(RestControllerAdvice.class);
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public RestControllerResponse handleException(Exception e){
		RestControllerResponse response = new RestControllerResponse();
		response.setCode(AUTHBRIDGECONSTANT.REST.ERROR_CODE);
		response.setStatus(e.getMessage());
		return response;
	}
	
	
	
}
