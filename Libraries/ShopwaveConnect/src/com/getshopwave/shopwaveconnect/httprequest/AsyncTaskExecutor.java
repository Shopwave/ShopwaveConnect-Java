package com.getshopwave.shopwaveconnect.httprequest;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to make an async HTTP Request, which should be called as a single instance.
 * 
 * @author yasir.mahmood
 */
public class AsyncTaskExecutor 
{
	private ExecutorService taskExecutor;
	private HttpRequestRunnable runnable;
	
	public AsyncTaskExecutor()
	{
		this.taskExecutor = Executors.newCachedThreadPool();
	}
	
	/**
	 * 
	 * @param task - Accepts a Runnable task or just call requestWebContents with a url to make an async Request.
	 */
	public void addTask(Runnable task)
	{
		this.taskExecutor.execute(task);
	}
	
	/**
	 * Web Contents will be provided in a callback function passed as a delegate.
	 * @param urlString
	 * @param requestType
	 * @param params
	 */
	public void requestWebContents(String urlString, String requestType, HashMap<String, String> params, HashMap<String, String> headers, HttpRequestDelegate responseDelegate)
	{
		this.runnable = new HttpRequestRunnable(urlString, requestType, responseDelegate);
		
		if(params != null)
		{
			this.runnable.setPostParams(params);
		}
		
		if(headers != null)
		{
			this.runnable.setHeaders(headers);
		}
		
		this.taskExecutor.execute(this.runnable);
	}
}
