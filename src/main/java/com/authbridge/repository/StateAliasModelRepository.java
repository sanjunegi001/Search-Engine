package com.authbridge.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.authbridge.model.StateAliasModel;

@Transactional
public interface StateAliasModelRepository extends CrudRepository<StateAliasModel, Serializable>{
	
	public List<StateAliasModel> findAllByOrderByAliasNameDesc();
	//public List<StateAliasModel> findAllOrderByAliasNameDesc();

}
