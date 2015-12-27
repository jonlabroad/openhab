package org.openhab.binding.insteonresthub.internal.api;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public final class RestClient {
	public static PostMethod ExecutePost(String baseUrl, String path, Map<String, String> parameters)
	{
		HttpClient client = new HttpClient();

		PostMethod postMethod = new PostMethod(baseUrl + "/" + path);
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setSoTimeout(10000); //todo parameter
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			postMethod.addParameter(new NameValuePair(entry.getKey(), entry.getValue()));
		}
		ExecuteMethod(client, postMethod);
		
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
