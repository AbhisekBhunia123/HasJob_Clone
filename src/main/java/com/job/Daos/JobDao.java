package com.job.Daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.job.Repositories.ApplicationRepo;
import com.job.Repositories.JobsRepo;
import com.job.Repositories.OrganizationRepo;
import com.job.entities.Application;
import com.job.entities.Job;
import com.job.entities.Organization;

@Repository
public class JobDao {

	@Autowired
	JobsRepo jobsRepo;
	@Autowired
	OrganizationRepo organizationRepo;
	@Autowired
	ApplicationRepo applicationRepo;
	
	public boolean saveJob(String headline,
			String alternateHeadline,
			String type,
			String category,
			String location,
			String description,
			String jobPerks,
			String jobPay,
			float equityCompensection,
			String jobNeed,
			String inermediaries,
			Date openedAt,
			Date closedAt,
			Organization organization,
			String payType,
			String payRange
			) {
		boolean isSaved = false;
		try {
			Job job = new Job();
			job.setHeadline(headline);
			if(alternateHeadline.length() != 0) {
				job.setAlternateHeading(alternateHeadline);
			}
			job.setType(type);
			job.setCategory(category);
			job.setLocation(location);
			job.setDescription(description);
			job.setJobParks(jobPerks);
			job.setJobPay(jobPay);
			job.setEquityCompensection(equityCompensection);
			job.setJobNeed(jobNeed);
			job.setIntermediaries(inermediaries);
			job.setOpenedAt(openedAt);
			job.setClosedAt(closedAt);
			job.setPayType(payType);
			job.setPayRange(Long.parseLong(payRange));
			job.setOrganization(organization);
			jobsRepo.save(job);
			isSaved = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}
	
	public Job getUniqueJobUsingOrganizationId(int orgId){
		Job job = jobsRepo.findFirstByOrganizationIdOrderById(orgId);
		return job;
	}
	
	public Job getJobById(int id) {
		return jobsRepo.findById(id).get();
	}
	
	public List<Job> getJobsInLocation(String location){
		return jobsRepo.findByLocation(location);
	}
	
	public boolean saveApply(String applicantName,String applicantEmail,String applicantPhone,String applicantDetails,int jobId) {
		boolean isSaved = false;
		try {
			Job job = jobsRepo.findById(jobId).get();
			Application application = new Application();
			application.setName(applicantName);
			application.setEmail(applicantEmail);
			application.setPhone(Long.parseLong(applicantPhone));
			application.setNeed(applicantDetails);
			application.setApplyAt(new Date());
			application.setJob(job);
			applicationRepo.save(application);
			isSaved = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}
	
	public List<Job> getAllJob(){
		List<Job> jobs = jobsRepo.findAll();
		return jobs;
	}
	
	public List<Job> filterJob(String[] types,String[] categorys,String[] locations,String payType,String payRange,String searchText){
		if(payRange.equals("0")) {
			List<Job> jobs = jobsRepo.filterJobWithOutPay(types, categorys, locations,payType,searchText);
			return jobs;
		}
		List<Job> jobs = jobsRepo.filterJob(types, categorys, locations,payType,searchText,Long.parseLong(payRange));
		return jobs;
	}
}
