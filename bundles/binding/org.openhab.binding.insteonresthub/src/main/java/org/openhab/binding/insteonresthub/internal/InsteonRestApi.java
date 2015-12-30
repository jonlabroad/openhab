package org.openhab.binding.insteonresthub.internal;

import java.io.IOException;

import org.openhab.binding.insteonresthub.internal.InsteonRestHubGenericBindingProvider.InsteonRestHubBindingConfig;
import org.openhab.binding.insteonresthub.internal.api.Authorization;
import org.openhab.binding.insteonresthub.internal.api.Command;
import org.openhab.binding.insteonresthub.internal.api.PollerManager;
import org.openhab.binding.insteonresthub.internal.api.TokenRefresher;
import org.openhab.core.library.types.OnOffType;

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
		
		//TESTING
		//Command.SendDeviceCommand("on", 732870, 100);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Command.SendDeviceCommand("off", 732870, 0);
	}
	
	public void receiveCommand(InsteonRestHubGenericBindingProvider provider, 
							   String itemName, org.openhab.core.types.Command command)
	{
		if (command instanceof OnOffType)
		{
			handleLightOnOffCommand(provider, itemName, command);
		}
		
	}
	
	public void handleLightOnOffCommand(InsteonRestHubGenericBindingProvider provider, String itemName, org.openhab.core.types.Command command)
	{
		String testId = "732870"; //TODO use real id!
		InsteonRestHubBindingConfig config = provider.getInsteonRestHubBindingConfig(itemName);
		if (command == OnOffType.ON) {
			Command.SendDeviceCommand("ON", Integer.parseInt(testId), 100);
			//logger.info("{}: sent msg to switch {} to {}", nm(), dev.getAddress(),
			//		level == 0xff ? "on" : level);
		} else if (command == OnOffType.OFF) {
			Command.SendDeviceCommand("OFF", Integer.parseInt(testId), 0);
			//logger.info("{}: sent msg to switch {} off", nm(), dev.getAddress());
		}
	}
	
	private void createTokenRefresher()
	{
		_poller.AddRepeatingPoller("TokenRefresher", TokenRefresher.class, defaultRefreshIntervalSec);
	}
}
