package com.authbridge.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "districts_state_mapping", catalog = "authbridge")
public class DistrictModel {

	
	
	private Integer id;
	private String districtName;
//	private StateModel state;
	
	@Id
	@Column(name = "district_code", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "district_name")
	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	@Override
	public String toString() {
		return "DistrictModel [id=" + id + ", districtName=" + districtName + "]";
	}

}

