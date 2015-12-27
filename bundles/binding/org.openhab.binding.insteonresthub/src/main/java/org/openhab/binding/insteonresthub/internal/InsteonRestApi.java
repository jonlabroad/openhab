package org.openhab.binding.insteonresthub.internal;

import org.openhab.binding.insteonresthub.internal.api.PollerManager;

public class InsteonRestApi {
	private PollerManager _poller;
	
	public InsteonRestApi()
	{
		initialize();
	}
	
	public void initialize()
	{
		_poller = new PollerManager();
	}
	
	private void createTokenRefresher()
	{
		//_poller.AddPoller("TokenRefresher", new ScheduledFuture);
	}
}
