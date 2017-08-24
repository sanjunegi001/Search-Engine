package com.authbridge.DTO;

/**
 * Holds inputs for weighted percentage calculation.
 */
public class CalcWeightageInputDTO {
	private Double namePercentage;
	private Double addressPercentage;
	private Double fatherPercentage;
	private Integer stateId;
	private Integer districtId;
	
	/**
	 * Initializes new {@link CalcWeightageInputDTO} from the given builder. 
	 * Made it private to make sure {@link Builder} is used for initialization.
	 */
	private CalcWeightageInputDTO(Builder builder) {
		this.namePercentage = builder.namePercentage;
		this.addressPercentage = builder.addressPercentage;
		this.fatherPercentage = builder.fatherPercentage;
		this.stateId = builder.stateId;
		this.districtId = builder.districtId;
	}
	
	public Double getNamePercentage() {
		return namePercentage;
	}
	public Double getAddressPercentage() {
		return addressPercentage;
	}
	public Double getFatherPercentage() {
		return fatherPercentage;
	}
	public Integer getStateId() {
		return stateId;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	
	/**
	 * Builder class for building objects of {@link CalcWeightageInputDTO}.
	 */
	public static class Builder {
		private Double namePercentage;
		private Double addressPercentage;
		
		// optional fields
		private Double fatherPercentage;
		private Integer stateId;
		private Integer districtId;
		
		/**
		 * Takes name match percentage and address match percentage as input and initializes builder.
		 * @param namePercentage
		 * @param addressPercentage
		 */
		public Builder(Double namePercentage, Double addressPercentage) {
			this.namePercentage = namePercentage;
			this.addressPercentage = addressPercentage;
		}
		
		public Builder withFatherPercentage(Double fatherPercentage) {
			this.fatherPercentage = fatherPercentage;
			return this;
		}
		
		public Builder withStateId(Integer stateId) {
			this.stateId = stateId;
			return this;
		}
		
		public Builder withDistriceId(Integer districtId) {
			this.districtId = districtId;
			return this;
		}
		
		public CalcWeightageInputDTO build() {
			return new CalcWeightageInputDTO(this);
		}
	}
}
