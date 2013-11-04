package org.mbaum.common.net;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpClientUtils
{
    private static final Logger LOGGER = Logger.getLogger( HttpClientUtils.class );

	private HttpClientUtils(){}
    
    public static <R> R parseResponse( HttpResponse response, Class<R> responseClass ) 
        throws JsonParseException, JsonMappingException, IOException 
    {
        InputStream responseStream = response.getEntity().getContent();
        
        String responseString = IOUtils.toString( responseStream );
        LOGGER.info( "parsing response: " + responseString );
    
        ObjectMapper mapper = new ObjectMapper();
    
        return mapper.readValue( responseString, responseClass );
    }
}
