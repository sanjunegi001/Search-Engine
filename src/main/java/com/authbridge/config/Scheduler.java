package com.authbridge.config;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.service.ModificationService;

@Component
public class Scheduler {
	private static Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Scheduler.class);
	
	@Value("${solr.zkHostString}")
	private String zkHostString;
	@Value("${solr.entityName}")
	private String entityName;
	
	@Autowired
	private ModificationService modificationService;

	// To schedule a full-import
	public String scheduleFullImport() {
		LOG.info("Scheduling full import with clean parameter set to true.!");
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, AUTHBRIDGECONSTANT.OPERATION.FULL_IMPORT,AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
		String res = schedulerComponent(true,"full-import");
		return res;
	}

	// To schedule a delta-import
	public String scheduleDeltaImport() {
		LOG.info("Scheduling delta import with clean parameter set to false.!");
		modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, AUTHBRIDGECONSTANT.OPERATION.DELTA_IMPORT,AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
		String res = schedulerComponent(false,"delta-import");
		return res;
	}

	private String schedulerComponent(boolean clean,String command) {
		//HttpSolrClient solr = new HttpSolrClient("http://localhost:8983/solr/authbridge");
		String result=null;
		
		CloudSolrClient solr = new CloudSolrClient(zkHostString);
		solr.setDefaultCollection("authbridge");
		solr.connect();
		
		// ->full import
		// http://localhost:8983/solr/core0/dataimport?command=full-import&clean=true
		// ->delta import
		// http://localhost:8983/solr/core0/dataimport?command=full-import&clean=false
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/dataimport");
		query.setParam("command", command);
		query.setParam("clean", clean);
		query.setParam("entity", entityName);
		query.setParam("commit", true);
		LOG.info("Values are set for importing data");
		QueryResponse response;
		try {
			LOG.info("Starting indexing/updating of documents.!");
			response = solr.query(query);
			//result=response.getResponse().get("statusMessages").toString();
			//LOG.info("Result: "+result);
			/*if(response.getResponse().get("statusMessages").toString().isEmpty()){
				LOG.info("response is empty...");
			}
			else{
				LOG.info("Some response..");
			}*/
			//Thread.sleep(5000);
			//long count = response.getFieldStatsInfo().get("Total Documents Processed").getCount();
			//LOG.info("Finished Indexing/Updating.!"+count);
			solr.close();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		return result;
	}
}
