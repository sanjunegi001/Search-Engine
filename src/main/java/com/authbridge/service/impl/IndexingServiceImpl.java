package com.authbridge.service.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.authbridge.model.CaseDetails;
import com.authbridge.repository.CaseDetailsRepository;
import com.authbridge.service.IndexingService;

@Component
public class IndexingServiceImpl implements IndexingService {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(IndexingServiceImpl.class);

	@Autowired
	private CaseDetailsRepository caseDetailsRepository;
	
	@Value("${solr.url}")
	private String solrURL;

	@Override
	public long totalCount() {
		long count = caseDetailsRepository.count();
		LOG.info("Total records fetched from database:-" + count);
		return count;
	}

	/**
	 * Method to perform full indexing in solr.
	 */
	@Override
	public void fullIndexing() {
		LOG.info("Solr URL->" + solrURL);

		Long count = caseDetailsRepository.count();
		LOG.info("Total no of records:" + count);

		Integer total = count.intValue();

		LOG.info("Converted to int->" + total);

		int resultCount = 2500000;

		int pages = Math.floorDiv(total, resultCount);

		LOG.info("Number of pages:-" + pages);

		HttpSolrClient solr = new HttpSolrClient(solrURL);

		for (int i = 0; i < pages; i++) {

			Page<CaseDetails> caseDetails = caseDetailsRepository.findAll(new PageRequest(i, resultCount));

			List<CaseDetails> details = caseDetails.getContent();

			Iterator<CaseDetails> itr = details.iterator();

			while (itr.hasNext()) {
				CaseDetails detail = (CaseDetails) itr.next();

				SolrInputDocument document = new SolrInputDocument();

				document.addField("id", detail.getPartyId());
				document.addField("case_id", detail.getCaseId());
				document.addField("partyname", detail.getPartyName());
				document.addField("partytype", detail.getPartyType());
				document.addField("address", detail.getPartyAddress());
				document.addField("partyfather", detail.getPartyFather());
				document.addField("advocatename", detail.getAdvocateName());
				document.addField("state_id", detail.getStateId());
				document.addField("district_id", detail.getDistrictId());
				document.addField("court_id", detail.getCourtId());
				document.addField("casecourtdetails", detail.getCaseCourtDetails());
				document.addField("caseacttype", detail.getCaseActType());
				document.addField("casesection", detail.getCaseSection());

				try {
					UpdateResponse response = solr.add(document);
					solr.commit();
				} catch (SolrServerException e) {
					LOG.info("SolrServerException occured:" + e.getLocalizedMessage());
					// e.printStackTrace();
				} catch (IOException e) {
					LOG.info("IOException occured:" + e.getLocalizedMessage());
					// e.printStackTrace();
				}
			}
			LOG.info("Indexing done for "+caseDetails.getNumberOfElements());

		}
		try {
			solr.close();
			LOG.info("Connection closed after indexing.");
		} catch (IOException e) {
			LOG.info("IOException occured when trying to close solr connection:" + e.getLocalizedMessage());
			// e.printStackTrace();
		}
	}

	@Override
	public void testing() {
		LOG.info("Solr URL->" + solrURL);

		Long count = caseDetailsRepository.count();
		LOG.info("Total no of records:" + count);

		Integer total = count.intValue();

		LOG.info("Converted to int->" + total);

		int resultCount = 2500000;

		int pages = Math.floorDiv(total, resultCount);

		LOG.info("Number of pages:-" + pages);

		HttpSolrClient solr = new HttpSolrClient(solrURL);

		// for (int i = 0; i < pages; i++) {

		Page<CaseDetails> caseDetails = caseDetailsRepository.findAll(new PageRequest(0, 10000));

		List<CaseDetails> details = caseDetails.getContent();
		LOG.info("Details size:" + details.size());

		Iterator<CaseDetails> itr = details.iterator();

		while (itr.hasNext()) {
			CaseDetails detail = (CaseDetails) itr.next();
			LOG.info("Indexing data for id:" + detail.getPartyId());
			SolrInputDocument document = new SolrInputDocument();

			document.addField("id", detail.getPartyId());
			document.addField("case_id", detail.getCaseId());
			document.addField("partyname", detail.getPartyName());
			document.addField("partytype", detail.getPartyType());
			document.addField("address", detail.getPartyAddress());
			document.addField("partyfather", detail.getPartyFather());
			document.addField("advocatename", detail.getAdvocateName());
			document.addField("state_id", detail.getStateId());
			document.addField("district_id", detail.getDistrictId());
			document.addField("court_id", detail.getCourtId());
			document.addField("casecourtdetails", detail.getCaseCourtDetails());
			document.addField("caseacttype", detail.getCaseActType());
			document.addField("casesection", detail.getCaseSection());

			try {
				UpdateResponse response = solr.add(document);
				LOG.info("Response added." + response.getStatus());
				solr.commit();
			} catch (SolrServerException e) {
				LOG.info("SolrServerException occured:" + e.getLocalizedMessage());
				// e.printStackTrace();
			} catch (IOException e) {
				LOG.info("IOException occured:" + e.getLocalizedMessage());
				// e.printStackTrace();
			}
		}

		// }
		try {
			solr.close();
			LOG.info("Connection closed after indexing.");
		} catch (IOException e) {
			LOG.info("IOException occured when trying to close solr connection:" + e.getLocalizedMessage());
			// e.printStackTrace();
		}
	}


	

}
