package com.authbridge.service;

import java.util.List;

import com.authbridge.model.DistrictModel;
import com.authbridge.model.StateModel;

public interface ConfigService {
	
	
	public List<StateModel> getStateList();
	public List<String> getNameStopwords();
	public List<String> getAddressStopwords();

}
