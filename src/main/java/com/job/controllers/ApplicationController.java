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

import com.job.entities.Application;
import com.job.entities.Job;
import com.job.entities.Organization;
import com.job.entities.User;
import com.job.services.ApplicationServices;
import com.job.services.UserServices;

@Controller
public class ApplicationController {

	@Autowired
	UserServices userServices;
	
	@Autowired
	ApplicationServices applicationServices;
	
	@GetMapping("/account/appliedjob")
	public String showAppliedJobs(Model model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		List<Application> applications = user.getApplications();
		Map<Integer,String> logos = new HashMap<>();
		for(Application application : applications) {
				if(application.getJob().getOrganization().getLogo() != null) {
					logos.put(application.getJob().getOrganization().getId(), Base64.getEncoder().encodeToString(application.getJob().getOrganization().getLogo()));
				}
		}
		System.out.println(logos);
		System.out.println(applications);
		model.addAttribute("user",user);
		model.addAttribute("applications",applications);
		model.addAttribute("logos",logos);
		return "appliedjob";
	}
	
	@PostMapping("/account/deleteapplication")
	public String deleteApplication(@RequestParam("appId") int appId) {
		applicationServices.deleteApplication(appId);
		return "redirect:/account/appliedjob";
	}
}
