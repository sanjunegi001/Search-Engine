package com.authbridge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "check_party_mapping")
public class CheckIdPartyIdMapper {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "check_id")
	private Integer checkId;
	@Column(name = "party_id")
	private Integer partyId;
	@Column(name = "status_matched")
	private Integer statusMatched;
	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public Integer getPartyId() {
		return partyId;
	}
	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}
	
	public Integer getStatusMatched() {
		return statusMatched;
	}
	public void setStatusMatched(Integer statusMatched) {
		this.statusMatched = statusMatched;
	}
	@Override
	public String toString() {
		return "CheckIdPartyIdMapper [checkId=" + checkId + ", partyId=" + partyId + "]";
	}
	
}
