package com.authbridge.repository;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.authbridge.model.StateModel;

@Transactional
public interface StateModelDao extends CrudRepository<StateModel, Serializable>{
	
	public List<StateModel> findAllByOrderByStateName();
	
	
	//public List<StateAliasModel> findAllOrderByAliasNameDesc();

}
