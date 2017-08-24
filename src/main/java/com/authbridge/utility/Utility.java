package com.authbridge.utility;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.cloud.Replica;
import org.apache.solr.common.cloud.Slice;
import org.apache.solr.common.cloud.ZkStateReader;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.authbridge.DTO.SchedulingStatusInfoDTO;
import com.authbridge.DTO.SolrStautusDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utility {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(Utility.class);
	
	@Value("${solr.zkHostString}")
	private String zkHostString;
	
	@Value("${solr.collection}")
	private String collection;
	
	@Value("${solr.url}")
	private String solrURL;
	
	public boolean checkEmpty(String value){
		if(value==null)
			return true;
		if(value.trim().length()==0)
			return true;
		return false;
	}
	
	/**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    public double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                                                             (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
 
    }
 
    // Example implementation of the Levenshtein Edit Distance
    // See http://r...content-available-to-author-only...e.org/wiki/Levenshtein_distance#Java
    public  int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
 
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
 
    /*public List<CaseDetails> sortList(List<CaseDetails> searchResults,
			String sortBy) {
		Query q = new Query();

		// Execute the query.
		QueryResults qr = null;
		try {
			q.parse("SELECT * FROM com.thoughtclan.textanalytics.vo.SearchResultVO order by "
					+ sortBy + " desc");
			qr = q.execute(searchResults);
		} catch (QueryExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return qr.getResults();

	}*/
	
	public double roundTwoDecimals(double d) {
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	
	public MappingJackson2HttpMessageConverter convertor(){
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
	
	/**
	 * method to convert the date into expected format
	 * Format: dd-MM-yyyy HH:mm
	 * @param stringDate
	 * @return
	 */
	public Date convertToDate(String stringDate){
		SimpleDateFormat dateformat = new SimpleDateFormat( "dd-MM-yyyy HH:mm");
		Date date = null;
		try {
			date = dateformat.parse(stringDate);
			//date = changeDateFormat(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public String changeDateFormat(Date date) throws ParseException{
		SimpleDateFormat dateformat = new SimpleDateFormat( "E dd.MMM.yyyy 'at' HH:mm");
		return dateformat.format(date);
	}
	/**
	 * To get the health condition of solr. To check if the corresponding node is
	 * up and running or down.
	 * Details can be fetched using the slices available in authbridge collection.
	 * @return
	 */
	public List<SolrStautusDTO> statusOfSolr(){
		 CloudSolrClient server = new CloudSolrClient(zkHostString);
 	     server.setDefaultCollection(collection);
		 server.connect();

		 ZkStateReader reader = server.getZkStateReader();
		 Collection<Slice> slices = reader.getClusterState().getSlices("authbridge");
		 Iterator<Slice> iter = slices.iterator();
		 List<SolrStautusDTO> listSolrStautusDTO = new ArrayList<SolrStautusDTO>();
		 try {
			server.close();
		} catch (IOException e) {
			LOG.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		 finally{
			 try {
				server.close();
			} catch (IOException e) {
				LOG.error("Error: "+e.getMessage());
				e.printStackTrace();
			}
		 }
		 while (iter.hasNext()) {
		 Slice slice = iter.next();
			 for(Replica replica:slice.getReplicas()) {
				 SolrStautusDTO solrStatus = new SolrStautusDTO();
				 solrStatus.setName(slice.getName());
				 solrStatus.setState(replica.getState().toString());
				 solrStatus.setParentName(slice.getParent());
				 solrStatus.setCoreURl(replica.getCoreUrl());
				 solrStatus.setReplicaName(replica.getName());
				 solrStatus.setNodeName(replica.getNodeName());
				 solrStatus.setBaseURl(String.valueOf(replica.get("base_url")));
				 listSolrStautusDTO.add(solrStatus); 
			 	}
		    }
		 return listSolrStautusDTO;
	}
	
	/**
	 * To get the first active node of the solr using its status.
	 * This method will return the active node to be used by abbr, aliases or stopwords addition/update
	 * if any of specific solr is down, this method helps to find the active solr node
	 * @return
	 */
	public SolrStautusDTO getFirstActiveNode(){
		List<SolrStautusDTO> listSolrStautusDTO = this.statusOfSolr();
		SolrStautusDTO activeSolr = null;
		for (SolrStautusDTO solrStautusDTO : listSolrStautusDTO) {
			if(solrStautusDTO.getState().equals("active")){
				activeSolr = solrStautusDTO;
				break;
			}
		}
		return activeSolr;
	}
	
	/**
	 * To get all the active solr nodes available to be displayed 
	 * in the dashboard to the admin user.
	 * @return
	 */
	public List<SolrStautusDTO> getAllActiveNode(){
		List<SolrStautusDTO> listSolrStautusDTO = this.statusOfSolr();
		List<SolrStautusDTO> activeList = new ArrayList<SolrStautusDTO>();
		SolrStautusDTO activeSolr = null;
		for (SolrStautusDTO solrStautusDTO : listSolrStautusDTO) {
			if(solrStautusDTO.getState().equals("active")){
				activeSolr = solrStautusDTO;
				activeList.add(activeSolr);
			}
		}
		return activeList;
	}
	
	public String manageResourceURL(){
		/*SolrStautusDTO activeSolr = this.getFirstActiveNode();
		if(activeSolr!= null){
			String baseURl = activeSolr.getBaseURl();
			return baseURl+"/"+collection;
		}
		return null;*/
		return solrURL;
	}
	
	/**
	 * To get the details of scheduling statistics to be displayed in dashboard
	 * to the admin user. Its based on the indexing process.
	 * @param statusMessage
	 * @return
	 */
	public SchedulingStatusInfoDTO schedulingStats(String statusMessage){
		SchedulingStatusInfoDTO stats = new SchedulingStatusInfoDTO();
		String[] str = statusMessage.split(",");
		for(String s : str){
			if(s.contains("Total Rows Fetched")){
				String[] val = s.split("=");
				stats.setRowsFetched(val[1]);
				LOG.info("Fetched: "+val[1]);
			}
			else if(s.contains("Total Documents Processed")){
				String[] val = s.split("=");
				stats.setDocsProcessed(val[1]);
				LOG.info("Processed: "+val[1]);
			}
			else if(s.contains("Total Documents Skipped")){
				String[] val = s.split("=");
				stats.setDocsSkipped(val[1]);
				LOG.info("Skipped: "+val[1]);
			}
			else if(s.contains("Time taken")){
				String[] val = s.split("=");
				stats.setTimeTaken(val[1].replace("}", ""));
				LOG.info("Time taken: "+val[1]);
			}
			else{
				LOG.info("No response.");
			}
		}
		LOG.info("Stats: "+stats.toString());
		return stats;
	}
}
