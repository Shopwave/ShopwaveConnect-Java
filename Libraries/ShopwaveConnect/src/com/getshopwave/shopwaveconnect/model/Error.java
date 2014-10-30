package com.getshopwave.shopwaveconnect.model;

import java.util.ArrayList;

public class Error 
{
	private String id;
	private int status;
	private String title;
	private String message;
	private ArrayList<Error> errors;

	public Error() 
	{

	}

	public Error(String id, int level, String title, String message) 
	{
		this.id = id;
		this.status = level;
		this.title = title;
		this.message = message;
	}

	public String getTitle() 
	{
		return this.title;
	}

	public String getMessage() 
	{
		return this.message;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}

	public String getId()
	{
		return this.id;
	}

	public int getStatusCode()
	{
		return this.status;
	}

	public ArrayList<Error> getErrors()
	{
		return this.errors;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setStatusCode(int level)
	{
		this.status = level;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setErrors(ArrayList<Error> errors)
	{
		this.errors = errors;
	}

}
