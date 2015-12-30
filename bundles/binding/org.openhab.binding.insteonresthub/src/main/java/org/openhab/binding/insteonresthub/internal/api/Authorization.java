package org.openhab.binding.insteonresthub.internal.api;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.apache.commons.httpclient.methods.PostMethod;

public class Authorization {
	private final static String AUTH_ENDPOINT = "oauth2/token";
	
	//username	string	The username of the Insteon account
	private final static String USERNAME_PARAM = "username";
	
	//password	string	The password of the Insteon account
	private final static String PASSWORD_PARAM = "password";
	
	//client_id	string	Valid API Key
	private final static String API_KEY_PARAM = "client_id";
	
	//grant_type
	private final static String GRANT_TYPE_PARAM = "grant_type";
	private final static String GRANT_TYPE_PASSWORD = "password";
	private final static String GRANT_TYPE_REFRESH = "refresh_token";
	
	private final static String REFRESH_TOKEN_PARAM = "refresh_token";
	
	private static Token _currentToken = null;
	
	public static void addAuthHeaders(PostMethod method)
	{
		method.addRequestHeader("Authentication", "APIKey " + InsteonApiConstants.Instance().API_KEY);
		method.addRequestHeader("Authorization", _currentToken.TokenType + " " + _currentToken.Token);
	}
	
	public static Token getNewToken(String username, String password)
	{
		Map<String, String> params = setupApiKeyParams(username, password);
		PostMethod postMethod = RestClient.ExecutePost(
				InsteonApiConstants.Instance().BASE_URL,
				AUTH_ENDPOINT,
				params, null, false);
		Token token = parseTokenFromResponse(postMethod);
		_currentToken = token;
		return token;
	}
	
	public static Token refreshToken(Token oldToken)
	{
		Map<String, String> params = setupKeyRefreshParams(oldToken);
		PostMethod postMethod = RestClient.ExecutePost(
				InsteonApiConstants.Instance().BASE_URL,
				AUTH_ENDPOINT,
				params, null, false);
		Token token = parseTokenFromResponse(postMethod);
		_currentToken = token;
		return token;
	}
	
	public static Token getToken()
	{
		return _currentToken;
	}
	
	private static Map<String, String> setupApiKeyParams(String username, String password)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(USERNAME_PARAM, username);
		params.put(PASSWORD_PARAM, password);
		params.put(API_KEY_PARAM, InsteonApiConstants.Instance().API_KEY);
		params.put(GRANT_TYPE_PARAM, GRANT_TYPE_PASSWORD);
		return params;
	}
	
	private static Map<String, String> setupKeyRefreshParams(Token token)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(REFRESH_TOKEN_PARAM, token.RefreshToken);
		params.put(API_KEY_PARAM, InsteonApiConstants.Instance().API_KEY);
		params.put(GRANT_TYPE_PARAM, GRANT_TYPE_REFRESH);
		return params;
	}
	
	private static Token parseTokenFromResponse(PostMethod postMethod)
	{
		JSONObject jObject = null;
		try {
			jObject = (JSONObject) JSONValue.parse(postMethod.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Token retToken = new Token();
		retToken.Token = (String) jObject.get("access_token");
		retToken.RefreshToken = (String)jObject.get(REFRESH_TOKEN_PARAM);
		retToken.TokenType = (String) jObject.get("token_type");
		long expiresInSeconds = (Long) jObject.get("expires_in");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, (int)expiresInSeconds);
		retToken.Expiration = cal.getTime();
		return retToken;
	}
	
	public static void main(String argv[])
	{
		Token token = Authorization.getNewToken(argv[0], argv[1]);
		System.out.println(token.Token);
		System.out.println(token.Expiration.toString());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		token = Authorization.refreshToken(token);
		System.out.println(token.Token);
		System.out.println(token.Expiration.toString());
		
	}
}
