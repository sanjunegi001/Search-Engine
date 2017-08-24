package com.authbridge.DTO;

public class WeightageDTO {
	
	private Integer weightId;
	private float weightage;
	private String name;
	
	
	
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

	public float getWeightage() {
		return weightage;
	}
	
	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}

	@Override
	public String toString() {
		return "WeightageDTO [weightId=" + weightId + ", weightage="
				+ weightage + ", name=" + name + "]";
	}
}
