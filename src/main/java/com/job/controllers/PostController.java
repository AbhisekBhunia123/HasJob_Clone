package com.job.controllers;

import java.util.List;
import java.util.Set;

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

import com.job.entities.Application;
import com.job.entities.Job;
import com.job.entities.Organization;
import com.job.entities.User;
import com.job.services.EmailService;
import com.job.services.JobPostServices;
import com.job.services.UserServices;

@Controller
public class PostController {
	
	@Autowired
	JobPostServices jobPostServices;
	
	@Autowired
	UserServices userServices;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping("/account/postjob")
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
			@RequestParam("logo") MultipartFile logo,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		boolean isPublished = jobPostServices.publishJob(headline, headlineB, type, category, location, description, jobPerks, jobPay, equityCompensection, jobNeed, intermediaries, organizationName, url, email, collaborators, logo,payType,payRange,user);
		if(isPublished) {
			return "redirect:/";
		}
		return "redirect:/account/new";
//		String formData = "<form  method='post' th:action='http://localhost:8080/postjob}' enctype='multipart/form-data'>"
//				+ "    <input type='hidden' name='headline' th:value='${'"+headline+"'}' />"
//				+ "    <input type='hidden' name='headlineB' th:value='${'"+headlineB+"'}' />"
//				+ "    <input type='hidden' name='type' th:value='${'"+type+"'}' />"
//				+ "    <input type='hidden' name='category' th:value='${'"+category+"'}' />"
//				+ "    <input type='hidden' name='location' th:value='${'"+location+"'}' />"
//				+ "    <input type='hidden' name='description' th:value='${'"+description+"'}' />"
//				+ "    <input type='hidden' name='jobPerks' th:value='${'"+jobPerks+"'}' />"
//				+ "    <input type='hidden' name='jobPay' th:value='${'"+jobPay+"'}' />"
//				+ "    <input type='hidden' name='equityCompensection' th:value='${'"+equityCompensection+"'}' />"
//				+ "    <input type='hidden' name='intermediaries' th:value='${'"+intermediaries+"'}' />"
//				+ "    <input type='hidden' name='organizationName' th:value='${'"+organizationName+"'}' />"
//				+ "    <input type='hidden' name='url' th:value='${'"+url+"'}' />"
//				+ "    <input type='hidden' name='email' th:value='${'"+email+"'}' />"
//				+ "    <input type='hidden' name='payType' th:value='${'"+payType+"'}' />"
//				+ "    <input type='hidden' name='payRange' th:value='${'"+payRange+"'}' />"
//				+ "    <input type='hidden' name='collaborators' th:value='${'"+collaborators+"'}' />"
//				+ "    <input type='hidden' name='logo' th:value='${'"+logo+"'}' />"
//				+ "    <button type='submit' style='width:100px;height:30px;border:none;background-color:#07B7EF'>Post</button>"
//				+ "    </form>";
//		emailService.sendPostJobEmail(email, username,formData);
//		return "laststep";
	}
	
	@PostMapping("/account/updatejob")
	public String updateJob(@RequestParam("jobId") int jobId,Model model) {
		Job  job = jobPostServices.getJobById(jobId);
		model.addAttribute("job",job);
		return "postjob";
	}
	
	@GetMapping("/job/{jobId}")
	public String shodIndivisualJob(@PathVariable("jobId") int jobId,Model model) {
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
	
	@PostMapping("/account/apply")
	public String applyForJob(@RequestParam("empName") String applicantName,
			@RequestParam("empEmail") String applicantEmail,
			@RequestParam("empPhone") String applicantPhone,
			@RequestParam("applicantDetail") String applicantDetails,
			@RequestParam("jobId") int jobId,
			Model model,
			Authentication authentication
			) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		Job job  = jobPostServices.getJobById(jobId);
		for(Application application : user.getApplications()) {
			if(application.getJob().getId() == job.getId()) {
				model.addAttribute("message","You already applied for this job");
				return "successfullapply";
			}
		}
		jobPostServices.applyForJob(applicantName,applicantEmail,applicantPhone,applicantDetails,jobId,user);
		emailService.sendJobSuccessEmail(applicantEmail, applicantName, job);
		emailService.applicantDetailsEmail(job.getOrganization(),job,applicantName,applicantEmail,applicantPhone);
		model.addAttribute("message","Applied successfully");
		return "successfullapply";
	}
	

}
