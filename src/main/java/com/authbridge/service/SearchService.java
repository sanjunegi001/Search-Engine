package com.authbridge.service;

import com.authbridge.DTO.SearchCaseDetailDTO;

/**
 * Provides operations for search feature.
 */
public interface SearchService {

	/**
	 * Returns {@link SearchCaseDetailDTO} filled with default values for
	 * fields.
	 * 
	 * @return
	 */
	public SearchCaseDetailDTO getDefSearchParams();
}
