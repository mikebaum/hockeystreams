package org.mbaum.serviio.net;

import static org.mbaum.common.net.parse.ResponseParsers.newJsonParser;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.mbaum.common.net.parse.ResponseParseException;
import org.mbaum.serviio.net.transferobject.PingResponse;

public class ServiioApiClient
{
    private static final Logger LOGGER = Logger.getLogger( ServiioApiClient.class );
    
    public static void repository() throws ClientProtocolException, IOException, ResponseParseException
    {
        String actionPath = "ping";
        LOGGER.info( "Executing GET request: " + actionPath  );
        
        HttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet( "http://localhost:23423/rest/repository" );
        request.setHeader( "Accept", "application/json" );

        HttpResponse response = client.execute( request );
        
        LOGGER.info( "GET request: " + actionPath + " succeeded" );
        
        PingResponse pingResponse = newJsonParser( PingResponse.class ).parseResponse( response );
        
        LOGGER.info( "GET reponse: " + pingResponse );
    }
    
    public static void main( String[] args )
    {
        try
        {
            repository();
        }
        catch ( ClientProtocolException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch ( ResponseParseException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
