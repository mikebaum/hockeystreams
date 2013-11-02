package org.mbaum.hockeystreams.net.hockeystreams.action;

import static org.mbaum.hockeystreams.net.HockeyStreamsApiClient.generateIpException;
import static org.mbaum.hockeystreams.net.HockeyStreamsApiClient.getLiveStreams;
import static org.mbaum.hockeystreams.net.HockeyStreamsApiClient.login;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.execution.AbstractProcess;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.GetLiveResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.IpExceptionResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.LoginResponse;

public class HockeyStreamsApiActions
{
    public static final Process<LoginContext, LoginResponse> LOGIN_ACTION = createLoginProcess();
    public static final Process<IpExectionsContext, IpExceptionResponse> GENERATE_IP_EXCEPTION_ACTION = createIpExceptionProcess();
    public static final Process<GetLiveStreamsContext, GetLiveResponse> GET_LIVE_STREAMS_ACTION = createGetLiveStreamsProcess();
    
    
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
        HockeyStreamsModel getHockeyStreamsModel();
        LoginPanelModel getLoginPanelModel();
    }
    
    private static Process<IpExectionsContext, IpExceptionResponse> createIpExceptionProcess()
    {
        return new AbstractProcess<IpExectionsContext, IpExceptionResponse>( "generate ip exception" )
        {
            @Override
            public IpExceptionResponse execute( IpExectionsContext context ) throws Exception
            {
                return generateIpException( context.getModel() );
            }
            
            @Override
            public boolean canExecuteWith( IpExectionsContext context )
            {
                return ! StringUtils.isBlank( context.getModel().getToken() );
            }
        };
    }

    private static Process<GetLiveStreamsContext, GetLiveResponse> createGetLiveStreamsProcess()
    {
        return new AbstractProcess<GetLiveStreamsContext, GetLiveResponse>( "get live streams" )
        {
            @Override
            public GetLiveResponse execute( GetLiveStreamsContext context ) throws Exception
            {
                return getLiveStreams( context.getModel() );
            }
            
            @Override
            public boolean canExecuteWith( GetLiveStreamsContext context )
            {
                return ! StringUtils.isBlank( context.getModel().getToken() );
            }
        };
    }

    private static Process<LoginContext, LoginResponse> createLoginProcess()
    {
    	return new AbstractProcess<LoginContext, LoginResponse>( "login" )
        {
            @Override
            public LoginResponse execute( LoginContext context ) throws Exception
            {
            	LoginPanelModel loginPanelModel = context.getLoginPanelModel();
            	return login( loginPanelModel.getUsername(),
            				  loginPanelModel.getPassword(),
            				  loginPanelModel.getApiKey() );
            }
            
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
        };
    }
}
