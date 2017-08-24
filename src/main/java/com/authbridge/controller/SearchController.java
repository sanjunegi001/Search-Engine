package com.authbridge.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.authbridge.DTO.SearchCaseDetailDTO;
import com.authbridge.DTO.SearchResultDTO;
import com.authbridge.service.FetchingDataService;
import com.authbridge.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SearchController {
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SearchController.class);

	@Value("${pagination.numberOfRows}")
	private Integer numberOfRows;
	
	@Value("${pagination.perpage}")
	private Integer perpage;
	
	@Autowired
	private FetchingDataService fetchingDataService;

	@Autowired
	private SearchService searchService;

	/**
	 * To fetch the details of the search query. Will call the fetching service to get 
	 * all the results.
	 * @param searchCaseDetailDTO
	 * @param model
	 * @return
	 */
	@RequestMapping("/searchQuery")
	public String fetchDataByNameAndAddress(@ModelAttribute("searchCaseDetailDTO")
			SearchCaseDetailDTO searchCaseDetailDTO, Model model) {
		LOG.debug("Entering search controller, with parameters: {}", searchCaseDetailDTO);

		SearchResultDTO searchResultDTO = null;
		try {
			searchResultDTO = fetchingDataService.getAllResults(searchCaseDetailDTO);
		} catch (Exception e) {
			LOG.error("Solr exception", e);
		}
		searchResultDTO.setPerPage(perpage);
		searchResultDTO.setPerPaginQuery(numberOfRows);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String searchResultDTOString = mapper.writeValueAsString(searchResultDTO);
			model.addAttribute("searchResultDTO",searchResultDTOString);
		} catch (JsonProcessingException e) {
			LOG.error("Json parse exception", e);
		}
		return "search";
	}

	/**
	 * Search landing page to be displayed to the user.
	 * @param model
	 * @return search landing page.
	 */
	@RequestMapping({"/search"})
	public String fetchDataByNameAndAddress(Model model) {
		LOG.trace("Entering search controller, for search landing view.");
		SearchCaseDetailDTO searchCaseDetailDTO = searchService.getDefSearchParams();
		model.addAttribute("searchCaseDetailDTO", searchCaseDetailDTO);
		return "search";
	}
}
