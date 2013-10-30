package org.mbaum.hockeystreams.net.hockeystreams.action;

import static org.mbaum.hockeystreams.net.hockeystreams.HockeyStreamsApiClient.generateIpException;
import static org.mbaum.hockeystreams.net.hockeystreams.HockeyStreamsApiClient.getLiveStreams;
import static org.mbaum.hockeystreams.net.hockeystreams.HockeyStreamsApiClient.login;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.execution.AbstractProcess;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.model.LoginPanelModel;

public class HockeyStreamsApiActions
{
    public static final Process<LoginContext> LOGIN_ACTION = createLoginProcess();
    public static final Process<IpExectionsContext> GENERATE_IP_EXCEPTION_ACTION = createIpExceptionProcess();
    public static final Process<GetLiveStreamsContext> GET_LIVE_STREAMS_ACTION = createGetLiveStreamsProcess();
    
    
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
    
    private static Process<IpExectionsContext> createIpExceptionProcess()
    {
        return new AbstractProcess<IpExectionsContext>( "generate ip exception" )
        {
            @Override
            public void execute( IpExectionsContext context ) throws Exception
            {
                generateIpException( context.getModel() );
            }
            
            @Override
            public boolean canExecuteWith( IpExectionsContext context )
            {
                return ! StringUtils.isBlank( context.getModel().getToken() );
            }
        };
    }

    private static Process<GetLiveStreamsContext> createGetLiveStreamsProcess()
    {
        return new AbstractProcess<GetLiveStreamsContext>( "get live streams" )
        {
            @Override
            public void execute( GetLiveStreamsContext context ) throws Exception
            {
                getLiveStreams( context.getModel() );
            }
            
            @Override
            public boolean canExecuteWith( GetLiveStreamsContext context )
            {
                return ! StringUtils.isBlank( context.getModel().getToken() );
            }
        };
    }

    private static Process<LoginContext> createLoginProcess()
    {
    	return new AbstractProcess<LoginContext>( "login" )
        {
            @Override
            public void execute( LoginContext context ) throws Exception
            {
                LoginPanelModel loginPanelModel = context.getLoginPanelModel();
                context.getHockeyStreamsModel().setSessionToken( login( loginPanelModel.getUsername(),
                                                                        loginPanelModel.getPassword(),
                                                                        loginPanelModel.getApiKey() ).getToken() );
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
