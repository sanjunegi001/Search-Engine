package com.authbridge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.authbridge.model.WeightagePercentage;

public interface WeightageRepository extends PagingAndSortingRepository<WeightagePercentage, Integer>{

	public WeightagePercentage findByName(String name);
	
}
