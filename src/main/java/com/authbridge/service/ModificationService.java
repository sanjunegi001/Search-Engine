package com.authbridge.service;

import java.util.List;

import com.authbridge.DTO.ModificationDTO;
import com.authbridge.model.Modification;

public interface ModificationService {

	public List<Modification> findAll();
	
	public List<ModificationDTO> findAllDTO();
	
	public Modification findById(Integer id);
	
	public Modification findByScreen(String screen);
	
	public Modification findByStatus(String status);
	
	public Modification persist(Modification modifications);
	
	public void deleteModification(Modification modifications);

	public Modification saveOrUpdate(String screen , String operation);
	
	public Modification saveOrUpdate(String screen , String operation,String status);
	
	public Modification saveOrUpdate(String screen , String operation, Long count);
	
	public Modification saveOrUpdate(String screen , String operation, Long count, String notes);

	public ModificationDTO classToDTO(Modification modification);

	public Modification saveOrUpdate(String screen, String operation, Long count,
			String notes, String status);
	
	public Boolean checkByInProgress();

	public Modification findByOperation(String operation);

	public Modification findByOperationAndScreen(String screen, String operation);

	
	
}
