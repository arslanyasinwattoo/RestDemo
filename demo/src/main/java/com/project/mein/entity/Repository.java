package com.project.mein.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "repository", catalog = "srd")
public class Repository implements java.io.Serializable {
	private int repository_Id;
	private String name;
	private String url;
	private String description;
	private User user;
	private Set<Languages> languages = new HashSet<Languages>(0);

	public Repository() {
	}

	public Repository(int repository_Id, String name, String url,
			String description, User user) {
		super();
		this.repository_Id = repository_Id;
		this.name = name;
		this.url = url;
		this.description = description;
		this.user = user;
	}

	public Repository(int repository_Id, String name, String url,
			String description, User user, Set<Languages> languages) {
		super();
		this.repository_Id = repository_Id;
		this.name = name;
		this.url = url;
		this.description = description;
		this.user = user;
		this.languages = languages;
	}

	@Id
	@Column(name = "repository_id", unique = true, nullable = false)
	public int getRepository_Id() {
		return repository_Id;
	}

	public void setRepository_Id(int repository_Id) {
		this.repository_Id = repository_Id;
	}

	@Column(name = "name", nullable = true, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", nullable = true, length = 100)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "description", nullable = true, length = 250)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "repository")
	public Set<Languages> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Languages> languages) {
		this.languages = languages;
	}

}
