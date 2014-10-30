package com.getshopwave.example.servletwebapplication.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getshopwave.example.servletwebapplication.model.UserGet;

public class UserGetParser 
{
	public ObjectMapper mapper;

    public UserGetParser()
    {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

	public UserGet getParsedData(String jsonData) 
	{
		try
        {
            return mapper.readValue(jsonData, UserGet.class);
        }
        catch(Exception e)
        {
            return null;
        }
	}
}