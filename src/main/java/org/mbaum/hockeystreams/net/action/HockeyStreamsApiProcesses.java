package org.mbaum.hockeystreams.net.action;

import static org.mbaum.common.net.parse.Parsers.newJsonParser;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.net.HttpProcess;
import org.mbaum.common.net.parse.Parsers;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.GetLiveResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.IpExceptionResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.LoginResponse;

public class HockeyStreamsApiProcesses
{
	private static final String LOGIN_PATH = "/Login";
	private static final String GET_LIVE_PATH = "/GetLive";
	private static final String IP_EXCEPTION_PATH = "/IPException";

	private static final String HOCKEYSTREAMS_API_URL = "https://api.hockeystreams.com";
	
    public static final Process<LoginContext, LoginResponse> 			 LOGIN_PROCESS           = createLoginProcess();
    public static final Process<IpExectionsContext, IpExceptionResponse> IP_EXCEPTION_PROCESS    = createIpExceptionProcess();
    public static final Process<GetLiveStreamsContext, GetLiveResponse>  GET_LIVE_STREAMS_ACTION = createGetLiveStreamsProcess();
    
    
    public static interface IpExectionsContext extends ProcessContext
    {
        HockeyStreamsModel getModel();
    }
    
    public static interface GetLiveStreamsContext extends ProcessContext
    {
        HockeyStreamsModel getModel();
    }
    
    public static interface LoginContext extends ProcessContext
    {
        LoginPanelModel getLoginPanelModel();
    }
    
    private static Process<IpExectionsContext, IpExceptionResponse> createIpExceptionProcess()
    {
        return new HttpProcess<IpExectionsContext, IpExceptionResponse>( newJsonParser( IpExceptionResponse.class ), 
        		                      									 "generate ip exception" )
        {
            @Override
            public boolean canExecuteWith( IpExectionsContext context )
            {
                return ! StringUtils.isBlank( context.getModel().getToken() );
            }

			@Override
			protected HttpUriRequest buildRequest( IpExectionsContext context )
			{
				HttpEntity entity = 
				        EntityBuilder.create()
									  .setParameters( new BasicNameValuePair( "token", context.getModel().getToken() ) )
									  .build();
				
				return RequestBuilder.post().setUri( HOCKEYSTREAMS_API_URL + IP_EXCEPTION_PATH )
		                   				    .setEntity( entity )
		                                    .addHeader( "content-type", "application/x-www-form-urlencoded" )
		                                    .build();
			}
        };
    }

    private static Process<GetLiveStreamsContext, GetLiveResponse> createGetLiveStreamsProcess()
    {
        return new HttpProcess<GetLiveStreamsContext, GetLiveResponse>( newJsonParser( GetLiveResponse.class ), "get live streams" )
        {
            @Override
            public boolean canExecuteWith( GetLiveStreamsContext context )
            {
                return ! StringUtils.isBlank( context.getModel().getToken() );
            }

			@Override
			protected HttpUriRequest buildRequest( GetLiveStreamsContext context )
			{
	            return RequestBuilder.get().setUri( HOCKEYSTREAMS_API_URL + GET_LIVE_PATH )
	            		                   .addParameter( "token", context.getModel().getToken() )
	            		                   .addHeader( "content-type", "application/x-www-form-urlencoded" )
	            		                   .build();
			}
        };
    }

    private static Process<LoginContext, LoginResponse> createLoginProcess()
    {
    	return new HttpProcess<LoginContext, LoginResponse>( newJsonParser( LoginResponse.class ), "login" )
        {
            @Override
            public boolean canExecuteWith( LoginContext context )
            {
                LoginPanelModel loginPanelModel = context.getLoginPanelModel();
                
                if ( StringUtils.isBlank( loginPanelModel.getUsername() ) )
                    return false;
                
                if ( StringUtils.isBlank( loginPanelModel.getPassword() ) )
                    return false;
                
                return ! StringUtils.isBlank( loginPanelModel.getApiKey() );
            }

			@Override
			protected HttpUriRequest buildRequest( LoginContext context )
			{
				LoginPanelModel loginPanelModel = context.getLoginPanelModel();
				
				HttpEntity entity = 
			        EntityBuilder.create()
								  .setParameters( new BasicNameValuePair( "username", loginPanelModel.getUsername() ),
										  		  new BasicNameValuePair( "password", loginPanelModel.getPassword() ),
										  		  new BasicNameValuePair( "key", loginPanelModel.getApiKey() ) )
								  .build();
				
				return RequestBuilder.post()
						             .setUri( HOCKEYSTREAMS_API_URL + LOGIN_PATH )
						             .setEntity( entity )
						             .addHeader( "content-type", "application/x-www-form-urlencoded" )
						             .build();
			}
        };
    }
}
