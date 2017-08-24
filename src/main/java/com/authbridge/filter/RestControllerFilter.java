package com.authbridge.filter;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;


public class RestControllerFilter {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(RestControllerFilter.class);
	
	@Before("execution(* com.authbridge.restcontroller.getAllResults(..))")
	public void checkForValidRequestBody() throws Exception{
		LOG.info("checking for validation");
	}
}
