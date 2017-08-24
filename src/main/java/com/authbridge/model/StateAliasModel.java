package com.authbridge.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state_alias_mapping", catalog = "authbridge")
public class StateAliasModel {
	
	
	private Integer id;
	private StateModel state;
	private String aliasName;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public StateModel getState() {
		return state;
	}

	public void setState(StateModel state) {
		this.state = state;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "aliasid", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "StateAliasModel [id=" + id + ", state=" + state + ", aliasName=" + aliasName + "]";
	}

	@Column(name = "state_alias")
	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

}
