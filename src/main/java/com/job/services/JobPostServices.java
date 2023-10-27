package com.job.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.job.Daos.JobDao;
import com.job.Daos.OrganizationDao;
import com.job.entities.Job;
import com.job.entities.Organization;

@Service
public class JobPostServices {

	@Autowired
	JobDao jobDao;

	@Autowired
	OrganizationDao organizationDao;


	public boolean publishJob(String headline, String headlineB, String type, String category, String location,
			String description, String jobPerks, String jobPay, float equityCompensection, String jobNeed,
			String intermediaries, String organizationName, String url, String email, String collaborators,
			MultipartFile logo,String payType,String payRange) {
		boolean isPublished = false;
		Date openedAt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(openedAt);
		calendar.add(Calendar.DATE, 30);
		Date closedAt = calendar.getTime();
		Organization organization = organizationDao.findByEmail(email);
		if (organization == null) {
			boolean isCreated = organizationDao.createOrganization(organizationName, url, email, collaborators, logo);
			if (isCreated) {
				Organization org = organizationDao.findByEmail(email);
				boolean isSaved = jobDao.saveJob(headline, headlineB, type, category, location, description, jobPerks,
						jobPay, equityCompensection, jobNeed, intermediaries, openedAt, closedAt, org,payType,payRange);
				if (isSaved) {
					isPublished = true;
				}

			}

		} else {
			Organization org = organizationDao.findByEmail(email);
			boolean isSaved = jobDao.saveJob(headline, headlineB, type, category, location, description, jobPerks,
					jobPay, equityCompensection, jobNeed, intermediaries, openedAt, closedAt, org,payType,payRange);
			if (isSaved) {
				isPublished = true;
			}
		}

		return isPublished;
	}
	
	
	public List<Job> getAllUniqueOrganizationJobs(){
		List<Job> jobs = new ArrayList<>();
		List<Organization> organizations = organizationDao.getAllOrganization();
		for(Organization org : organizations) {
			jobs.add(jobDao.getUniqueJobUsingOrganizationId(org.getId()));
		}
		return jobs;
		
	}
	
	public List<Organization> getallOrganization(){
		List<Organization> organizations = organizationDao.getAllOrganization();
		return organizations;
	}

	public String getImageAsBase64(int id) {
		return organizationDao.getImageAsBase64(id);
	}
	
	public Job getJobById(int id) {
		return jobDao.getJobById(id);
	}
	
	public List<Job> getJobsInLocation(String location){
		return jobDao.getJobsInLocation(location);
	}
	
	public boolean applyForJob(String applicantName,String applicantEmail,String applicantPhone,String applicantDetails,int jobId) {
		boolean isApplied = false;
		boolean isSave = jobDao.saveApply(applicantName,applicantEmail,applicantPhone,applicantDetails,jobId);
		if(isSave) {
			isApplied = true;
		}
		return isApplied;
	}
	
	public Set<String> getAllLocations(){
		Set<String> locations = new HashSet<>();
		List<Job>  jobs = jobDao.getAllJob();
		for(Job job : jobs) {
			locations.add(job.getLocation());
		}
		return locations;
	}
	
	public List<Job> filterJobs(String[] type,String[] categories,String[] locations,String payType,String payRange,String searchText){
		List<Job> jobs = jobDao.filterJob(type,categories,locations,payType,payRange,searchText);
		return jobs;
	}

}
