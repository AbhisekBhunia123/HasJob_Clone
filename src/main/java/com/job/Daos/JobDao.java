package com.job.Daos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.job.Repositories.JobsRepo;
import com.job.Repositories.OrganizationRepo;
import com.job.entities.Job;
import com.job.entities.Organization;

@Repository
public class JobDao {

	@Autowired
	JobsRepo jobsRepo;
	@Autowired
	OrganizationRepo organizationRepo;
	
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
			Organization organization
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
}
