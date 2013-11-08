package org.mbaum.common.net.parse;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

class JsonResponseParser<R> implements ResponseParser<R>
{
	private static final Logger LOGGER = Logger.getLogger( JsonResponseParser.class );
	private final Class<R> mResultClass;
	
	JsonResponseParser( Class<R> resultClass )
	{
		mResultClass = resultClass;
	}
	
	@Override
	public R parseResponse( HttpResponse response ) throws ResponseParseException
	{
		InputStream responseStream;
		try
		{
			responseStream = response.getEntity().getContent();
			
			String responseString = IOUtils.toString( responseStream );
			LOGGER.info( "parsing response: " + responseString );
			
			ObjectMapper mapper = new ObjectMapper();
			
			return mapper.readValue( responseString, mResultClass );
		}
		catch ( IllegalStateException e )
		{
			throw new ResponseParseException( "Could not get response content", e );
		}
		catch ( IOException e )
		{
			throw new ResponseParseException( "Failed to read response content", e );
		}
	}
}