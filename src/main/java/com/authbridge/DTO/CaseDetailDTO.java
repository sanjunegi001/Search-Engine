package com.authbridge.DTO;

import java.util.Comparator;

public class CaseDetailDTO {

	private Integer partyId;
	private Integer caseId;
	private String partyName;
	private Integer partyType;
	private String partyAddress;
	private String partyFather;
	private String advocateName;
	private Integer stateId;
	private Integer districtId;
	private Integer courtId;
	private String caseCourtDetails;
	private String caseActType;
	private String caseSection;
	private String score;
	private Double nameMatchPercentage;
	private Double addressMatchPercentage;
	private Double bothMatchPercentage;
	private Double weightedPercentage;
	private Double fatherPercentage;
	private Integer rank;

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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Double getNameMatchPercentage() {
		return nameMatchPercentage;
	}
	public void setNameMatchPercentage(Double nameMatchPercentage) {
		this.nameMatchPercentage = nameMatchPercentage;
	}
	public Double getAddressMatchPercentage() {
		return addressMatchPercentage;
	}
	public void setAddressMatchPercentage(Double addressMatchPercentage) {
		this.addressMatchPercentage = addressMatchPercentage;
	}
	public Double getBothMatchPercentage() {
		return bothMatchPercentage;
	}
	public void setBothMatchPercentage(Double bothMatchPercentage) {
		this.bothMatchPercentage = bothMatchPercentage;
	}
	
	 public Double getWeightedPercentage() {
		return weightedPercentage;
	}
	public void setWeightedPercentage(Double weightedPercentage) {
		this.weightedPercentage = weightedPercentage;
	}

	public static Comparator<CaseDetailDTO> NAME_MATCH = new Comparator<CaseDetailDTO>() {
          @Override
          public int compare(CaseDetailDTO o1, CaseDetailDTO o2) {
              return o1.nameMatchPercentage.compareTo(o2.nameMatchPercentage);
          }
     };
     
     public static Comparator<CaseDetailDTO> ADDRESS_MATCH = new Comparator<CaseDetailDTO>() {
         @Override
         public int compare(CaseDetailDTO o1, CaseDetailDTO o2) {
             return o1.addressMatchPercentage.compareTo(o2.addressMatchPercentage);
         }
    };
    
    public static Comparator<CaseDetailDTO> NAME_ADDRESS_MATCH = new Comparator<CaseDetailDTO>() {
        @Override
        public int compare(CaseDetailDTO o1, CaseDetailDTO o2) {
            return o1.bothMatchPercentage.compareTo(o2.bothMatchPercentage);
        }
   };
   
   public static Comparator<CaseDetailDTO> WEIGHT_MATCH = new Comparator<CaseDetailDTO>() {
       @Override
       public int compare(CaseDetailDTO o1, CaseDetailDTO o2) {
           return o1.weightedPercentage.compareTo(o2.weightedPercentage);
       }
  };
  
  public static Comparator<CaseDetailDTO> FATHER_MATCH = new Comparator<CaseDetailDTO>() {
      @Override
      public int compare(CaseDetailDTO o1, CaseDetailDTO o2) {
          return o1.fatherPercentage.compareTo(o2.fatherPercentage);
      }
 };



public Double getFatherPercentage() {
	return fatherPercentage;
}
public void setFatherPercentage(Double fatherPercentage) {
	this.fatherPercentage = fatherPercentage;
}
public Integer getRank() {
	return rank;
}
public void setRank(Integer rank) {
	this.rank = rank;
}

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((caseId == null) ? 0 : caseId.hashCode());
	return result;
}
/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	CaseDetailDTO other = (CaseDetailDTO) obj;
	if (caseId == null) {
		if (other.caseId != null)
			return false;
	} else if (!caseId.equals(other.caseId))
		return false;
	return true;
}
}
