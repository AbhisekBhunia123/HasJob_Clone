package com.job.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		Set<String> locations = jobPostServices.getAllLocations();
		model.addAttribute("organizations",organizations);
		model.addAttribute("locations",locations);
		return "home";
	}
	
	@GetMapping("/new")
	public String showPostJob(Model model) {
		Set<String> locations = jobPostServices.getAllLocations();
		model.addAttribute("locations",locations);
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
	
	
	@PostMapping("/multifilter")
	public String multiFilter(@RequestParam(name = "t",required = false) String[] types,
			@RequestParam(name = "c",required = false)String[] categories,
			@RequestParam(name = "location",required = false)String[] locations,
			@RequestParam(name = "payType",required = false) String payType,
			@RequestParam(name = "payRange",required = false) String payRange,
			@RequestParam(name = "searchText",required = false) String searchText,
			Model model) {
		System.out.println(searchText);
		List<Job> jobs = jobPostServices.filterJobs(types,categories,locations,payType,payRange,searchText);
		Set<String> navlocations = jobPostServices.getAllLocations();
		model.addAttribute("locations",navlocations);
		if(locations != null)model.addAttribute("filterLocation",new ArrayList<>(Arrays.asList(locations)));
		if(types != null) model.addAttribute("filterType",new ArrayList<>(Arrays.asList(types)));
		if(categories != null) model.addAttribute("filterCategory",new ArrayList<>(Arrays.asList(categories)));
		model.addAttribute("filterPayType",payType);
		model.addAttribute("filterPayRange",payRange);
		model.addAttribute("filterJobs",jobs);
		model.addAttribute("filterSearchText",searchText);
		System.out.println(jobs);
		return "home";
	}
}
