package com.authbridge.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.authbridge.model.Modification;

public interface ModificationRepository extends PagingAndSortingRepository<Modification , Integer> {

	public Modification findByScreen(@Param("screen") String screen);
	
	public Modification findByStatus(@Param("status") String status);

	public Modification findByOperation(@Param("operation")String operation);
	
	public Modification findByOperationAndScreen(@Param("operation")String operation ,@Param("screen") String screen );

	
}
