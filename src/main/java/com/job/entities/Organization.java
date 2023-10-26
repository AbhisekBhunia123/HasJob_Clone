package com.job.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizations")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String name;
	@Lob
    private byte[] logo;
	private String url;
	@Column(unique = true,nullable = false)
	private String email;
	private String collaborators;
	@OneToMany(mappedBy = "organization",cascade = CascadeType.ALL)
	private List<Job> jobs;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Organization(int id, String name, byte[] logo, String url, String email, String collaborators,
			List<Job> jobs, User user) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.url = url;
		this.email = email;
		this.collaborators = collaborators;
		this.jobs = jobs;
		this.user = user;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(String collaborators) {
		this.collaborators = collaborators;
	}



	public List<Job> getJobs() {
		return jobs;
	}



	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
}
