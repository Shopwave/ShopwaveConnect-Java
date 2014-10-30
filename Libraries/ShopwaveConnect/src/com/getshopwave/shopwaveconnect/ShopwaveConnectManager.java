package com.getshopwave.shopwaveconnect;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.getshopwave.shopwaveconnect.httprequest.AsyncTaskExecutor;
import com.getshopwave.shopwaveconnect.httprequest.HttpRequest;
import com.getshopwave.shopwaveconnect.httprequest.HttpRequestDelegate;
import com.getshopwave.shopwaveconnect.model.Token;
import com.getshopwave.shopwaveconnect.parser.TokenParser;

public final class ShopwaveConnectManager 
{
    private static AsyncTaskExecutor asyncTaskExecutor;
    
    private final static String accessType = "online";
    private final static String responseTypeCode = "code";
    private final static String oAuthBaseUrl = "http://secure.merchantstack.com";
    private final static String apiBaseUrl = "http://api.merchantstack.com";
    private final static String authUri = "/oauth/authorize";
    private final static String tokenUri = "/oauth/token";
    private final static String logoutUri = "/logout";
    private final static String grantTypeAuthoricationCode = "authorization_code";
    private final static String grantTypeRefreshToken = "refresh_token";
    
    public ShopwaveConnectManager()
    {
    	ShopwaveConnectManager.asyncTaskExecutor = new AsyncTaskExecutor();
    }

    public String getAuthoriseApplicationUri(String redirectUrl,  String clientId, String scope)
    {	
        return getConnectionUrl(redirectUrl, ShopwaveConnectManager.accessType, ShopwaveConnectManager.responseTypeCode, clientId, scope);
    }

    public Token makeTokenCall(String redirectUrl, String clientId, String clientSecret, String scope, String code, HttpRequestDelegate responseDelegate)
    {
        return getRefreshToken(ShopwaveConnectManager.grantTypeAuthoricationCode, redirectUrl, clientId, clientSecret, scope, code, null, responseDelegate);
    }
    
    public Token refreshTokenCall(String redirectUrl, String clientId, String clientSecret, String scope, String refreshToken, HttpRequestDelegate responseDelegate)
    {
    	return getRefreshToken(ShopwaveConnectManager.grantTypeRefreshToken, redirectUrl, clientId, clientSecret, scope, null, refreshToken, responseDelegate);
    }
    
    public String makeShopwaveApiCall(String endpoint, Token tokens, String method, HashMap<String, String> params, HashMap<String, String> headers, HttpRequestDelegate callback)
    {
    	HashMap<String, String> defaultHeaders = new HashMap<String, String>();
    	defaultHeaders.put("accept", "application/json");
    	
    	if(tokens != null)
    	{
    		defaultHeaders.put("Authorization", "OAuth "+tokens.getAccess_token());
    	}
    	
    	if(headers != null)
    	{
    		for (String key : headers.keySet()) 
    		{
    			defaultHeaders.put(key, headers.get(key));
    		}
    	}
    	
    	method = (method == null) ? "GET" : method; 
    	endpoint = getApiEndpoint(endpoint);
    	
    	return makeWebQuery(endpoint, method, params, defaultHeaders, callback);
    }
    
    public String getLogoutUrl(String redirectUrl, String clientId, String scope)
    {
    	String uriParts = ShopwaveConnectManager.oAuthBaseUrl + ShopwaveConnectManager.logoutUri + "?access_type=" + ShopwaveConnectManager.accessType
                + "&redirect_uri=" + redirectUrl
                + "&response_type=" + ShopwaveConnectManager.responseTypeCode
                + "&client_id=" + clientId
                + "&scope=" + scope;
    	
    	return uriParts;
    }

    private String getConnectionUrl(String redirectUrl, String accessType, String responseType, String clientId, String scope)
    {
        String uriParts = "?access_type=" + accessType
                + "&redirect_uri=" + redirectUrl
                + "&response_type=" + responseType
                + "&client_id=" + clientId
                + "&scope=" + scope;

        return ShopwaveConnectManager.oAuthBaseUrl + ShopwaveConnectManager.authUri + uriParts;
    }
    
    private String getApiEndpoint(String endpoint)
    {
    	return ShopwaveConnectManager.apiBaseUrl + endpoint;
    }

    private Token getRefreshToken(String grantType, String redirectUrl,  String clientId, String clientSecret, String scope, String code, String refreshToken, HttpRequestDelegate callback)
    {
        HashMap<String, String> defaultParams = new HashMap<String, String>();
        defaultParams.put("redirect_uri", redirectUrl);
        defaultParams.put("client_id", clientId);
        defaultParams.put("client_secret", clientSecret);
        defaultParams.put("grant_type", grantType);
        
        if(grantType == ShopwaveConnectManager.grantTypeAuthoricationCode)
        {
        	defaultParams.put("code", code);
        }
        else
        {
        	defaultParams.put("refresh_token", refreshToken);
        }
        
        String method = "POST";
        String results = makeWebQuery(ShopwaveConnectManager.oAuthBaseUrl+ShopwaveConnectManager.tokenUri, method, defaultParams, null, null);
        
        TokenParser parser = new TokenParser();
        
        return parser.getParsedData(results);
    }
    
    private String makeWebQuery(String requestUrl, String method, HashMap<String, String> params, HashMap<String, String> headers, HttpRequestDelegate callback)
    {
    	if(callback != null)
    	{
        	makeWebQuery(requestUrl, method, params, headers, callback);
        }
        else
        {
        	HttpRequest httpRequest = new HttpRequest();
        	try 
    		{
        		if(method == "GET")
        		{
        			return httpRequest.getDataFromWeb(new URL(requestUrl), headers);
        		}
        		else
        		{
        			String results = httpRequest.post(new URL(requestUrl), params, headers);
        			return results;
        		}
    		} 
    		catch (MalformedURLException e) 
    		{
    			e.printStackTrace();
    		}
        	
        }
    	
    	ShopwaveConnectManager.asyncTaskExecutor.requestWebContents(requestUrl, method, params, headers, callback);
    	
    	return null;
    }
}
