package com.authbridge.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.authbridge.DTO.SchedulerDTO;
import com.authbridge.config.Scheduler;
import com.authbridge.constant.AuthBridgeEnum;
import com.authbridge.model.ImportScheduler;
import com.authbridge.model.Modification;
import com.authbridge.service.ModificationService;
import com.authbridge.service.SchedulingService;

@Controller
public class SchedulerController {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(SchedulerController.class);
	
	@Autowired
	private SchedulingService schedulingService;
	
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private ModificationService modificationService;
	
	/**
	 * To get the schedular details to be displayed to the user in schedular page.
	 * Will get the details of both full and delta import
	 * @param model
	 * @return
	 */
	@RequestMapping({"/schedular"})
	public String getSchedular(Model model) {
		LOG.info("schedular page ");
		
		ImportScheduler fullImport = schedulingService.findOneByType(AuthBridgeEnum.Type.Full_Import.toString());
		ImportScheduler deltaImport = schedulingService.findOneByType(AuthBridgeEnum.Type.Delta_Import.toString());
		model.addAttribute("fullImport", schedulingService.classToDTO(fullImport));
		model.addAttribute("deltaImport", schedulingService.classToDTO(deltaImport));
		model.addAttribute("importType", AuthBridgeEnum.Type.values());
		model.addAttribute("isImportRunning", modificationService.checkByInProgress());
		return "schedular";
	}
	/**
	 * Changes made to the schedular function will be persisted 
	 * using this method. Admin can change the schedule for full or delta import.
	 * @param model
	 * @param schedulerDTO
	 * @return
	 */
	@RequestMapping(value="/saveScheduler" , method=RequestMethod.POST)
	public String saveOrUpdate(Model model , @ModelAttribute("schedulerDTO") SchedulerDTO schedulerDTO){
		LOG.info("date for scheduling : "+schedulerDTO);
		Integer id = null;
		String code = null;
		ImportScheduler importScheduler = schedulingService.dtoToClass(schedulerDTO);
		try{
			id = schedulingService.saveOrUpdateScheduling(importScheduler);
			code = "200";
		}catch(Exception e){
			code = "500";
		}
		model.addAttribute("code", code);
		return "redirect:/schedular";
	}
	/**
	 * This method will add a new schedular information to the database
	 * @param model
	 * @param importType
	 * @return
	 */
	@RequestMapping(value="/addeditschedular" , method=RequestMethod.GET)
	public String addeditschedular(Model model, @RequestParam("importType") String importType){
		LOG.info("import type in addeditschedular page: "+importType);
		SchedulerDTO schedulerDTO = null;
		ImportScheduler scheduling = schedulingService.findOneByType(importType);
		if(scheduling==null){
			schedulerDTO = new SchedulerDTO();
			schedulerDTO.setType(importType);
		}else{
			schedulerDTO = schedulingService.classToDTO(scheduling);
		}
		
		model.addAttribute("schedulerDTO", schedulerDTO);
		model.addAttribute("recurringList", AuthBridgeEnum.recurringAsList());
		return "addeditschedular";
	}
	/**
	 * To delete an existing schedular
	 * @param model
	 * @param importType
	 * @return
	 */
	@RequestMapping(value="/deleteSchedular" , method=RequestMethod.GET)
	public String deleteSchedular(Model model, @RequestParam("importType") String importType){
		LOG.info("deleting the import type : "+importType);
		schedulingService.removeScheduling(importType);
		return "redirect:/schedular";
	}
	/**
	 * To execute full import
	 * @return
	 */
	@RequestMapping(value="/executeFullImport" , method = RequestMethod.GET)
	public String executeFullImport(){
		LOG.info("Running the full import  : ");
		LOG.info("Full import scheduled.");
		scheduler.scheduleFullImport();
		return "redirect:/schedular";
	}
	/**
	 * To execute delta import
	 * @return
	 */
	@RequestMapping(value="/executeDeltaImport" , method = RequestMethod.GET)
	public String executeDeltaImport(){
		LOG.info("Running the delta import : ");
		LOG.info("Delta import scheduled.");
		scheduler.scheduleDeltaImport();
		return "redirect:/schedular";
	}
}
