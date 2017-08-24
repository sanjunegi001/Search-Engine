package com.authbridge.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import com.authbridge.DTO.SchedulerDTO;
import com.authbridge.config.SchedulingConfig;
import com.authbridge.constant.AuthBridgeEnum;
import com.authbridge.model.ImportScheduler;
import com.authbridge.repository.ImportSchedulerRepository;
import com.authbridge.service.SchedulingService;
import com.authbridge.utility.Utility;

@Component
public class SchedulingServiceImpl implements SchedulingService {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SchedulingServiceImpl.class);
	
	@Autowired
	private ImportSchedulerRepository importSchedulerRepository;
	
	@Autowired
	private SchedulingConfig schedulingConfig;
	
	@Autowired
	private Utility utility;

	
	String fullIndexing="Full_Import";
	
	@Override
	public void fullIndexing() {
		ImportScheduler importScheduler = importSchedulerRepository.findOneByType(fullIndexing);
		LOG.info("ScheduledTaskRegistrar is being called to configure the full indexing.");
		ScheduledTaskRegistrar taskRegistrar = new ScheduledTaskRegistrar();
		schedulingConfig.setFlag(1);
		schedulingConfig.configureTasks(taskRegistrar);
	}

	@Override
	public void deltaIndexing() {
		// TODO Auto-generated method stub
		ScheduledTaskRegistrar taskRegistrar = new ScheduledTaskRegistrar();
		//schedulingConfig.configureTasks(taskRegistrar);
	}

	@Override
	public ImportScheduler findOneByType(String name) {
		return importSchedulerRepository.findOneByType(name);
	}

	@Override
	public Integer saveOrUpdateScheduling(ImportScheduler importScheduler ) {
		importSchedulerRepository.save(importScheduler);
		ScheduledTaskRegistrar taskRegistrar = new ScheduledTaskRegistrar();
		if(importScheduler.getType().equalsIgnoreCase(AuthBridgeEnum.Type.Full_Import.toString())){
			if(importScheduler.getRecurring().equalsIgnoreCase(AuthBridgeEnum.Recurring.None.toString())){
				schedulingConfig.setFlag(3);
			}
			else{
				schedulingConfig.setFlag(1);
			}
		}
		if(importScheduler.getType().equalsIgnoreCase(AuthBridgeEnum.Type.Delta_Import.toString())){
			if(importScheduler.getRecurring().equalsIgnoreCase(AuthBridgeEnum.Recurring.None.toString())){
				schedulingConfig.setFlag(4);
			}
			else{
				schedulingConfig.setFlag(2);
			}
		}
		LOG.info("Import flag is set to "+schedulingConfig.getFlag()+".Calling scheduling config to trigger execution.");
		schedulingConfig.configureTasks(taskRegistrar);
		return importScheduler.getSchedulerId();
	}
	
	public ImportScheduler getRow(){
		ImportScheduler importScheduler = null;
		try{
			
			if(importSchedulerRepository.count() >0){
				importScheduler = importSchedulerRepository.findAll().iterator().next();
			}
		}catch(Exception e){
			LOG.info("No rows found");
		}
		return importScheduler;
	}

	@Override
	public SchedulerDTO classToDTO(ImportScheduler importScheduler) {
		
		SchedulerDTO schedulerDTO = null;
		if(importScheduler!= null){
			schedulerDTO = new SchedulerDTO();
			try {
				schedulerDTO.setDateTime(utility.changeDateFormat(importScheduler.getDateTime()));
			} catch (ParseException e) {
			LOG.info("Some problem with time comversion");
			}
			schedulerDTO.setRecurring(importScheduler.getRecurring());
			schedulerDTO.setType(importScheduler.getType());
			schedulerDTO.setId(importScheduler.getSchedulerId());
		}
		return schedulerDTO;
	}

	@Override
	public ImportScheduler dtoToClass(SchedulerDTO schedulerDTO) {
		ImportScheduler importScheduler = null;
		if(schedulerDTO != null){
			importScheduler = new ImportScheduler();
			importScheduler.setDateTime(utility.convertToDate(schedulerDTO.getDateTime()));
			importScheduler.setRecurring(schedulerDTO.getRecurring());
			importScheduler.setType(schedulerDTO.getType());
			importScheduler.setSchedulerId(schedulerDTO.getId());
		}
		return importScheduler;
	}

	@Override
	public void removeScheduling(String importType) {
		importSchedulerRepository.delete(this.findOneByType(importType));
		
	}
	
	@Override
	public Map<String,SchedulerDTO> getAllSchedulerTypes(){
		Map<String,SchedulerDTO> listScheduler = new HashMap<String,SchedulerDTO>();
		List<String> types = AuthBridgeEnum.typeAsList();
		for (String type : types) {
			listScheduler.put(type, this.classToDTO(this.findOneByType(type)));
		}
		return listScheduler;
	}

}
