package com.project.mein.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "languages", catalog = "srd")
public class Languages implements java.io.Serializable {
	private int languagesId;
	private String name;
	private int number;
	private Repository repository;

	public Languages() {
	}

	public Languages(int languageId, String name, int number,
			Repository repository) {
		this.languagesId = languageId;
		this.name = name;
		this.number = number;
		this.repository = repository;
	}

	@Id
	@Column(name = "languages_id", unique = true, nullable = false)
	public int getLanguagesId() {
		return languagesId;
	}

	public void setLanguagesId(int languagesId) {
		this.languagesId = languagesId;
	}

	@Column(name = "name", nullable = true, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "name", nullable = true)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id", nullable = false)
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

}
