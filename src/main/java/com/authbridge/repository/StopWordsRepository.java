package com.authbridge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.authbridge.model.StopWordModel;

public interface StopWordsRepository extends PagingAndSortingRepository<StopWordModel, Integer> {

}
