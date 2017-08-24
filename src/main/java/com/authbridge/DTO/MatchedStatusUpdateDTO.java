package com.authbridge.DTO;

import java.util.List;

public class MatchedStatusUpdateDTO {
	private Integer checkId;
	private List<Integer> partyId;
	
	public Integer getCheckId() {
		return checkId;
	}
	public List<Integer> getPartyId() {
		return partyId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public void setPartyId(List<Integer> partyId) {
		this.partyId = partyId;
	}
	
	
}
