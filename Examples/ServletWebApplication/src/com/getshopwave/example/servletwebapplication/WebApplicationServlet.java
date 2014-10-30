package com.getshopwave.example.servletwebapplication;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.getshopwave.example.servletwebapplication.model.User;
import com.getshopwave.example.servletwebapplication.model.UserGet;
import com.getshopwave.example.servletwebapplication.parser.UserGetParser;
import com.getshopwave.shopwaveconnect.ShopwaveConnectManager;
import com.getshopwave.shopwaveconnect.httprequest.HttpRequestDelegate;
import com.getshopwave.shopwaveconnect.model.Token;


public class WebApplicationServlet extends HttpServlet 
{
	private static final long serialVersionUID = -1272028209228940855L;
	private RequestDispatcher requestDispatcher;
	private ShopwaveConnectManager connector;
	private Token token;
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		ServletContext context = config.getServletContext();
		this.requestDispatcher = context.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		String authCode = req.getParameter("code");
		
		boolean logout = Boolean.parseBoolean(req.getParameter("logout"));
		
		if(logout == true)
		{
			this.token = null;
			String uriParts = this.getDefaultConnector().getLogoutUrl(Config.redirectUri, Config.clientIdentifier, Config.scope);
			resp.sendRedirect(uriParts);
		}
		else
		{
			if(authCode != null && authCode.length()>0)
			{
				this.token = this.getDefaultConnector().makeTokenCall(Config.redirectUri, Config.clientIdentifier, Config.clientSecret, Config.scope, authCode, null);
			}
			
			if(this.token != null)
			{
				User user = this.getUser(this.token, null, null);
				
				if(user != null)
				{
					req.setAttribute("firstName", user.getFirstName());
					req.setAttribute("lastName", user.getLastName());
					req.setAttribute("merchantId", user.getEmployee().getMerchantId());
				}
			}
	
			req.setAttribute("isLoggedIn", (this.token == null) ? false : true);
			req.setAttribute("connectionUrl", this.getConnectionString());
			
	        try 
	        {
				this.requestDispatcher.forward(req, resp);
			} 
	        catch (ServletException e) 
	        {
				e.printStackTrace();
			}
		}
	}
	
	public String getConnectionString()
	{
		return this.getDefaultConnector().getAuthoriseApplicationUri(Config.redirectUri, Config.clientIdentifier, Config.scope);
	}
	
	public ShopwaveConnectManager getDefaultConnector()
	{
		return this.connector = (this.connector == null) ? new ShopwaveConnectManager() : this.connector;
	}
	
    public User getUser(Token token, HashMap<String, String> params, HttpRequestDelegate callback)
    {
    	HashMap<String, String> headers = new HashMap<String, String>();
    	
    	String userJSONObject = this.getDefaultConnector().makeShopwaveApiCall("/user", token, "GET", params, headers, callback);
    	UserGetParser parser = new UserGetParser();
    	UserGet userRootNode=  parser.getParsedData(userJSONObject);
    	
    	if(userRootNode != null)
    	{
    		return userRootNode.getUser();
    		
    	}
    	return null;
    }
}
