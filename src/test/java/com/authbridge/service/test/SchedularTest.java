package com.authbridge.service.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.constant.AuthBridgeEnum;
import com.authbridge.model.ImportScheduler;
import com.authbridge.service.SchedulingService;
import com.authbridge.utility.Utility;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class SchedularTest {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SchedularTest.class);
		
	@Autowired
	private SchedulingService schedulingService;
	
	@Autowired
	private Utility utility;
	
	
	@Test
	public void dateTest(){
		String date = "2016-06-29 19:50";
		LOG.info("adte : "+utility.convertToDate(date));
		
	}
	
	@Test
	public void changeDateFormatTest(){
		Date date = new Date();
		LOG.info("adte : "+date);
		SimpleDateFormat dateformat = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
		LOG.info("change date format " +dateformat.format(date) );
		
	}
	
	@Test
	public void getRowTest(){
		LOG.info("adte : "+schedulingService.getRow());
	}
	
	@Test
	public void getImportTypeTest(){
		LOG.info("adte : "+schedulingService.findOneByType(AuthBridgeEnum.Type.Delta_Import.toString()));
	}
	
	public void saveTest(){
		ImportScheduler schedular = new ImportScheduler();
		schedular.setDateTime(new Date());
		schedular.setRecurring(AuthBridgeEnum.Recurring.Monthly.toString());
		schedular.setType(AuthBridgeEnum.Type.Delta_Import.toString());
		Integer id = schedulingService.saveOrUpdateScheduling(schedular);
		LOG.info("saved with id : "+id);
	}
	
	public void updateTest(){
		ImportScheduler schedular = new ImportScheduler();
	}
}
