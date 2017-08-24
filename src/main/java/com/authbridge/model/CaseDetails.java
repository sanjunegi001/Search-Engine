package com.authbridge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ab_case_party_details")
public class CaseDetails {
	@Id
	@Column(name = "PARTY_ID")
	private Integer partyId;
	@Column(name = "CASE_ID")
	private Integer caseId;
	@Column(name = "PARTY_NAME")
	private String partyName;
	@Column(name = "PARTY_TYPE")
	private Integer partyType;
	@Column(name = "PARTY_ADDRESS")
	private String partyAddress;
	@Column(name = "PARTY_FATHER")
	private String partyFather;
	@Column(name = "ADVOCATE_NAME")
	private String advocateName;
	@Column(name = "STATE_ID")
	private Integer stateId;
	@Column(name = "DISTRICT_ID")
	private Integer districtId;
	@Column(name = "COURT_ID")
	private Integer courtId;
	@Column(name = "CASE_COURT_DETAILS")
	private String caseCourtDetails;
	@Column(name = "CASE_ACT_TYPE")
	private String caseActType;
	@Column(name = "CASE_SECTION")
	private String caseSection;
	
	
		public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Integer getPartyType() {
		return partyType;
	}

	public void setPartyType(Integer partyType) {
		this.partyType = partyType;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getPartyFather() {
		return partyFather;
	}

	public void setPartyFather(String partyFather) {
		this.partyFather = partyFather;
	}

	public String getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(String advocateName) {
		this.advocateName = advocateName;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getCourtId() {
		return courtId;
	}

	public void setCourtId(Integer courtId) {
		this.courtId = courtId;
	}

	public String getCaseCourtDetails() {
		return caseCourtDetails;
	}

	public void setCaseCourtDetails(String caseCourtDetails) {
		this.caseCourtDetails = caseCourtDetails;
	}

	public String getCaseActType() {
		return caseActType;
	}

	public void setCaseActType(String caseActType) {
		this.caseActType = caseActType;
	}

	public String getCaseSection() {
		return caseSection;
	}

	public void setCaseSection(String caseSection) {
		this.caseSection = caseSection;
	}

}
