package com.getshopwave.shopwaveconnect.parser;

import com.getshopwave.shopwaveconnect.model.Token;

public class TokenParser extends Parser<Token> 
{
	@Override
	public Token getParsedData(String jsonTokenData) 
	{
		try
        {
            return super.mapper.readValue(jsonTokenData, Token.class);
        }
        catch(Exception e)
        {
            return null;
        }
	}
}
