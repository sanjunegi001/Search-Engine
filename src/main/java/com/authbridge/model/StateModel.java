package com.authbridge.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "state_master", catalog = "authbridge")
public class StateModel {
	
	private Integer id;
	private String stateName;
	private Collection<DistrictModel> district = new LinkedHashSet<DistrictModel>();

	@Id
	@Column(name = "state_id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "state_name", nullable = false, length = 100)
	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return "StateModel [id=" + id + ", stateName=" + stateName + ", district=" + district + "]";
	}

	@OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
	@JoinColumn(name= "state_id")
	public Collection<DistrictModel> getDistrict() {
		return district;
	}

	public void setDistrict(List<DistrictModel> district) {
		this.district = district;
	}

}
