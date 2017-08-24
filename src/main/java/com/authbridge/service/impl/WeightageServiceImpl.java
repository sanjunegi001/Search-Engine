package com.authbridge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.authbridge.DTO.WeightageDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.model.WeightagePercentage;
import com.authbridge.repository.WeightageRepository;
import com.authbridge.service.WeightageService;

@Component
public class WeightageServiceImpl implements WeightageService {
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(WeightageServiceImpl.class);

	@Autowired
	private WeightageRepository weightageRepository;

	@Override
	public void setWeightage(WeightageDTO wdt) {
		WeightagePercentage wt = getWeightagePercentage(wdt);
		weightageRepository.save(wt);
	}

	/**
	 * Returns weightage details based upon name. If no weightage details are found,
	 * returns a default weightage value of 0.0.
	 * 
	 * @param Weightage name
	 * @return Weightage details; Weightage percentage of 0.0 if not found
	 */
	@Override
	public WeightageDTO getWeightage(String name) {
		WeightagePercentage percentage = this.getWeightageByName(name);
		return convertToDTO(percentage, name);
	}

	@Override
	public List<WeightageDTO> getWeightagesList() {
		// TODO: could optimize to get all the weightage records using findAll() instead of firing 
		// separate queries
		List<WeightageDTO> list = new ArrayList<WeightageDTO>();
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.N_W));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.N_W_T));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.A_W));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.A_W_T));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.F_W));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.F_W_T));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.S_W));
		list.add(this.getWeightage(AUTHBRIDGECONSTANT.D_W));
		return list;
	}

	public Map<String, WeightageDTO> getAllWeightages() {
		// TODO: could optimize to get all the weightage records using findAll() instead of firing 
		// separate queries
		Map<String, WeightageDTO> weightages = new HashMap<>();
		weightages.put(AUTHBRIDGECONSTANT.N_W, this.getWeightage(AUTHBRIDGECONSTANT.N_W));
		weightages.put(AUTHBRIDGECONSTANT.N_W_T, this.getWeightage(AUTHBRIDGECONSTANT.N_W_T));
		weightages.put(AUTHBRIDGECONSTANT.A_W, this.getWeightage(AUTHBRIDGECONSTANT.A_W));
		weightages.put(AUTHBRIDGECONSTANT.A_W_T, this.getWeightage(AUTHBRIDGECONSTANT.A_W_T));
		weightages.put(AUTHBRIDGECONSTANT.F_W, this.getWeightage(AUTHBRIDGECONSTANT.F_W));
		weightages.put(AUTHBRIDGECONSTANT.F_W_T, this.getWeightage(AUTHBRIDGECONSTANT.F_W_T));
		weightages.put(AUTHBRIDGECONSTANT.S_W, this.getWeightage(AUTHBRIDGECONSTANT.S_W));
		weightages.put(AUTHBRIDGECONSTANT.D_W, this.getWeightage(AUTHBRIDGECONSTANT.D_W));
		return weightages;
	}

	/**
	 * Retrieves weightage percentage based upon the name of given weightage DTO,
	 * sets updated value from DTO and returns.
	 * 
	 * If weightage percentage is not found based upon name, it creates a new weightage percentage
	 * with the given name and returns it.
	 * 
	 * @return Weightage percentage object
	 */
	private WeightagePercentage getWeightagePercentage(WeightageDTO weightageDTO) {
		WeightagePercentage wt = this.getWeightageByName(weightageDTO.getName());
		if(wt == null) {
			LOG.info("Given weightage percentage not found based upon name. Creating new.");
			wt = new WeightagePercentage();
			wt.setName(weightageDTO.getName());
		}
		wt.setValue(weightageDTO.getWeightage());
		return wt;
	}

	private WeightagePercentage getWeightageByName(String weightage) {
		return weightageRepository.findByName(weightage);
	}

	private WeightageDTO convertToDTO(WeightagePercentage percentage, String name) {
		WeightageDTO weight = new WeightageDTO();

		if(percentage != null) {
			weight.setWeightage(percentage.getValue());
			weight.setName(percentage.getName());
			weight.setWeightId(percentage.getWeightId());
		} else {
			weight.setWeightage(0.0F);
			weight.setName(name);
		}

		return weight;
	}
}
