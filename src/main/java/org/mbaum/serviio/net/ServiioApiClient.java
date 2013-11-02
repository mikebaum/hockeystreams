package org.mbaum.serviio.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.mbaum.common.net.HttpClientUtils;
import org.mbaum.serviio.net.transferobject.PingResponse;

public class ServiioApiClient
{
    private static final Logger LOGGER = Logger.getLogger( ServiioApiClient.class );
    
    public static void ping() throws ClientProtocolException, IOException
    {
        String actionPath = "ping";
        LOGGER.info( "Executing GET request: " + actionPath  );
        
        HttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet( "http://localhost:23423/rest/ping" );
        request.setHeader( "Accept", "application/json" );

        HttpResponse response = client.execute( request );
        
        LOGGER.info( "GET request: " + actionPath + " succeeded" );
        
        PingResponse pingResponse = HttpClientUtils.parseResponse( response, PingResponse.class );
        
        LOGGER.info( "GET reponse: " + pingResponse );
    }
    
    public static void repository() throws ClientProtocolException, IOException
    {
        String actionPath = "ping";
        LOGGER.info( "Executing GET request: " + actionPath  );
        
        HttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet( "http://localhost:23423/rest/repository" );
        request.setHeader( "Accept", "application/json" );

        HttpResponse response = client.execute( request );
        
        LOGGER.info( "GET request: " + actionPath + " succeeded" );
        
        PingResponse pingResponse = HttpClientUtils.parseResponse( response, PingResponse.class );
        
        LOGGER.info( "GET reponse: " + pingResponse );
    }
    
    public static void main( String[] args )
    {
        try
        {
            ping();
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
    }
}
