package com.authbridge.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

import com.authbridge.DTO.SchedulingStatusInfoDTO;
import com.authbridge.DTO.SolrStautusDTO;
import com.authbridge.constant.AUTHBRIDGECONSTANT;
import com.authbridge.constant.AuthBridgeEnum;
import com.authbridge.model.ImportScheduler;
import com.authbridge.model.Modification;
import com.authbridge.repository.ImportSchedulerRepository;
import com.authbridge.service.ModificationService;
import com.authbridge.utility.Utility;


@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

	private static Logger LOG = org.slf4j.LoggerFactory
			.getLogger(SchedulingConfig.class);

	private int flag;
	
	@Autowired
	private Utility utility;
	
	@Value("${solr.collection}")
	private String collectionName;
	
	private Calendar checkSchedule = new GregorianCalendar();

	@Autowired
	private ImportSchedulerRepository importSchedulerRepository;
	
	@Autowired
	private ModificationService modificationService;

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@Bean
	public Scheduler schedule() {
		return new Scheduler();
	}
/**
 * This method is to configure the scheduling task.
 * It makes use of spring batch to schedule the tasks at the specified time.
 * Full and delta import can be scheduled accordingly. Based on the input and the 
 * time selected by the user, the scheduler will trigger the indexing process at that time.
 * It can be triggered on any time by daily, weekly or monthly basis
 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

		taskRegistrar.setScheduler(taskExecutor());
		
		Calendar next = new GregorianCalendar();

		List<TriggerTask> taskList = new ArrayList<TriggerTask>();

		/**
		 * Logic for triggering full/delta import with recurring time stamp
		 * 
		 */
		Long count = importSchedulerRepository.count();
		if (count != null && count != 0) {
			LOG.info("Scheduler table has some values.");
			ImportScheduler fullImportScheduler = importSchedulerRepository
					.findOneByType(AuthBridgeEnum.Type.Full_Import.toString());
			ImportScheduler deltaImportScheduler = importSchedulerRepository
					.findOneByType(AuthBridgeEnum.Type.Delta_Import.toString());

			if (flag == 1) {
				LOG.info("Setting up variables for Full Import. Flag is set to 1");

				TriggerTask fullImport = new TriggerTask(new Runnable() {

					@Override
					public void run() {
						LOG.info("Triggering full import");
						//if(scheduleCounter==1){
							LOG.info("All set for full import. It will be executed now.");
							/*if(next.getTime().compareTo(Calendar.getInstance().getTime())==0){
								System.out.println("same time.");
							}
							else{
								System.out.println("diff time..");
							}*/
							//modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, AUTHBRIDGECONSTANT.OPERATION.FULL_IMPORT,AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
							schedule().scheduleFullImport();
							LOG.info("Full import running.");
						/*}
						else{
							LOG.info("Expected execution time is yet to be scheduled.");
						}*/
						Calendar c = new GregorianCalendar();
						c.setTime(next.getTime());
						if (fullImportScheduler.getRecurring()
								.equalsIgnoreCase(
										AuthBridgeEnum.Recurring.Everyday
												.toString())) {
							c.add(Calendar.DATE, 1);
							fullImportScheduler.setDateTime(c.getTime());
							importSchedulerRepository.save(fullImportScheduler);
							LOG.info("Next execution date for everyday: "
									+ fullImportScheduler.getDateTime());
						} else if (fullImportScheduler.getRecurring()
								.equalsIgnoreCase(
										AuthBridgeEnum.Recurring.Weekly
												.toString())) {
							c.add(Calendar.DATE, 7);
							fullImportScheduler.setDateTime(c.getTime());
							importSchedulerRepository.save(fullImportScheduler);
							LOG.info("Next execution date for everyweek: "
									+ fullImportScheduler.getDateTime());

						} else if (fullImportScheduler.getRecurring()
								.equalsIgnoreCase(
										AuthBridgeEnum.Recurring.Monthly
												.toString())) {
							c.add(Calendar.MONTH, 1);
							fullImportScheduler.setDateTime(c.getTime());
							importSchedulerRepository.save(fullImportScheduler);
							LOG.info("Next execution date for everymonth: "
									+ fullImportScheduler.getDateTime());

						}

					}
				}, new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						Calendar nextExecutionTime = new GregorianCalendar();

						nextExecutionTime.setTime(fullImportScheduler
								.getDateTime());
						next.setTime(nextExecutionTime.getTime());
						//setting the below parameter to see if scheduling runs properly
						//checkSchedule.setTime(nextExecutionTime.getTime());
						//scheduleCounter=1;
						LOG.info("Next execution date in trigger: "
								+ nextExecutionTime.getTime());
						return nextExecutionTime.getTime();
					}
				});

				taskList.add(fullImport);
			} else if (flag == 2) {
				LOG.info("Setting up variables for Delta Import. Flag is set to 2");

				TriggerTask deltaImport = new TriggerTask(new Runnable() {

					@Override
					public void run() {
						LOG.info("Triggering delta import!");
						//if(scheduleCounter==1){
							LOG.info("All set for delta import. It will be executed now.");
							schedule().scheduleDeltaImport();
							//modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, AUTHBRIDGECONSTANT.OPERATION.DELTA_IMPORT,AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
						//	LOG.info("Delta import successfull."+res);
						/*}
						else{
							LOG.info("Expected delta import is yet to be triggered.");
						}*/
						Calendar c = new GregorianCalendar();
						if (deltaImportScheduler.getRecurring()
								.equalsIgnoreCase(
										AuthBridgeEnum.Recurring.Everyday
												.toString())) {
							c.add(Calendar.DATE, 1);
							deltaImportScheduler.setDateTime(c.getTime());
							importSchedulerRepository.save(deltaImportScheduler);
							LOG.info("Next execution date: "
									+ deltaImportScheduler.getDateTime());
						} else if (deltaImportScheduler.getRecurring()
								.equalsIgnoreCase(
										AuthBridgeEnum.Recurring.Weekly
												.toString())) {
							c.add(Calendar.DATE, 7);
							deltaImportScheduler.setDateTime(c.getTime());
							importSchedulerRepository
									.save(deltaImportScheduler);
							LOG.info("Next execution date: "
									+ deltaImportScheduler.getDateTime());

						} else if (deltaImportScheduler.getRecurring()
								.equalsIgnoreCase(
										AuthBridgeEnum.Recurring.Monthly
												.toString())) {
							c.add(Calendar.MONTH, 1);
							deltaImportScheduler.setDateTime(c.getTime());
							importSchedulerRepository
									.save(deltaImportScheduler);
							LOG.info("Next execution date: "
									+ deltaImportScheduler.getDateTime());

						}
					}
				}, new Trigger() {

					@Override
					public Date nextExecutionTime(TriggerContext triggerContext) {
						Calendar nextExecutionTime = new GregorianCalendar();
						nextExecutionTime.setTime(deltaImportScheduler
								.getDateTime());
						//scheduleCounter=1;
						LOG.info("Next execution time: "
								+ nextExecutionTime.getTime());
						return nextExecutionTime.getTime();
					}
				});
				taskList.add(deltaImport);
			} else {
				LOG.info("Recurring is not opted by admin. Checking recurring condition for none.");
			}
			LOG.info("Setting import triggers to the trigger list..!");
			taskRegistrar.setTriggerTasksList(taskList);
			taskRegistrar.afterPropertiesSet();

			if (flag == 3) {
				/**
				 * Logic for triggering full/delta import only once
				 */

				if (fullImportScheduler.getRecurring().equalsIgnoreCase("none")) {

					ThreadPoolTaskScheduler fullImportWithoutRecurring = new ThreadPoolTaskScheduler();
					fullImportWithoutRecurring.initialize();
					fullImportWithoutRecurring.schedule(new Runnable() {

						@Override
						public void run() {
							LOG.info("Triggering full import. Recurring value is set to none.!");
						    schedule().scheduleFullImport();
							//modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, AUTHBRIDGECONSTANT.OPERATION.FULL_IMPORT,AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
							LOG.info("Full import running..!");
						}
					}, fullImportScheduler.getDateTime());
				}

			} else if (flag == 4) {
				if (deltaImportScheduler.getRecurring()
						.equalsIgnoreCase("none")) {
					ThreadPoolTaskScheduler deltaWithoutRecurring = new ThreadPoolTaskScheduler();
					deltaWithoutRecurring.initialize();
					deltaWithoutRecurring.schedule(new Runnable() {

						@Override
						public void run() {
							LOG.info("Triggering delta import. Recurring value is set to none.!");
							schedule().scheduleDeltaImport();
							//modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, AUTHBRIDGECONSTANT.OPERATION.DELTA_IMPORT,AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
							//LOG.info("Delta import run successfully..!"+res);
						}
					}, deltaImportScheduler.getDateTime());
				}
			} else {
				LOG.info("Admin has not selected recurring as none.");
			}
		} else {
			LOG.info("There are no values in the scheduler.");
		}
	}
	/**
	 * To get the status of solr when scheduler job is running. This method will check if the 
	 * server is still processing the request or it has become idle. If it is idle and the processing state is completed successfully 
	 * then the import is done successfully.
	 */
	@Scheduled(fixedRateString="${solr.schedulingStatusCheck}")
	public void getStatusOfSolr(){
		boolean status = modificationService.checkByInProgress();
			if(status){
				List<SolrStautusDTO> activeNodes = utility.getAllActiveNode();
				for(SolrStautusDTO solrStatusDTO : activeNodes){
					HttpSolrClient solr = new HttpSolrClient(solrStatusDTO.getBaseURl()+"/"+collectionName);
					SolrQuery query = new SolrQuery();
					query.setRequestHandler("/dataimport");
					query.setParam("command", "status");
					QueryResponse response;
					try {
						response = solr.query(query);
						LOG.info("Status message: "+response.getResponse().get("statusMessages"));
						LOG.info("Status message length: "+response.getResponse().get("statusMessages").toString().length());
						LOG.info("Status: "+response.getResponse().get("status"));
						String statusMessage = response.getResponse().get("statusMessages").toString();
						LOG.info("Import response: "+response.getResponse().get("importResponse"));
						
						if(response.getResponse().get("statusMessages").toString().length()>2){
							if(!(response.getResponse().get("status").toString().equalsIgnoreCase("busy"))){
								
							SchedulingStatusInfoDTO schedulingStatus = utility.schedulingStats(statusMessage);
							Modification modification = modificationService.findByStatus(AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.INPROGRESS);
							if(modification!=null){
							modificationService.saveOrUpdate(AUTHBRIDGECONSTANT.SCREEN.SCHEDULING, modification.getOperation(), 
																	Long.valueOf(schedulingStatus.getDocsProcessed()),
																			schedulingStatus.getTimeTaken(),
																				AUTHBRIDGECONSTANT.SCHEDULINGSTATUS.IDLE);
							}
							else{
								LOG.info("Modification table already updated with proper values.");
							}
							LOG.info("Status message with values: "+response.getResponse().get("statusMessages").toString());
							//break;LOG.info("Command is still running.");
							}
							else{
								LOG.info("Proceed.");
							}
						}
						else{
							LOG.info("Nothing in status message..");
						}
						//solr.close();
					} catch (SolrServerException | IOException e) {
						LOG.error("Error message when checking status:"+e.getLocalizedMessage());
						e.printStackTrace();
					} 
					finally{
						try {
							solr.close();
						} catch (IOException e) {
							LOG.error("Error message when checking status:"+e.getLocalizedMessage());
							e.printStackTrace();
						}
					}
							
			}
			LOG.info(" SCHEDULING in progress!!! getting response");
			}else{
			LOG.info("No SCHEDULING in progress!!!");
		}
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Calendar getCheckSchedule() {
		return checkSchedule;
	}

	public void setCheckSchedule(Calendar checkSchedule) {
		this.checkSchedule = checkSchedule;
	}

}