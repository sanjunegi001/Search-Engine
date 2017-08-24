package com.authbridge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.authbridge.model.CheckIdPartyIdMapper;
import com.authbridge.repository.CheckPartyMappingRepository;
import com.authbridge.service.CheckPartyMappingService;

@Component
public class CheckPartyMappingServiceImpl implements CheckPartyMappingService {
	
	@Autowired
	private CheckPartyMappingRepository checkPartyMappingRepository;

	/**
	 * To retrieve all the checkIdPartyIdMap from the repository
	 */
	@Override
	public List<CheckIdPartyIdMapper> getAllCheckPartyMapping() {
		List<CheckIdPartyIdMapper> checkIdPartyIdMapList = (List<CheckIdPartyIdMapper>) checkPartyMappingRepository.findAll();
		return checkIdPartyIdMapList;
	}

	@Override
	public void setCheckPartyMapping(CheckIdPartyIdMapper checkIdPartyIdMapper) {
		checkPartyMappingRepository.save(checkIdPartyIdMapper);
	}

	@Override
	public CheckIdPartyIdMapper findByCheckIdAndPartyId(Integer checkId, Integer partyId) {
		return checkPartyMappingRepository.findByCheckIdAndPartyId(checkId, partyId);
	}

}
