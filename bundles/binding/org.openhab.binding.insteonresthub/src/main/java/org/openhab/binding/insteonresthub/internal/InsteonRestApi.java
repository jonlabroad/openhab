package org.openhab.binding.insteonresthub.internal;

import org.openhab.binding.insteonresthub.internal.api.Authorization;
import org.openhab.binding.insteonresthub.internal.api.PollerManager;
import org.openhab.binding.insteonresthub.internal.api.TokenRefresher;

public class InsteonRestApi {
	private PollerManager _poller;
	
	private int defaultRefreshIntervalSec = 3600;
	
	public InsteonRestApi(String username, String password)
	{
		initialize(username, password);
	}
	
	public void initialize(String username, String password)
	{
		_poller = new PollerManager();
		Authorization.getNewToken(username, password);
		createTokenRefresher();
	}
	
	private void createTokenRefresher()
	{
		_poller.AddRepeatingPoller("TokenRefresher", TokenRefresher.class, defaultRefreshIntervalSec);
	}
}
