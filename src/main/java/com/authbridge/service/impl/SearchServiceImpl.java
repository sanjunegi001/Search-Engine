package com.authbridge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authbridge.DTO.SearchCaseDetailDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.service.SearchService;
import com.authbridge.service.WeightageService;

/**
 * Default implementation of {@link SearchService}.
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private WeightageService weightageService;
	
	/**
	 * To get the default search parameters
	 * Descending order is set as sorting order
	 * Start value to be set as 0
	 * To set the weightage information entered by the admin user.
	 */
	@Override
	public SearchCaseDetailDTO getDefSearchParams() {
		SearchCaseDetailDTO searchCaseDetailDTO = new SearchCaseDetailDTO();
		searchCaseDetailDTO.setSort(AUTHBRIDGECONSTANT.SORT_DESC);
		searchCaseDetailDTO.setStart(0);
		searchCaseDetailDTO.setSortField(AUTHBRIDGECONSTANT.SORT_FIELD.WEIGHT_MATCH);
		searchCaseDetailDTO.setNameThreshold(Float.valueOf(
				weightageService.getWeightage(AUTHBRIDGECONSTANT.N_W_T).getWeightage()
			));
		searchCaseDetailDTO.setAddrThreshold(Float.valueOf(
				weightageService.getWeightage(AUTHBRIDGECONSTANT.A_W_T).getWeightage()
			));
		return searchCaseDetailDTO;
	}
}
