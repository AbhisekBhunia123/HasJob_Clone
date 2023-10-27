package com.job.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.job.entities.Job;
import com.job.entities.Organization;
import com.job.services.JobPostServices;

@Controller
public class PostController {
	
	@Autowired
	JobPostServices jobPostServices;
	
	@PostMapping("/postjob")
	public String postJob(@RequestParam("headline") String headline,
			@RequestParam("headlineB") String headlineB,
			@RequestParam("type") String type,
			@RequestParam("category") String category,
			@RequestParam("location") String location,
			@RequestParam("description") String description,
			@RequestParam("jobPerks") String jobPerks,
			@RequestParam("jobPay") String jobPay,
			@RequestParam("equityCompensection") float equityCompensection,
			@RequestParam("jobNeed") String jobNeed,
			@RequestParam("intermediaries") String intermediaries,
			@RequestParam("organizationName") String organizationName,
			@RequestParam("url") String url,
			@RequestParam("email") String email,
			@RequestParam("payType") String payType,
			@RequestParam("payRange") String payRange,
			@RequestParam("collaborators") String collaborators,
			@RequestParam("logo") MultipartFile logo) {
		
		boolean isPublished = jobPostServices.publishJob(headline, headlineB, type, category, location, description, jobPerks, jobPay, equityCompensection, jobNeed, intermediaries, organizationName, url, email, collaborators, logo,payType,payRange);
		if(isPublished) {
			return "redirect:/";
		}
		return "redirect:/new";
	}
	
	@PostMapping("/updatejob")
	public String updateJob(@RequestParam("jobId") int jobId,Model model) {
		Job  job = jobPostServices.getJobById(jobId);
		model.addAttribute("job",job);
		return "postjob";
	}
	
	@GetMapping("/{orgEmail}/{jobId}")
	public String shodIndivisualJob(@PathVariable("orgEmail") String orgEmail,@PathVariable("jobId") int jobId,Model model) {
		Job job = jobPostServices.getJobById(jobId);
		List<Job> jobs = jobPostServices.getJobsInLocation(job.getLocation());
		Set<String> locations = jobPostServices.getAllLocations();
		String base64Image = jobPostServices.getImageAsBase64(jobId);
        if (base64Image != null) {
            model.addAttribute("base64Image",base64Image);
        }
		model.addAttribute("job",job);
		model.addAttribute("jobs",jobs);
		model.addAttribute("locations",locations);
		return "jobpage";
	}
	
	@PostMapping("/apply")
	public String applyForJob(@RequestParam("empName") String applicantName,
			@RequestParam("empEmail") String applicantEmail,
			@RequestParam("empPhone") String applicantPhone,
			@RequestParam("applicantDetail") String applicantDetails,
			@RequestParam("jobId") int jobId
			) {
		jobPostServices.applyForJob(applicantName,applicantEmail,applicantPhone,applicantDetails,jobId);
		return "successfullapply";
	}
	

}
