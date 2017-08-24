package com.authbridge.service;

import java.util.List;

import com.authbridge.model.CheckIdPartyIdMapper;

public interface CheckPartyMappingService {
	public List<CheckIdPartyIdMapper> getAllCheckPartyMapping();
	public void setCheckPartyMapping(CheckIdPartyIdMapper checkIdPartyIdMapper);
	public CheckIdPartyIdMapper findByCheckIdAndPartyId(Integer checkId ,Integer partyId );
}
