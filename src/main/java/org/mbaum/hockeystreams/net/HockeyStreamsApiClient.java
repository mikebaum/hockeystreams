package org.mbaum.hockeystreams.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.mbaum.common.net.HttpClientUtils;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.GetLiveResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.IpExceptionResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.LoginResponse;

public class HockeyStreamsApiClient
{
    public static final Logger LOGGER = Logger.getLogger( HockeyStreamsApiClient.class );

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
            return HttpClientUtils.parseResponse( doPost( actionPath, entityString ), LoginResponse.class );
        }
        catch ( Exception e )
        {
            throw new HockeyStreamsApiException( failureMessage, e );
        }
    }

    public static IpExceptionResponse generateIpException( HockeyStreamsModel model ) throws HockeyStreamsApiException
    {
        String actionPath = IP_EXCEPTION_PATH;
        String entityString = "token=" + model.getToken();
        String failureMessage = "Failed to generate ip exception.";

        try
        {
        	return HttpClientUtils.parseResponse( doPost( actionPath, entityString ), 
        										  IpExceptionResponse.class );
//            IpExceptionResponse ipExceptionResponse = HttpClientUtils.parseResponse( doPost( actionPath, entityString ), 
//                                                                     IpExceptionResponse.class );
//
//            LOGGER.info( actionPath + " request result: " + ipExceptionResponse );
//
//            if ( StringUtils.equals( "Failed", ipExceptionResponse.getStatus() ) )
//                throw new HockeyStreamsApiException( failureMessage );
        }
        catch ( Exception e )
        {
            throw new HockeyStreamsApiException( failureMessage, e );
        }
    }

    public static GetLiveResponse getLiveStreams( HockeyStreamsModel model ) throws HockeyStreamsApiException
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
            
            GetLiveResponse getLiveResult = HttpClientUtils.parseResponse( response, GetLiveResponse.class );
            
            LOGGER.info( GET_LIVE_PATH + " parsed result: " + getLiveResult );
            
            return getLiveResult;
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
        
        LOGGER.info( "POST request: " + actionPath + " complete" );
        
        return response;
    }
}
