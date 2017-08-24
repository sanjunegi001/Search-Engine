package com.authbridge.DTO;

public class SchedulerDTO {

	private Integer id;
	private String dateTime;
	private String type;
	private String recurring;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRecurring() {
		return recurring;
	}
	public void setRecurring(String recurring) {
		this.recurring = recurring;
	}
	@Override
	public String toString() {
		return "SchedulerDTO [id=" + id + ", dateTime=" + dateTime + ", type=" + type + ", recurring=" + recurring
				+ "]";
	}
	
	
}
