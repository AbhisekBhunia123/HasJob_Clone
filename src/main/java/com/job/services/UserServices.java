package com.job.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.Repositories.UserRepo;
import com.job.entities.User;

@Service
public class UserServices {
	
	@Autowired
	UserRepo userRepo;
	
	public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.format("%06d", otp);
    }
	
	public User findByEmail(String email) {
		return userRepo.findByPrimaryEmail(email);
	}
	
	public User registerUser(String username,String email,String password) {
		try {
			User user = new User();
			List<String> list = new ArrayList<>();
			list.add(email);
			user.setEmails(list);
			user.setName(username);
			user.setMobileNo(0);
			user.setPrimaryEmail(email);
			user.setPassword(password);
			user.setUserName(null);
			user.setTimezone(null);
			return userRepo.save(user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean deleteAccount(int id) {
		try {
			userRepo.deleteById(id);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateUser(String name,String username,String timezone,int userId) {
		User user = userRepo.findById(userId).get();
		user.setName(name);
		user.setUserName(username);
		user.setTimezone(timezone);
		userRepo.save(user);
		return true;
	}
	
	public boolean setPassword(int userId,String password) {
		User user = userRepo.findById(userId).get();
		user.setPassword(password);
		userRepo.save(user);
		return true;
	}
	
	public boolean addEmail(int userId,String email) {
		User user = userRepo.findById(userId).get();
		List<String> emails = user.getEmails();
		emails.add(email);
		userRepo.save(user);
		return true;
	}
	
	public boolean setAsPrimary(int userId,String email) {
		User user = userRepo.findById(userId).get();
		user.setPrimaryEmail(email);
		userRepo.save(user);
		return true;
	}
}
