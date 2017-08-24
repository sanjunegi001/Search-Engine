package com.authbridge.service.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.authbridge.Application;
import com.authbridge.service.AbbreviationStopwordService;
import com.authbridge.service.FetchingDataService;
import com.authbridge.utility.Utility;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class FetchingDataServiceTest {
	
	@Autowired
	private FetchingDataService fetchingDataService;
	
	@Autowired
	private Utility utility;
	
	@Autowired
	private AbbreviationStopwordService abbreviationStopwordService;
	
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(FetchingDataServiceTest.class);

	
	@Test
	public void testStatusOfSolr(){
		utility.statusOfSolr();
	}
	
	@Test
	public void testStatusOfActiveSolr(){
		utility.getFirstActiveNode();
	}
	
	@Test
	public void setAbbrivaitions() {
		try {
			Map<String,List<String>> map = new java.util.HashMap<String,List<String>>();
			String s = "ku,km,km.,my";
			List<String> list = Arrays.asList(s);
			map.put("kumar", list);
			System.out.println(abbreviationStopwordService.setAbbrivaitions(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void setAliases() {
		try {
			String s = "ku,km,km.,my";
			String[] list = s.split(",");
			System.out.println(abbreviationStopwordService.setAliases(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
