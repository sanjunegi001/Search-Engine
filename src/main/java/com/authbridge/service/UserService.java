package com.authbridge.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.authbridge.model.User;

public interface UserService extends UserDetailsService {

	public User findById( Long id);
	
	public User findByUserName( String userName);
	
	public User createUser( User user);
	
}
