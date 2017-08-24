package com.authbridge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.authbridge.model.ImportScheduler;

public interface ImportSchedulerRepository extends
		PagingAndSortingRepository<ImportScheduler, Integer> {
	public ImportScheduler findOneByType(String name);
}
