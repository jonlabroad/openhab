package org.openhab.binding.insteonresthub.internal.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class PollerManager {
	private static final int NUM_THREADS = 3;
	
	private Map<String, ScheduledFuture<?>> _futureMap = null;
	private ScheduledExecutorService _futureScheduler = null; 
	public PollerManager() 
	{
		_futureScheduler = Executors.newScheduledThreadPool(NUM_THREADS);
		_futureMap = new HashMap<String, ScheduledFuture<?>>();
	}
	
	public void AddPoller(String name, Runnable task)
	{
		//_futureMap.put(name, _futureScheduler.schedule(task, arg1, arg2));
	}
	
	public void StopAllPollers()
	{
		for (String key : _futureMap.keySet())
		{
			_futureMap.get(key).cancel(true);
		}
	}
	
	public void DestroyAllPollers()
	{
		for (String key : _futureMap.keySet())
		{
			_futureMap.remove(key).cancel(true);
		}
	}
}
