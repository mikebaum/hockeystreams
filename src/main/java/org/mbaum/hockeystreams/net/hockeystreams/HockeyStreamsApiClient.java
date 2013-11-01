package org.mbaum.hockeystreams.net.hockeystreams;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.GetLiveResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.IpExceptionResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.LoginResponse;

public class HockeyStreamsApiClient
{
    private static final Logger LOGGER = Logger.getLogger( HockeyStreamsApiClient.class );

    private static final String HOCKEYSTREAMS_API_URL = "https://api.hockeystreams.com";
    
    private static final String LOGIN_PATH = "/Login?";
    private static final String IP_EXCEPTION_PATH = "/IPException?";
    private static final String GET_LIVE_PATH = "/GetLive";

    public static LoginResponse login( String username, String password, String apiKey ) throws HockeyStreamsApiException
    {
        String actionPath = LOGIN_PATH;
        String entityString = "username=" + username + "&password=" + password + "&key=" + apiKey;
        String failureMessage = "Failed to login.";

        try
        {
            return parseResponse( doPost( actionPath, entityString ), LoginResponse.class );
        }
        catch ( Exception e )
        {
            throw new HockeyStreamsApiException( failureMessage, e );
        }
    }

    public static void generateIpException( HockeyStreamsModel model ) throws HockeyStreamsApiException
    {
        String actionPath = IP_EXCEPTION_PATH;
        String entityString = "token=" + model.getToken();
        String failureMessage = "Failed to generate ip exception.";

        try
        {
            IpExceptionResponse ipExceptionResponse = parseResponse( doPost( actionPath, entityString ), 
                                                                     IpExceptionResponse.class );

            LOGGER.info( actionPath + " request result: " + ipExceptionResponse );

            if ( StringUtils.equals( "Failed", ipExceptionResponse.getStatus() ) )
                throw new HockeyStreamsApiException( failureMessage );
        }
        catch ( Exception e )
        {
            throw new HockeyStreamsApiException( failureMessage, e );
        }
    }

    public static void getLiveStreams( HockeyStreamsModel model ) throws HockeyStreamsApiException
    {
        LOGGER.info( "Executing GET request: " + GET_LIVE_PATH );
        
        HttpClient client = HttpClients.createDefault();

        try
        {
            URIBuilder uriBuilder = new URIBuilder( HOCKEYSTREAMS_API_URL + GET_LIVE_PATH );
            uriBuilder.addParameter( "token", model.getToken() );

            HttpGet request = new HttpGet( uriBuilder.build() );

            request.setHeader( "content-type", "application/x-www-form-urlencoded" );
            HttpResponse response = client.execute( request );

            LOGGER.info( "GET request: " + GET_LIVE_PATH + " succeeded" );
            
            GetLiveResponse getLiveResult = parseResponse( response, GetLiveResponse.class );
            
            LOGGER.info( GET_LIVE_PATH + " parsed result: " + getLiveResult );
        }
        catch ( Exception e )
        {
            throw new HockeyStreamsApiException( "failed to get live streams", e );
        }
    }

    private static HttpResponse doPost( String actionPath, String entityString ) throws ClientProtocolException, IOException
    {
        LOGGER.info( "Executing POST request: " + actionPath );
        
        HttpClient client = HttpClients.createDefault();

        HttpPost request = new HttpPost( HOCKEYSTREAMS_API_URL + actionPath );
        request.setHeader( "content-type", "application/x-www-form-urlencoded" );

        StringEntity entity = new StringEntity( entityString );
        request.setEntity( entity );

        HttpResponse response = client.execute( request );
        
        LOGGER.info( "POST request: " + actionPath + " succeeded" );
        
        return response;
    }

    private static <R> R parseResponse( HttpResponse response, Class<R> responseClass ) 
        throws JsonParseException, JsonMappingException, IOException 
    {
        InputStream responseStream = response.getEntity().getContent();
        
        String responseString = IOUtils.toString( responseStream );
        LOGGER.info( "parsing response: " + responseString );

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue( responseString, responseClass );
    }
}