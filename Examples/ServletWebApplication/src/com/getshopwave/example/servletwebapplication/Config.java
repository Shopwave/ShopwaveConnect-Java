package com.getshopwave.example.servletwebapplication;

public class Config 
{
	/* Your Shopwave ClientIdentifier (e.g. js7woa9ro028djsnakf778sn3wiam3ond274knao) */
	public final static String redirectUri = "SHOPWAVE_REDIRECT_URI";
	
	/* Your Shopwave ClientSecret (e.g. 76h4389732ru2039r20ruju023r9u2309jk8sna0) */
	public final static String clientIdentifier = "SHOPWAVE_CLIENT_IDENTIFIER";
	
	/* Your Shopwave RedirectUri (e.g. http://my.app) */
    public final static String clientSecret = "SHOPWAVE_CLIENT_SECRET";
    
    /* Your Shopwave Scope */
	public final static String scope = "user,application,merchant,store,product,category,basket,promotion"; 
}