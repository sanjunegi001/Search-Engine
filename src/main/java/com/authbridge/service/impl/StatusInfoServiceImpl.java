package com.authbridge.service.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.authbridge.service.StatusInfoService;

@Component
public class StatusInfoServiceImpl implements StatusInfoService{
	
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(StatusInfoServiceImpl.class);
	
	@Value("${solr.zkHostString}")
	private String zkHostString;

	@Value("${solr.collection}")
	private String collection;
	
	@Override
	public void getStatus() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * To reload the collection. 
	 * Collection is reloaded if there are any changes related to configuration
	 * or any new aliases/abbr/stop words are added or updated.
	 */
	@Override
	public void reloadCollection() {
		CloudSolrClient solr = new CloudSolrClient(zkHostString);	
		solr.setDefaultCollection(collection);
		solr.connect();
		
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/admin/collections");
		query.setParam("action", "RELOAD");
		query.setParam("name", collection);
		
		try {
			QueryResponse response = solr.query(query);
			LOG.info(response.getResponseHeader().get("status")+" status------");
			
			solr.close();
		} catch (SolrServerException | IOException e) {
			LOG.error("Some exception : "+e.getMessage());
		}finally {
			try {
				solr.close();
			} catch (IOException e) {
				LOG.error("Some exception  in closng solr : "+e.getMessage());
			}
		}
		
	}

}
