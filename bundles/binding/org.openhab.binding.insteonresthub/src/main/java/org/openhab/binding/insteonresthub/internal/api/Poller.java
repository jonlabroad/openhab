package org.openhab.binding.insteonresthub.internal.api;

import java.util.concurrent.ScheduledExecutorService;

public abstract class Poller implements Runnable {
	protected ScheduledExecutorService _futureScheduler;
	
	public void SetScheduler(ScheduledExecutorService service)
	{
		_futureScheduler = service;
	}
}
