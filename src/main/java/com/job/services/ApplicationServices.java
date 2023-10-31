package com.job.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.Repositories.ApplicationRepo;

@Service
public class ApplicationServices {
	
	@Autowired
	ApplicationRepo applicationRepo;

	
	public boolean deleteApplication(int id) {
		applicationRepo.deleteById(id);
		return true;
	}
}
