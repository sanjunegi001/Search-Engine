package com.authbridge.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stop_words_master", catalog = "authbridge")
public class StopWordModel {

	private Integer id;
	private String word;
	private StopWordType type;

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "stop_words_text", nullable = false, length = 100)
	public String getWord() {
		return this.word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type")
	public StopWordType getType() {
		return type;
	}

	public void setType(StopWordType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "StopWordModel [id=" + id + ", word=" + word + ", type=" + type.getType() + "]";
	}
	
	

}
