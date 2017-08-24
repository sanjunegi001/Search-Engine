package com.authbridge.DTO;

public class ManageResourceResponse {

	private String status;
	private String qtime;
	private String synonymMappings;
	private String wordSet;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQtime() {
		return qtime;
	}
	public void setQtime(String qtime) {
		this.qtime = qtime;
	}
	public String getSynonymMappings() {
		return synonymMappings;
	}
	public void setSynonymMappings(String synonymMappings) {
		this.synonymMappings = synonymMappings;
	}
	@Override
	public String toString() {
		return "ManageResourceResponse  synonymMappings=" + synonymMappings
				+ "]";
	}
	public String getWordSet() {
		return wordSet;
	}
	public void setWordSet(String wordSet) {
		this.wordSet = wordSet;
	}
	
}


