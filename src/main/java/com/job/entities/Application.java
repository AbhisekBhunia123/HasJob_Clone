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
	@JoinColumn(name = "application_id")
	private User user;
	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	
	
	

}
