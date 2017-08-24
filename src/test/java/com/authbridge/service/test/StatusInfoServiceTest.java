package com.authbridge.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.service.StatusInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class StatusInfoServiceTest {
	
	@Autowired
	private StatusInfoService statusInfoService;
	
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(StatusInfoServiceTest.class);
	
	@Test
	public void testing(){
		statusInfoService.reloadCollection();
		LOG.info("Successful.");
	}
}
