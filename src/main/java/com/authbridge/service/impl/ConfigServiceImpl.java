package com.authbridge.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authbridge.enums.StopWordTypeEnum;
import com.authbridge.model.StateModel;
import com.authbridge.model.StopWordModel;
import com.authbridge.repository.DistrictAliasModelRepository;
import com.authbridge.repository.StateModelDao;
import com.authbridge.repository.StopWordTypeDao;
import com.authbridge.repository.StopWordsDao;
import com.authbridge.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {


	@Autowired
	DistrictAliasModelRepository districtAliasModelRepository;

	@Autowired
	StateModelDao stateDao;
	
	@Autowired
	StopWordsDao stopWordsDao;


	@Autowired
	StopWordTypeDao stopWordTypeDao;

	private List<StateModel> state;
	private List<String> swsName;
	private List<String> swsAddress;
	
	@Override
	public List<String> getAddressStopwords() {
		// Returns Stop words for Address field
		return swsAddress;
	}

	
	@Override
	public List<String> getNameStopwords() {
		// Returns Stop words for Name field
		return swsName;

	}

	@PostConstruct
	void loadConfig() {
		/*swsName = new ArrayList<String>();
		swsAddress = new ArrayList<String>();
		for (StopWordModel stopWord: stopWordsDao.findByTypeOrderByWordDesc(stopWordTypeDao.findById(StopWordTypeEnum.NAME.getValue()))){
			swsName.add(stopWord.getWord());
		}
		
		for (StopWordModel stopWord:stopWordsDao
				.findByTypeOrderByWordDesc(stopWordTypeDao.findById(StopWordTypeEnum.ADDRESS.getValue()))){
			swsAddress.add(stopWord.getWord());
			
		}*/
		swsName = new ArrayList<String>();
		swsAddress = new ArrayList<String>();
		for (StopWordModel stopWord: stopWordsDao.findByTypeOrderByWordDesc(stopWordTypeDao.findById(StopWordTypeEnum.NAME.getValue()))){
			swsName.add(stopWord.getWord());
		}
		
		for (StopWordModel stopWord:stopWordsDao
				.findByTypeOrderByWordDesc(stopWordTypeDao.findById(StopWordTypeEnum.ADDRESS.getValue()))){
			swsAddress.add(stopWord.getWord());
			
		}
		state = stateDao.findAllByOrderByStateName();
		/*for(StateModel stateModel:state){
			Collection<DistrictModel> list = stateModel.getDistrict();
			Collections.sort(list, new Comparator<T>() {
				
			});
			
		}*/
		

	}

	public List<StateModel> getStateList() {
		return state;
	}




}
