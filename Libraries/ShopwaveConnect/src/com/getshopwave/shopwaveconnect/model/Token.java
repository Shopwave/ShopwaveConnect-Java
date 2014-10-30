package com.getshopwave.shopwaveconnect.model;


/**
 * Created by yasirmahmood on 26/09/2014.
 */
public class Token 
{
    private String authCode;
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String token_type;
    
	public String getAuthCode() 
	{
		return authCode;
	}
	
	public void setAuthCode(String authCode) 
	{
		this.authCode = authCode;
	}
	
	public String getAccess_token() 
	{
		return access_token;
	}
	
	public void setAccess_token(String access_token) 
	{
		this.access_token = access_token;
	}
	
	public String getRefresh_token() 
	{
		return refresh_token;
	}
	
	public void setRefresh_token(String refresh_token) 
	{
		this.refresh_token = refresh_token;
	}
	
	public String getExpires_in() 
	{
		return expires_in;
	}
	
	public void setExpires_in(String expires_in) 
	{
		this.expires_in = expires_in;
	}
	
	public String getToken_type() 
	{
		return token_type;
	}
	
	public void setToken_type(String token_type) 
	{
		this.token_type = token_type;
	}
}


