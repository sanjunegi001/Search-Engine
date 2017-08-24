package com.authbridge.DTO;

/**
 * Holds inputs derived from search query for score calculation of each record.
 */
public class CalcScoreInputDTO {
	private String name;
	private String address;
	private String fatherName;
	private Integer stateId;
	private Integer districtId;
	private Float nameThreshold;
	private Float addrThreshold;
	
	/**
	 * Initializes new {@link CalcScoreInputDTO} from the given builder. 
	 * Made it private to make sure {@link Builder} is used for initialization.
	 */
	private CalcScoreInputDTO(Builder builder) {
		super();
		this.name = builder.name;
		this.address = builder.address;
		this.fatherName = builder.fatherName;
		this.stateId = builder.stateId;
		this.districtId = builder.districtId;
		this.nameThreshold = builder.nameThreshold;
		this.addrThreshold = builder.addrThreshold;
	}
	
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getFatherName() {
		return fatherName;
	}
	public Integer getStateId() {
		return stateId;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public Float getNameThreshold() {
		return nameThreshold;
	}
	public Float getAddrThreshold() {
		return addrThreshold;
	}

	public static class Builder {
		private String name;
		private String address;
		
		// optional fields
		private String fatherName;
		private Integer stateId;
		private Integer districtId;
		private Float nameThreshold;
		private Float addrThreshold;
		
		/**
		 * Takes name and address as input and initializes builder.
		 * 
		 * @param name
		 * @param address
		 */
		public Builder(String name, String address) {
			this.name = name;
			this.address = address;
		}
		
		public Builder withFatherName(String fatherName) {
			this.fatherName = fatherName;
			return this;
		}
		
		public Builder withStateId(Integer stateId) {
			this.stateId = stateId;
			return this;
		}
		
		public Builder withDistrictId(Integer districtId) {
			this.districtId = districtId;
			return this;
		}

		public Builder withNameThreshold(Float nameThreshold) {
			this.nameThreshold = nameThreshold;
			return this;
		}
		
		public Builder withAddrThreshold(Float addrThreshold) {
			this.addrThreshold = addrThreshold;
			return this;
		}
		
		public CalcScoreInputDTO build() {
			return new CalcScoreInputDTO(this);
		}
	}
}
