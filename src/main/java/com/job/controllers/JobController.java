package com.job.controllers;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.job.entities.Organization;
import com.job.entities.User;
import com.job.services.JobServices;
import com.job.services.UserServices;

@Controller
public class JobController {

	@Autowired
	UserServices userServices;
	
	@Autowired
	JobServices jobServices;
	
	@GetMapping("/account/jobs")
	public String showJobsPage(Model model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		List<Organization> organizations = user.getOrganizations();
		Map<Integer,String> logos = new HashMap<>();
		for(Organization org : organizations) {
			if(org.getLogo() != null) {
				logos.put(org.getId(), Base64.getEncoder().encodeToString(org.getLogo()));
			}
		}
		model.addAttribute("user",user);
		model.addAttribute("organizations",organizations);
		model.addAttribute("logos",logos);
		return "jobs";
	}
	
	@PostMapping("/account/deletejob")
	public String deleteJob(@RequestParam("jobId") int jobId) {
		jobServices.deleteJob(jobId);
		return "redirect:/account/organization";
	}
}
