package com.authbridge.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.authbridge.model.StopWordModel;
import com.authbridge.model.StopWordType;

@Transactional
public interface StopWordsDao extends CrudRepository<StopWordModel, Serializable>{
	
	public List<StopWordModel> findAll();
	public List<StopWordModel> findByTypeOrderByWordDesc(StopWordType swt);

}
