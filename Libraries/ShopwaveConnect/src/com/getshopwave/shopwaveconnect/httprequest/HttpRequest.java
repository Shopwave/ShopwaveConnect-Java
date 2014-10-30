package com.getshopwave.shopwaveconnect.httprequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Makes HTTP GET and POST requests
 * 
 * @author yasir.mahmood
 *
 */
public class HttpRequest 
{
	private HttpRequestDelegate delegate;
	
	/**
	 * @param delegate - Requires a delegate to be setup to handle the response.
	 */
	public void setDelegate(HttpRequestDelegate delegate)
	{
		this.delegate = delegate;
	}
	
	/**
	 * When no delegate set , return response as inputstream
	 * @param url - required (Url to make request to)
	 */
	public InputStream getInputStream(URL url, HashMap<String, String> headers)
	{
		InputStream in = null;
		
		try
		{
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			if(headers != null)
			{
				for(String key: headers.keySet())
				{
					urlConnection.setRequestProperty(key, headers.get(key));
				}
			}
			
			try 
			{
				in = new BufferedInputStream(urlConnection.getInputStream());
				return in;
			} 
			finally 
			{
				urlConnection.disconnect();
			}	
			
		}
		catch(Exception e)
		{
			
		}
		
		return in;
	}
	
	/**
	 * get data from web
	 * @param url
	 * @param headers
	 * @return String object
	 */
	public String getDataFromWeb(URL url, HashMap<String, String> headers)
	{
		InputStream stream = get(url, headers);
		
		if(stream != null)
		{
			return readStream(stream);
		}
		return null;
	}

    public InputStream get(URL url, HashMap<String, String> headers) 
    {
		String result = null;
		InputStream in = null;
		HttpURLConnection urlConnection = null;
		
		if(this.delegate!=null)
		{
			try
			{
				urlConnection = (HttpURLConnection) url.openConnection();
				
				if(headers != null)
				{
					for(String key: headers.keySet())
					{
						urlConnection.setRequestProperty(key, headers.get(key));
					}
				}
				
				try 
				{
					in = new BufferedInputStream(urlConnection.getInputStream());
					result = readStream(in);
				} 
				finally 
				{
					urlConnection.disconnect();
					this.delegate.onCompleteHttpRequest(result);
				}
				
			}
			catch(Exception e)
			{
				try 
				{
					com.getshopwave.shopwaveconnect.model.Error error = new com.getshopwave.shopwaveconnect.model.Error();
					error.setStatusCode(urlConnection.getResponseCode());
					this.delegate.onFailHttpRequest(error);
				} 
				catch (IOException e1) 
				{
					this.delegate.onFailHttpRequest(null);
				}
			}
		}
		else
		{
			return getInputStream(url, headers);
		}
		
		return null;
	}

	public String post(URL url, HashMap<String, String> params, HashMap<String, String> headers) 
	{
		String result = null;
		
		try
		{
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			if(headers != null)
			{
				for(String key: headers.keySet())
				{
					urlConnection.setRequestProperty(key, headers.get(key));
				}
			}
			
			try 
			{
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.setChunkedStreamingMode(0);
	
				DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
				
				if(params!=null)
				{
					writeStream(out, getParseUrlEncodeParams(params));
				}
	
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				result = readStream(in);
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
			finally 
			{
				urlConnection.disconnect();
				
				if(this.delegate != null)
				{
					this.delegate.onCompleteHttpRequest(result);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			
			if(this.delegate != null)
			{
				this.delegate.onFailHttpRequest(null);
			}
		}
		return result;
	}
	
	private String readStream(InputStream in) 
	{
		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		
		try 
		{
			reader = new BufferedReader(new InputStreamReader(in));

			String line = "";

			while ((line = reader.readLine()) != null) 
			{
				result.append(line);
				if(this.delegate != null)
				{
					this.delegate.onProgressHttpRequest(0);
				}
			}

		} 
		catch (IOException e) 
		{
			System.out.println(e.toString());
		}
		catch(Exception exception)
		{
			
		}
		finally 
		{
			if (reader != null) 
			{
				try 
				{
					reader.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		if(result!=null)
		{
			return result.toString();
		}
		else
		{
			return null;
		}
	}
	
	private void writeStream(DataOutputStream output, String contents) 
	{
		try 
		{
			output.writeBytes(contents);
			output.flush();
			output.close();
		} 
		catch (IOException ex) 
		{

		}
	}
	
	private String getParseUrlEncodeParams(HashMap<String, String> params)
	{
		StringBuilder postParams = new StringBuilder();
		String key, value = "", result = "";
		
		for (Map.Entry<String, String> entry : params.entrySet()) 
		{
		    key = entry.getKey();
		    
		    try 
		    {
				value =  URLEncoder.encode((String) entry.getValue(), "UTF-8");
			} 
		    catch (UnsupportedEncodingException e) 
		    {
				e.printStackTrace();
			}
		    
		    postParams.append(key+"="+value+"&");
		}
		result = postParams.toString();
		result = result.substring(0, result.length() - 1);
		return result;
	}
}
