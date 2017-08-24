package com.authbridge.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.authbridge.model.StopWordType;

@Transactional
public interface StopWordTypeDao extends CrudRepository<StopWordType, Serializable>{
	
	public List<StopWordType> findAll();
	public StopWordType findById(Integer id);

}
