package org.openhab.binding.insteonresthub.internal.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.json.simple.JSONObject;

//TODO break this up into different types of commands (device vs scene)
public class Command {
	private static String COMMAND_ENDPOINT = "/commands";
	
	private static String COMMAND_PARAM = "command";
	private static String LEVEL_PARAM = "level";
	private static String DEVICE_ID_PARAM = "device_id";
	
	public static PostMethod SendDeviceCommand(String command, int deviceId, int level)
	{
		JSONObject bodyParams = setupDeviceCommandBodyParams(command, deviceId, level);
		PostMethod postMethod = RestClient.ExecutePost(
				InsteonApiConstants.Instance().BASE_URL,
				COMMAND_ENDPOINT,
				null, bodyParams, true);
		return postMethod;
	}
	
	private static JSONObject setupDeviceCommandBodyParams(String command, int deviceId, int level)
	{
		JSONObject params = new JSONObject();
		params.put(COMMAND_PARAM, command);
		params.put(LEVEL_PARAM, level);
		params.put(DEVICE_ID_PARAM, deviceId);
		return params;
	}
}
