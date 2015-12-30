package org.openhab.binding.insteonresthub.internal.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.simple.JSONObject;

public final class RestClient {
	public static GetMethod ExecuteGet(String baseUrl, String path,
									  Map<String, String> urlParameters, Map<String, String> bodyParameters)
	{
		HttpClient client = new HttpClient();
		
		GetMethod method = new GetMethod(baseUrl + "/" + path);
		method.getParams().setContentCharset("UTF-8");
		method.getParams().setSoTimeout(10000); //todo parameter
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		NameValuePair[] urlParams = new NameValuePair[urlParameters.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : urlParameters.entrySet())
		{
			urlParams[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		method.setQueryString(urlParams);
		
		//TODO implement the rest when I actually need it
		
		return null;
	}
	
	public static PostMethod ExecutePost(String baseUrl, String path,
										 Map<String, String> urlParameters, JSONObject bodyJson,
										 boolean auth)
	{
		HttpClient client = new HttpClient();

		PostMethod postMethod = new PostMethod(baseUrl + "/" + path);
		if (auth)
		{
			Authorization.addAuthHeaders(postMethod);
			postMethod.addRequestHeader("Content-Type", "application/json");
		}
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setSoTimeout(10000); //todo parameter
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		if (urlParameters != null)
		{
			for (Map.Entry<String, String> entry : urlParameters.entrySet())
			{
				postMethod.addParameter(new NameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		if (bodyJson != null)
		{	
/*
			for (Map.Entry<String, String> entry : bodyParameters.entrySet())
			{
				try {
					int numVal = Integer.parseInt(entry.getValue());
					jsonObj.put(entry.getKey(), numVal);
				}
				catch (Exception e) {
					jsonObj.put(entry.getKey(), entry.getValue());
				}
			}
*/			
			StringRequestEntity requestEntity = null;
			try {
				requestEntity = new StringRequestEntity(
						bodyJson.toJSONString(),
					    "application/json",
					    "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			postMethod.setRequestEntity(requestEntity);
		}
		int statusCode = ExecuteMethod(client, postMethod);
		
		return postMethod;
	}
	
	private static int ExecuteMethod(HttpClient client, HttpMethod method)
	{
		int statusCode = -1;
		try {
			statusCode = client.executeMethod(method);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return statusCode;
	}
}
