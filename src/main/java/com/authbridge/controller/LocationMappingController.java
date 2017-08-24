package com.authbridge.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocationMappingController {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(LocationMappingController.class);
	
	@RequestMapping({"/dashboard"})
	public String getHomePage(Model model) {
		LOG.info("setting abbreviations");
		return "dashboard";
	}
	
	@RequestMapping({"/403"})
	public String getAccessDenied(Model model) {
		LOG.info("Access denied page");
		return "403";
	}
	
	
}
