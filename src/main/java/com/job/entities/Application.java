package com.job.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "applications")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private long phone;
	@Column(nullable = false)
	private String need;
	@Column(name = "apply_at")
	private Date applyAt;
	@Column(name = "better_exp")
	private String betterExperience;
	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Application(int id, String name, String email, long phone, String need, Date applyAt,
			String betterExperience, Job job, User user) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.need = need;
		this.applyAt = applyAt;
		this.betterExperience = betterExperience;
		this.job = job;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public Date getApplyAt() {
		return applyAt;
	}
	public void setApplyAt(Date applyAt) {
		this.applyAt = applyAt;
	}
	public String getBetterExperience() {
		return betterExperience;
	}
	public void setBetterExperience(String betterExperience) {
		this.betterExperience = betterExperience;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
