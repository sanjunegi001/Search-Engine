package com.authbridge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.authbridge.model.CheckIdPartyIdMapper;

public interface CheckPartyMappingRepository extends PagingAndSortingRepository<CheckIdPartyIdMapper, Integer>{
	public CheckIdPartyIdMapper findByCheckIdAndPartyId(@Param("check_id")Integer checkId ,@Param("party_id") Integer partyId);
}
