package com.job.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.job.Repositories.UserRepo;
import com.job.entities.User;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByPrimaryEmail(username);
		System.out.print(username + " ");
		if (user == null) {
			System.out.print("hello");
			throw new UsernameNotFoundException("Could not found User !");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		return customUserDetails;
	}

}