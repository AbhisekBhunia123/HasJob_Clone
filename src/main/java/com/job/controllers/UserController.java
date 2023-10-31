package com.job.controllers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.job.Repositories.TokenRepo;
import com.job.entities.Token;
import com.job.entities.User;
import com.job.services.EmailService;
import com.job.services.UserServices;

@Controller
public class UserController {

	@Autowired
	private EmailService emailService;

	@Autowired
	UserServices userServices;

	@Autowired
	TokenRepo tokenRepo;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@GetMapping("/loginpage")
	public String showLogin() {
		return "login";
	}

	@PostMapping("/sendotp")
	public String showOTPSection(Model model, @RequestParam("username") String username,
			@RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			User user = userServices.findByEmail(email);
			List<User> users = userServices.findAllUser();
			if (user != null){
				return "redirect:/";
			}
			for(User u : users) {
				if(u.getEmails().contains(email)) {
					return "redirect:/";
				}
			}
			String otp = userServices.generateOTP();
			Token existToken = tokenRepo.findByEmail(email);
			if (existToken != null) {
				existToken.setToken(otp);
				tokenRepo.save(existToken);
			} else {
				Token token = new Token();
				token.setEmail(email);
				token.setToken(otp);
				tokenRepo.save(token);
			}

			emailService.sendRegistrationEmail(email, username, otp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		return "otpsection";
	}

	@PostMapping("/userlogin")
	public String processForRegisterAndLogin(@RequestParam("username") String username,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("c1") String c1, @RequestParam("c2") String c2, @RequestParam("c3") String c3,
			@RequestParam("c4") String c4, @RequestParam("c5") String c5, @RequestParam("c6") String c6,
			Model model) {
		System.out.println(c1 + "" + c2 + "" + c3 + "" + c4 + "" + c5 + "" + c6);
		String userOTP = c1 + "" + c2 + "" + c3 + "" + c4 + "" + c5 + "" + c6;
		String originalOTP = tokenRepo.findByEmail(email).getToken();
		if (userOTP.equals(originalOTP)) {
			userServices.registerUser(username, email, bcryptPasswordEncoder.encode(password));
			model.addAttribute("email",email);
			model.addAttribute("password",password);
			return "submitform"; 
		}
		return "redirect:/";
	}
	
	@PostMapping("/directlogin")
	public String directLogin(Model model, @RequestParam(name = "username",required = false) String username,
			@RequestParam("email") String email, @RequestParam("password") String password) {
		model.addAttribute("email",email);
		model.addAttribute("password",password);
		return "submitform"; 
	}
	
	@PostMapping("/account/delete")
	public String deleteAccount(@RequestParam("id") int userId,Model model) {
		userServices.deleteAccount(userId);
		model.addAttribute("msg","Your account has been deleted....");
		return "logoutmsg";
	}
	
	@GetMapping("/account/showupdate")
	public String showUpdateProfile(Model model,
			Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		model.addAttribute("user",user);
		return "updateprofile";
	}
	
	@PostMapping("/account/updateuser")
	public String updateUserDetails(
			@RequestParam("name") String name,
			@RequestParam("username") String username,
			@RequestParam("timezone") String timezone,
			@RequestParam("userId") int userId) {
		userServices.updateUser(name,username,timezone,userId);
		return "redirect:/account";
	}
	
	@GetMapping("/account/showSetPassword")
	public String showSetPassword(Model model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		model.addAttribute("user",user);
		return "setpassword";
	}
	
	@PostMapping("/account/setpassword")
	public String setpassword(
			@RequestParam("userId") int userId,
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword
			) {
		userServices.setPassword(userId,bcryptPasswordEncoder.encode(password));
		return "redirect:/account";
	}
	
	@GetMapping("/account/showaddemailpage")
	public String addEmail(Model model,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		model.addAttribute("user",user);
		model.addAttribute("mode","simple");
		return "addemailpage";
	}
	
	@PostMapping("/account/addemail")
	public String addEmailWithOTP(Model model,@RequestParam("email") String email,Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userServices.findByEmail(username);
		for(String userEmail : user.getEmails()) {
			if(userEmail.equals(email)) {
				return "redirect:/account";
			}
		}
		String otp = userServices.generateOTP();
		Token existToken = tokenRepo.findByEmail(email);
		if (existToken != null) {
			existToken.setToken(otp);
			tokenRepo.save(existToken);
		} else {
			Token token = new Token();
			token.setEmail(email);
			token.setToken(otp);
			tokenRepo.save(token);
		}

		emailService.sendRegistrationEmail(email, username, otp);
		model.addAttribute("email",email);
		model.addAttribute("user",user);
		model.addAttribute("mode","otp");
		return "addemailpage";
	}
	
	@PostMapping("/account/directaddemail")
	public String directAddEmail(@RequestParam("userId") int userId,
			@RequestParam("email") String email,
			@RequestParam("c1") String c1, @RequestParam("c2") String c2, @RequestParam("c3") String c3,
			@RequestParam("c4") String c4, @RequestParam("c5") String c5, @RequestParam("c6") String c6,
			Model model) {
		String userOTP = c1 + "" + c2 + "" + c3 + "" + c4 + "" + c5 + "" + c6;
		String originalOTP = tokenRepo.findByEmail(email).getToken();
		if (userOTP.equals(originalOTP)) {
			userServices.addEmail(userId, email);
			return "redirect:/account";
		}
		return "redirect:/account";
	}
	
	@PostMapping("/account/setasprimary")
	public String setAsPrimary(@RequestParam("userId") int userId,@RequestParam("primeryEmail") String email,Model model) {
		userServices.setAsPrimary(userId,email);
		model.addAttribute("msg","You changed your primary email so for double check we redirect you to login page .. ");
		return "logoutmsg";
	}

}
