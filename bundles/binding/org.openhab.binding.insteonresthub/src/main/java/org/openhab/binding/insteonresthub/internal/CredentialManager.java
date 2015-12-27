package org.openhab.binding.insteonresthub.internal;

public class CredentialManager {

	private String _username = "";
	private String _password = "";
	
	private static CredentialManager _instance = null;
	
	private CredentialManager() {};
	
	public static CredentialManager getInstance()
	{
		if (_instance == null)
		{
			_instance = new CredentialManager();
		}
		return _instance;
	}
	
	public void setUsernamePassword(String username, String password)
	{
		_username = username;
		_password = password;
	}
	
	public String getUsername()
	{
		return _username;
	}
	
	public String getPassword()
	{
		return _password;
	}
}
