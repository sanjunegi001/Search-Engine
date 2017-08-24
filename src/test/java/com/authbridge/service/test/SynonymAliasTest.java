package com.authbridge.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.service.AbbreviationStopwordService;
import com.authbridge.utility.Utility;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
public class SynonymAliasTest {
	
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SynonymAliasTest.class);
	
	@Autowired
	private AbbreviationStopwordService abbreviationStopwordService;
	
	@Test
	public void getSynonymTest(){
		LOG.info("Abbr Response : "+abbreviationStopwordService.getAbbrivaitions());
	}
	
	

}
