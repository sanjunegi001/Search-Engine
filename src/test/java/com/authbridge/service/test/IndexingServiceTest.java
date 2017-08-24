package com.authbridge.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.service.IndexingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class IndexingServiceTest {
	@Autowired
	private IndexingService indexingService;
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(IndexingServiceTest.class);
	
	@Test
	public void getCount(){
		long count = indexingService.totalCount();
		LOG.info("Total count of records from database:-"+count);
	}
	
	@Test
	public void testing(){
		LOG.info("Started full indexing");
		indexingService.fullIndexing();
		LOG.info("Successfull..!");
	}
	
	
}
