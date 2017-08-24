package com.authbridge.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.DTO.WeightageDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.service.FetchingDataService;
import com.authbridge.service.WeightageService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class WeightageTest {
	@Autowired
	private FetchingDataService fetchingDataService;

	@Autowired
	private WeightageService weightageService;

	private static Logger LOG = org.slf4j.LoggerFactory
			.getLogger(WeightageTest.class);

	
	@Test
	public void getWeightageByName(){
		WeightageDTO wp = weightageService.getWeightage(AUTHBRIDGECONSTANT.F_W_T);
		LOG.info("Weightage {}", wp);
	}
}
