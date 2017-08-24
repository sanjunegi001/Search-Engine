package com.authbridge.DTO;

public class SearchCaseDetailDTO {

	private String name;
	private String address;
	private Integer start;
	private Integer count;
	private String sort;
	private String sortField;
	private String fatherName;
	private Integer stateId;
	private Integer districtId;
	private Float nameThreshold;
	private Float addrThreshold;
	private Integer checkId;

	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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
	public Float getNameThreshold() {
		return nameThreshold;
	}
	public void setNameThreshold(Float nameThreshold) {
		this.nameThreshold = nameThreshold;
	}
	public Float getAddrThreshold() {
		return addrThreshold;
	}
	public void setAddrThreshold(Float addrThreshold) {
		this.addrThreshold = addrThreshold;
	}
	
	@Override
	public String toString() {
		return "SearchCaseDetailDTO [name=" + name + ", address=" + address
				+ ", start=" + start + ", count=" + count + ", sort=" + sort
				+ ", sortField=" + sortField + ", fatherName=" + fatherName
				+ ", stateId=" + stateId + ", districtId=" + districtId
				+ ", nameThreshold=" + nameThreshold + ", addrThreshold="
				+ addrThreshold + "]";
	}
}
