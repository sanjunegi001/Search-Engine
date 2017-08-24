package com.authbridge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "district_alias_mapping", catalog = "authbridge")
public class DistrictAliasModel {

	private Integer id;
	private String aliasName;
	private DistrictModel district;
	
	@Id
	@Column(name = "alias_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "district_id")
	public DistrictModel getDistrict() {
		return district;
	}

	public void setDistrict(DistrictModel district) {
		this.district = district;
	}
	
	@Column(name = "district_alias_name")
	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	@Override
	public String toString() {
		return "DistrictAliasModel [id=" + id + ", aliasName=" + aliasName + ", district=" + district + "]";
	}
	
}
