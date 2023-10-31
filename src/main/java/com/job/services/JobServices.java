package com.job.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.Repositories.JobsRepo;

@Service
public class JobServices {

	@Autowired
	JobsRepo jobsRepo;
	
	public boolean deleteJob(int id) {
		jobsRepo.deleteById(id);
		return true;
	}
}
