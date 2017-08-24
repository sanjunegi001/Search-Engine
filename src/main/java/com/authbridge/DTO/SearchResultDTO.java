package com.authbridge.DTO;

public class SearchResultDTO {

	private Object data;
	private Integer totalResult;
	private Float maxScore;
	private Long start;
	private Long numFound;
	private Integer perPage;
	private Integer perPaginQuery;
	
	
	
	public Float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(Integer totalResult) {
		this.totalResult = totalResult;
	}
	public Long getNumFound() {
		return numFound;
	}
	public void setNumFound(Long numFound) {
		this.numFound = numFound;
	}
	public Integer getPerPaginQuery() {
		return perPaginQuery;
	}
	public void setPerPaginQuery(Integer perPaginQuery) {
		this.perPaginQuery = perPaginQuery;
	}
	public Integer getPerPage() {
		return perPage;
	}
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
	
	
	
}
