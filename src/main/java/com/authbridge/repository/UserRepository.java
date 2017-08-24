package com.authbridge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.authbridge.model.User;


public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	public User findOneById(@Param("id") Long id);
	
	public User findOneByUserName(@Param("username") String userName);
	
	
}
