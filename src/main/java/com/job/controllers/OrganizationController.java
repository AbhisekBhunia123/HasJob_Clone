package com.job.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.job.entities.Organization;
import com.job.entities.User;
import com.job.services.OrganizationServices;
import com.job.services.UserServices;

@Controller
public class OrganizationController {

	@Autowired
	UserServices userServices;

	@Autowired
	OrganizationServices organizationServices;

	@GetMapping("/account/organization")
	public String organizationPage(Model model, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		List<Organization> organizations = user.getOrganizations();
		Map<Integer, String> logos = new HashMap<>();
		for (Organization org : organizations) {
			if (org.getLogo() != null) {
				logos.put(org.getId(), Base64.getEncoder().encodeToString(org.getLogo()));
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("organizations", organizations);
		model.addAttribute("logos", logos);
		return "organization";
	}

	@GetMapping("/account/organizationjobs/{orgId}")
	public String organizationJobs(Model model, Authentication authentication,
			@PathVariable("orgId") int organizationId) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		List<Organization> organizations = new ArrayList<>();
		Organization organization = organizationServices.getOrganizationById(organizationId);
		organizations.add(organization);
		Map<Integer, String> logos = new HashMap<>();
		if (organization.getLogo() != null) {
			logos.put(organization.getId(), Base64.getEncoder().encodeToString(organization.getLogo()));
		}
		model.addAttribute("user", user);
		model.addAttribute("organizations", organizations);
		model.addAttribute("logos", logos);
		return "jobs";
	}
	
	@PostMapping("/account/updateorganization")
	public String updateOrganization(@RequestParam("name") String name,
			@RequestParam("url") String url,
			@RequestParam("orgId") int id,
			@RequestParam(name = "logo",required = false) MultipartFile logo) {
		organizationServices.updateOrganization(name,url,logo,id);
		return "redirect:/account/organization";
	}
	
	@GetMapping("/account/deleteorg/{orgId}")
	public String deleteOrganization(@PathVariable("orgId") int orgId) {
		organizationServices.deleteOrganization(orgId);
		return "redirect:/account/organization";
	}
}
