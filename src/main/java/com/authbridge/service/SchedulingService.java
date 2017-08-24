package com.authbridge.service;

import java.util.Map;

import com.authbridge.DTO.SchedulerDTO;
import com.authbridge.model.ImportScheduler;

public interface SchedulingService {
		
		//to perform full indexing of data
		public void fullIndexing();
		
		//delete this method once testing is done
		public void deltaIndexing();

		public ImportScheduler findOneByType(String name);
		
		public Integer saveOrUpdateScheduling(ImportScheduler importScheduler );
		
		public ImportScheduler getRow();
		
		public void removeScheduling(String importType);
		
		public SchedulerDTO classToDTO(ImportScheduler importScheduler);
		
		public ImportScheduler dtoToClass(SchedulerDTO schedulerDTO);

		public Map<String, SchedulerDTO> getAllSchedulerTypes();

}
