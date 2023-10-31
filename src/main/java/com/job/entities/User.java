package com.job.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private List<String> emails;
	@Column(name = "username")
	private String userName;
	@Column(nullable = false)
	private String password;
	private String timezone;
	@Column(name = "mobile_no")
	private long mobileNo;
	@Column(name = "primary_email",nullable = false)
	private String primaryEmail;
	private String role = "ROLE_USER";
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	List<Application> applications;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	List<Organization> organizations;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public User(int id, String name, List<String> emails, String userName, String password, String timezone,
			long mobileNo, String primaryEmail, String role, List<Application> applications,
			List<Organization> organizations) {
		super();
		this.id = id;
		this.name = name;
		this.emails = emails;
		this.userName = userName;
		this.password = password;
		this.timezone = timezone;
		this.mobileNo = mobileNo;
		this.primaryEmail = primaryEmail;
		this.role = role;
		this.applications = applications;
		this.organizations = organizations;
	}



	
	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public List<Application> getApplications() {
		return applications;
	}



	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}



	public List<Organization> getOrganizations() {
		return organizations;
	}



	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
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

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
	
	
	
	
	
}
