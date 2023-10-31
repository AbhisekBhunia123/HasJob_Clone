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

import com.job.entities.Organization;
import com.job.entities.User;
import com.job.services.UserServices;

@Controller
public class NotificationController {

	@Autowired
	UserServices userServices;
	
	
	@GetMapping("/account/notification")
	public String showNotification(Model model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		model.addAttribute("user",user);
		return "notification";
	}
}
