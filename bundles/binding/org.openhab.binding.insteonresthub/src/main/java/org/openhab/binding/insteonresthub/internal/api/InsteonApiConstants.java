package org.openhab.binding.insteonresthub.internal.api;

public class InsteonApiConstants {
	public String BASE_URL = "https://connect.insteon.com/api/v2/";
	public String API_KEY = "29ea7d23-049e-48e9-89c2-4465cce4f7771448865927.098675851";
	
	private static InsteonApiConstants _instance = null;
	
	private InsteonApiConstants() {	}
	
	public static InsteonApiConstants Instance()
	{
		if (_instance == null) {
			_instance = new InsteonApiConstants();
		}
		return _instance;
	}
}
