package com.authbridge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.authbridge.model.StopWordModel;
import com.authbridge.repository.StopWordsRepository;
import com.authbridge.service.StopWordsService;

@Component
public class StopWordsServiceImpl implements StopWordsService {
	
	@Autowired
	private StopWordsRepository stopWordsRepository;

	/**
	 * To retrieve all the stopwords from the repository
	 */
	@Override
	public List<StopWordModel> getAllStopWords() {
		List<StopWordModel> stopWordsList = (List<StopWordModel>) stopWordsRepository.findAll();
		return stopWordsList;
	}

}
