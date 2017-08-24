package com.authbridge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.authbridge.model.User;
import com.authbridge.repository.UserRepository;
import com.authbridge.service.UserService;

@Component
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	/**
	 * To find the user by user id
	 */
	@Override
	public User findById(Long id) {
		return userRepository.findOneById(id);
	}

	/**
	 * To find the user by username. Will get the username as input
	 */
	@Override
	public User findByUserName(String userName) {
		return userRepository.findOneByUserName(userName);
	}

	/**
	 * To create a new user
	 */
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * To load the proper user and get the user details using the username
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = this.findByUserName(userName);
		if(user == null){
			throw new UsernameNotFoundException("No user present with username: "+userName);
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.getAuthorities());
	}
	
	

}
