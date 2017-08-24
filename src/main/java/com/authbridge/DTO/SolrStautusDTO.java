package com.authbridge.DTO;

public class SolrStautusDTO {

	private String name;
	private String parentName;
	private String coreURl;
	private String nodeName;
	private String baseURl;
	private String replicaName;
	private String state;
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getCoreURl() {
		return coreURl;
	}
	public void setCoreURl(String coreURl) {
		this.coreURl = coreURl;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getBaseURl() {
		return baseURl;
	}
	public void setBaseURl(String baseURl) {
		this.baseURl = baseURl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReplicaName() {
		return replicaName;
	}
	public void setReplicaName(String replicaName) {
		this.replicaName = replicaName;
	}
	
	
}
