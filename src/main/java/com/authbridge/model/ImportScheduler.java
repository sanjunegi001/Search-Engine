package com.authbridge.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "scheduler")
public class ImportScheduler {
	@Id
	@Column(name = "Scheduler_Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer schedulerId;
	
	@Column(name = "date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	@Column(name = "import_type")
	private String type;
	
	@Column(name = "recurring")
	private String recurring;
	
	public Integer getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(Integer schedulerId) {
		this.schedulerId = schedulerId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
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

	
	
	
}
