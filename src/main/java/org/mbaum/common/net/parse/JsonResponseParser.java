package org.mbaum.common.net.parse;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.mbaum.common.serialization.json.JsonDeserializer;
import org.mbaum.common.serialization.json.JsonDeserializers;
import org.mbaum.common.serialization.json.JsonException;

class JsonResponseParser<R> implements ResponseParser<R>
{
	private static final Logger LOGGER = Logger.getLogger( JsonResponseParser.class );
	
	private final JsonDeserializer<R> mDeserializer;
	
	JsonResponseParser( Class<R> resultClass )
    {
        this( JsonDeserializers.createDefault( resultClass ) );
    }

    JsonResponseParser( JsonDeserializer<R> deserializer )
	{
        mDeserializer = deserializer;
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
			
			return mDeserializer.deserialize( responseString );
		}
		catch ( IOException e )
		{
			throw new ResponseParseException( "Failed to read response content", e );
		}
        catch ( JsonException e )
        {
            throw new ResponseParseException( "Failed to deserialize response", e );
        }
	}
}