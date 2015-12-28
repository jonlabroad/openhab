package org.openhab.binding.insteonresthub.internal.api;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openhab.binding.insteonresthub.internal.InsteonRestApi;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TokenRefresher implements Job {

	public void run() {
		Token newToken = Authorization.refreshToken(Authorization.getToken());
		//long sleepTimeSec = (newToken.Expiration.getTime() - Calendar.getInstance().getTime().getTime())/1000;
		//_futureScheduler.schedule(this, sleepTimeSec, TimeUnit.SECONDS);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Token newToken = Authorization.refreshToken(Authorization.getToken());	
		System.out.println("New token: " + newToken.Token);
	}

}
