package com.getshopwave.shopwaveconnect.parser;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Parser<T> 
{
    public ObjectMapper mapper;

    public Parser()
    {
        this.mapper = new ObjectMapper(); // create once, reuse
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public abstract T getParsedData(String jsonTokenData);
}