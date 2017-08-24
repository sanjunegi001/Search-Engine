package com.authbridge.service;

import java.util.List;
import java.util.Map;

import com.authbridge.DTO.WeightageDTO;

public interface WeightageService {
	
	public void setWeightage(WeightageDTO wdt);

	public WeightageDTO getWeightage(String name);

	public List<WeightageDTO> getWeightagesList();

	/**
	 * Returns a map with all weightages with weightage name as the key.
	 * 
	 * @return
	 */
	public Map<String, WeightageDTO> getAllWeightages();
}
