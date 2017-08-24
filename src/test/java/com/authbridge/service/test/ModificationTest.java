package com.authbridge.service.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.model.Modification;
import com.authbridge.service.ModificationService;
import com.authbridge.service.impl.ModificationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class ModificationTest {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(ModificationTest.class);
	
	@Autowired
	private ModificationService modificationService;
	
	@Test
	public void saveModification(){
		Modification modification = new Modification();
		modification.setModifiedOn(new Date());
		modification.setOperation(AUTHBRIDGECONSTANT.OPERATION.EDIT);
		modification.setScreen(AUTHBRIDGECONSTANT.SCREEN.ALIAS);
		modificationService.persist(modification);
	}
	
	
	@Test
	public void findByAliasName(){
		LOG.info("The screen Object : "+modificationService.findByScreen(AUTHBRIDGECONSTANT.SCREEN.STOPWORD));
	}
	
	@Test
	public void findByOperationAndScreenTest(){
		LOG.info("The screen Object : "+modificationService.findByOperationAndScreen(AUTHBRIDGECONSTANT.OPERATION.DELTA_IMPORT ,AUTHBRIDGECONSTANT.SCREEN.SCHEDULING ));
	}
	
	@Test
	public void saveOrUpdate(){
		Modification modification = modificationService.findByScreen(AUTHBRIDGECONSTANT.SCREEN.STOPWORD);
		if(modification == null){
			modification = new Modification();
			modification.setScreen(AUTHBRIDGECONSTANT.SCREEN.STOPWORD);
			modification.setOperation(AUTHBRIDGECONSTANT.OPERATION.EDIT);
		}
		
		modification.setModifiedOn(new Date());
		modificationService.persist(modification);
	}
	
}
