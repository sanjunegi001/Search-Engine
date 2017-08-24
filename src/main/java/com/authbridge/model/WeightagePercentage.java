package com.authbridge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "weightage")
public class WeightagePercentage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WEIGHT_ID")
	private Integer weightId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "VALUE")
	private float value;

	public Integer getWeightId() {
		return weightId;
	}

	public void setWeightId(Integer weightId) {
		this.weightId = weightId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "WeightagePercentage [weightId=" + weightId + ", name=" + name + ", value=" + value + "]";
	}

	
	
}
