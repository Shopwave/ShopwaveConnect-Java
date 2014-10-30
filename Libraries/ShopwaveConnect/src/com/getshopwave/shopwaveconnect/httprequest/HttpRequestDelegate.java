package com.getshopwave.shopwaveconnect.httprequest;

public interface HttpRequestDelegate 
{
	public void onCompleteHttpRequest(String result);
	public void onFailHttpRequest(com.getshopwave.shopwaveconnect.model.Error e);
	public void onProgressHttpRequest(int progress);
}
