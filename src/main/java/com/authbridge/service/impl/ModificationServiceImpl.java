package com.authbridge.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.authbridge.DTO.ModificationDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.model.Modification;
import com.authbridge.repository.ModificationRepository;
import com.authbridge.service.ModificationService;
import com.authbridge.utility.Utility;

@Component
public class ModificationServiceImpl implements ModificationService {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(ModificationServiceImpl.class);
	
	@Autowired
	private ModificationRepository modificationRepository;
	
	@Autowired
	private Utility utility;
	
	@Override
	public List<Modification> findAll() {
		return (List<Modification>) modificationRepository.findAll();
	}
	
	@Override
	public List<ModificationDTO> findAllDTO() {
		List<Modification> modifications = this.findAll();
		List<ModificationDTO> modificationsDTO = new ArrayList<ModificationDTO>();
		for (Modification modification : modifications) {
			modificationsDTO.add(this.classToDTO(modification));
		}
		 return modificationsDTO;
	}

	@Override
	public Modification findById(Integer id) {
		return modificationRepository.findOne(id);
	}

	@Override
	public Modification findByScreen(String screen) {
		return modificationRepository.findByScreen(screen);
	}

	
	@Override
	public Modification persist(Modification modifications) {
		return modificationRepository.save(modifications);
	}

	@Override
	public void deleteModification(Modification modifications) {
		modificationRepository.delete(modifications);
	}

	@Override
	public Modification saveOrUpdate(String screen , String operation){
		Modification modification = this.findByScreen(screen);
		if(modification == null){
			modification = new Modification();
		}
		modification.setScreen(screen);
		modification.setOperation(operation);
		modification.setModifiedOn(new Date());
		return this.persist(modification);
	}
	
	/**
	 * Conversion to modification dto based on the screen and operation
	 * such as addition/update. Abbr, stop words, aliases can be added or updated
	 * The modifications will be noted with modified time.
	 */
	@Override
	public ModificationDTO classToDTO(Modification modification){
		ModificationDTO modificationDTO = new ModificationDTO();
		modificationDTO.setId(modification.getId());
		modificationDTO.setCount(modification.getCount());
		try {
			modificationDTO.setModifiedOn(utility.changeDateFormat(modification.getModifiedOn()));
		} catch (ParseException e) {
			LOG.error("Exception in modifing date format");
		}
		modificationDTO.setOperation(modification.getOperation());
		modificationDTO.setScreen(modification.getScreen());
		return modificationDTO;
	}

	/**
	 * To save or update the modifications based on the screen and the type of operation
	 */
	@Override
	public Modification saveOrUpdate(String screen, String operation, Long count) {
		Modification modification = this.findByOperationAndScreen(operation, screen);
		if(modification == null){
			modification = new Modification();
		}
		modification.setScreen(screen);
		modification.setOperation(operation);
		modification.setModifiedOn(new Date());
		modification.setCount(count);
		return this.persist(modification);
	}

	/**
	 * To save or update the modifications based on the screen and the type of operation.
	 * It will persist the modification along with the notes.
	 */
	@Override
	public Modification saveOrUpdate(String screen, String operation,
			Long count, String notes) {
		Modification modification = this.findByOperationAndScreen(operation, screen);
		if(modification == null){
			modification = new Modification();
		}
		modification.setScreen(screen);
		modification.setOperation(operation);
		modification.setModifiedOn(new Date());
		modification.setCount(count);
		modification.setNotes(notes);
		return this.persist(modification);
	}
	
	/**
	 * To save or update the modifications based on the screen and the type of operation.
	 * Modification is persisted along with the status and notes information.
	 */
	@Override
	public Modification saveOrUpdate(String screen, String operation,
			Long count, String notes ,String status) {
		Modification modification = this.findByOperationAndScreen(operation, screen);
		if(modification == null){
			modification = new Modification();
		}
		modification.setScreen(screen);
		modification.setOperation(operation);
		modification.setModifiedOn(new Date());
		modification.setCount(count);
		modification.setNotes(notes);
		modification.setStatus(status);
		return this.persist(modification);
	}

	@Override
	public Modification saveOrUpdate(String screen, String operation,
			String status) {
		Modification modification = this.findByOperationAndScreen(operation, screen);
		if(modification == null){
			modification = new Modification();
		}
		modification.setScreen(screen);
		modification.setOperation(operation);
		modification.setModifiedOn(new Date());
		modification.setStatus(status);
		return this.persist(modification);
	}

	@Override
	public Modification findByStatus(String status) {
		return modificationRepository.findByStatus(status);
	}
	
	@Override
	public Modification findByOperation(String operation) {
		return modificationRepository.findByOperation(operation);
	}
	
	@Override
	public Modification findByOperationAndScreen(String operation ,String screen ) {
		return modificationRepository.findByOperationAndScreen(operation ,screen );
	}
	
	
	@Override
	public Boolean checkByInProgress() {
		Modification modifications = this.findByStatus(AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
		if(modifications !=null)
			return true;
		return false;
	}
	
}
