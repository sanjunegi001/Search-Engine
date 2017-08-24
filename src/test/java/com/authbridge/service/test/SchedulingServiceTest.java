package com.authbridge.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.model.ImportScheduler;
import com.authbridge.service.SchedulingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class SchedulingServiceTest {
	@Autowired
	private SchedulingService schedulingService;
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SchedulingServiceTest.class);
	
	@Test
	public void test(){
		ImportScheduler is = schedulingService.findOneByType("FULL_INDEXING");
		LOG.info("Executed successfully..!"+is.getSchedulerId()+"   "+is.getRecurring()+"      "+is.getDateTime());
	}
	
	@Test
	public void importing(){
		LOG.info("Calling fullindexing to check if running properly..!");
		schedulingService.fullIndexing();
		LOG.info("Successfull..");
	}
	
}
