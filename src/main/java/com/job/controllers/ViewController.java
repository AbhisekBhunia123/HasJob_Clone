package com.job.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.job.entities.Job;
import com.job.entities.Organization;
import com.job.services.JobPostServices;


@Controller
public class ViewController {
	
	@Autowired
	JobPostServices jobPostServices;

	@RequestMapping("/")
	public String home(Model model) {
		List<Organization> organizations = jobPostServices.getallOrganization();
		model.addAttribute("organizations",organizations);
		return "home";
	}
	
	@GetMapping("/new")
	public String showPostJob() {
		return "postjob";
	}
	
	@GetMapping("/displayImage/{entityId}")
    public String displayImage(@PathVariable int entityId, Model model) {
        String base64Image = jobPostServices.getImageAsBase64(entityId);
        if (base64Image != null) {
            model.addAttribute("base64Image",base64Image);
        }
        return "home";
    }
	
}
