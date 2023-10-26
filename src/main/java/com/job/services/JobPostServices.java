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
			MultipartFile logo) {
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
						jobPay, equityCompensection, jobNeed, intermediaries, openedAt, closedAt, org);
				if (isSaved) {
					isPublished = true;
				}

			}

		} else {
			Organization org = organizationDao.findByEmail(email);
			boolean isSaved = jobDao.saveJob(headline, headlineB, type, category, location, description, jobPerks,
					jobPay, equityCompensection, jobNeed, intermediaries, openedAt, closedAt, org);
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

}
