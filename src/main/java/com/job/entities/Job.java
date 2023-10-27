package com.job.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String headline;
	@Column(name = "alternate_heading")
	private String alternateHeading;
	private String type;
	private String category;
	private String location;
	private String description;
	@Column(name = "job_parks")
	private String jobParks;
	@Column(name = "job_pay")
	private String jobPay;
	@Column(name = "pay_type")
	private String payType;
	@Column(name = "pay_range")
	private long payRange;
	@Column(name = "equity_compensection")
	private float equityCompensection;
	@Column(name = "job_need")
	private String jobNeed;
	private String intermediaries;
	@Column(name = "opened_at")
	private Date openedAt;
	@Column(name = "closed_at")
	private Date closedAt;
	@OneToMany(mappedBy = "job",cascade = CascadeType.ALL)
	private List<Application> applications;
	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;
	
	
	public Job() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Job(int id, String headline, String alternateHeading, String type, String category, String location,
			String description, String jobParks, String jobPay, String payType, long payRange,
			float equityCompensection, String jobNeed, String intermediaries, Date openedAt, Date closedAt,
			List<Application> applications, Organization organization) {
		super();
		this.id = id;
		this.headline = headline;
		this.alternateHeading = alternateHeading;
		this.type = type;
		this.category = category;
		this.location = location;
		this.description = description;
		this.jobParks = jobParks;
		this.jobPay = jobPay;
		this.payType = payType;
		this.payRange = payRange;
		this.equityCompensection = equityCompensection;
		this.jobNeed = jobNeed;
		this.intermediaries = intermediaries;
		this.openedAt = openedAt;
		this.closedAt = closedAt;
		this.applications = applications;
		this.organization = organization;
	}





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getAlternateHeading() {
		return alternateHeading;
	}

	public void setAlternateHeading(String alternateHeading) {
		this.alternateHeading = alternateHeading;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobParks() {
		return jobParks;
	}

	public void setJobParks(String jobParks) {
		this.jobParks = jobParks;
	}

	public String getJobPay() {
		return jobPay;
	}

	public void setJobPay(String jobPay) {
		this.jobPay = jobPay;
	}

	public float getEquityCompensection() {
		return equityCompensection;
	}

	public void setEquityCompensection(float equityCompensection) {
		this.equityCompensection = equityCompensection;
	}

	public String getJobNeed() {
		return jobNeed;
	}

	public void setJobNeed(String jobNeed) {
		this.jobNeed = jobNeed;
	}

	public String getIntermediaries() {
		return intermediaries;
	}

	public void setIntermediaries(String intermediaries) {
		this.intermediaries = intermediaries;
	}


	public Date getOpenedAt() {
		return openedAt;
	}


	public void setOpenedAt(Date openedAt) {
		this.openedAt = openedAt;
	}


	public Date getClosedAt() {
		return closedAt;
	}


	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}


	public List<Application> getApplications() {
		return applications;
	}


	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}


	public Organization getOrganization() {
		return organization;
	}


	public void setOrganization(Organization organization) {
		this.organization = organization;
	}



	public String getPayType() {
		return payType;
	}



	public void setPayType(String payType) {
		this.payType = payType;
	}



	public long getPayRange() {
		return payRange;
	}



	public void setPayRange(long payRange) {
		this.payRange = payRange;
	}
	
	
	
	
	
	
	
	
	

}
