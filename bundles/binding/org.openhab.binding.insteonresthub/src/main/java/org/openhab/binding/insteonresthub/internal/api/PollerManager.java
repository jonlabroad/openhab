package org.openhab.binding.insteonresthub.internal.api;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PollerManager {
	private static final Logger logger = LoggerFactory.getLogger(PollerManager.class);
	
	public PollerManager() 
	{
	}
	
	public void AddRepeatingPoller(String name, Class<?> jobClass, int repeatTimeSec)
	{
		Scheduler scheduler = GetScheduler();
		
		JobDataMap map = new JobDataMap();
		//map.put("binding", jobInstance);

		JobDetail job = newJob(TokenRefresher.class)
				.withIdentity(name, "InsteonRestApi")
				.usingJobData(map)
				.build();

		Trigger trigger = newTrigger()
				.withIdentity(name, "InsteonRestApi")
				.withSchedule(simpleSchedule()
						.repeatForever()
						.withIntervalInSeconds(repeatTimeSec)) //TODO configurable and changing based on expiration received from API            
						.build();

		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("An exception occurred while scheduling a Quartz Job");
		}
	}
	
	private Scheduler GetScheduler()
	{
		Scheduler sched = null;
		try {
			sched =  StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			logger.error("An exception occurred while getting a reference to the Quartz Scheduler");
		}
		return sched;
	}
	
	public void StopAllPollers()
	{
	}
	
	public void DestroyAllPollers()
	{
	}
}
