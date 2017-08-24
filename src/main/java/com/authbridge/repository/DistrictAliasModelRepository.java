package com.authbridge.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.authbridge.model.DistrictAliasModel;
import com.authbridge.model.DistrictModel;

@Transactional
public interface DistrictAliasModelRepository extends CrudRepository<DistrictModel, Serializable>{
	
	public List<DistrictModel> findAllByOrderByDistrictName();

}
