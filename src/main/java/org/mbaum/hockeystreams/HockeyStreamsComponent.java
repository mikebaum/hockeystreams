package org.mbaum.hockeystreams;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.action.ActionUtils.createAction;
import static org.mbaum.common.action.ActionUtils.createActionModel;
import static org.mbaum.common.execution.ProgressPanelExecutor.createProgressPanelExecutor;
import static org.mbaum.common.model.ProgressPanelModel.MESSAGE;
import static org.mbaum.common.veto.Vetoers.createVetoer;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.GET_LIVE_STREAMS_ACTION;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.GET_LIVE_VALIDATOR;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.IP_EXCEPTION_PROCESS;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.IP_EXCEPTION_VALIDATOR;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.LOGIN_PROCESS;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.LOGIN_VALIDATOR;

import java.awt.BorderLayout;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mbaum.common.Component;
import org.mbaum.common.Destroyable;
import org.mbaum.common.action.ActionExecutable;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessListenerAdapter;
import org.mbaum.common.execution.ResultHandler;
import org.mbaum.common.model.ProgressPanelModel;
import org.mbaum.common.model.ProgressPanelModelImpl;
import org.mbaum.common.veto.Vetoer;
import org.mbaum.common.view.ActionPanel;
import org.mbaum.common.view.ProgressPanel;
import org.mbaum.common.view.View;
import org.mbaum.common.view.ViewBuilder;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.model.HockeyStreamsModelImpl;
import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.model.LoginPanelModelImpl;
import org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.GetLiveStreamsContext;
import org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.IpExectionsContext;
import org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.LoginContext;
import org.mbaum.hockeystreams.net.transferobject.GetLiveResponse;
import org.mbaum.hockeystreams.net.transferobject.IpExceptionResponse;
import org.mbaum.hockeystreams.net.transferobject.LoginResponse;
import org.mbaum.hockeystreams.view.LoginPanel;

public class HockeyStreamsComponent extends AbstractComponent implements Component
{
	private static final String LOGIN = "LOGIN";
	private static final String IP_EXCEPTION = "IP_EXCEPTION";
	private static final String LIVE_STREAMS = "LIVE_STREAMS";

	static final Logger LOGGER = Logger.getLogger( HockeyStreamsComponent.class );
	
	private final LoginPanelModel mLoginPanelModel;
    private final HockeyStreamsModel mHockeyStreamsModel;
    private final ExecutableProcess<LoginResponse> mLoginProcess;
    private final ExecutableProcess<IpExceptionResponse> mIpExceptionProcess;
    private final ExecutableProcess<GetLiveResponse> mGetLiveStreamsProcess;
    private final ActionExecutable mLoginActionExecutable;
    private final ActionExecutable mIpExceptionActionExecutable;
    private final ActionExecutable mGetLiveStreamsActionExecutable;
    private final ProcessExecutorService mHockeyStreamsExecutor;
	private final ProgressPanelModel mProgressPanelModel;
	private final View mView;
	
	public HockeyStreamsComponent( JFrame parent )
	{
		mLoginPanelModel = d( new LoginPanelModelImpl() );
		mHockeyStreamsModel = d( new HockeyStreamsModelImpl() );
		mProgressPanelModel = d( new ProgressPanelModelImpl() );
		mHockeyStreamsExecutor = createProgressPanelExecutor( mProgressPanelModel, "HockeyStreamsRESTApiExecutor" );
		
        mLoginProcess = 
        		mHockeyStreamsExecutor.buildExecutableProcess( LOGIN_PROCESS,
                                     						   createLoginContext( mLoginPanelModel ),
                                     				           createLoginListener( mHockeyStreamsModel, parent ),
                                     				           createLoginResultHandler( mProgressPanelModel ) );
        mIpExceptionProcess = 
        		mHockeyStreamsExecutor.buildExecutableProcess( IP_EXCEPTION_PROCESS, 
                                     						   createIpExceptionContext( mHockeyStreamsModel ),
                                     						   createIpExceptionListener() );
        mGetLiveStreamsProcess = 
        		mHockeyStreamsExecutor.buildExecutableProcess( GET_LIVE_STREAMS_ACTION, 
                                     						   createGetLiveContext( mHockeyStreamsModel ),
                                     						   createGetLiveListener() );
        
		mLoginActionExecutable = d( createLoginActionExecutable( mLoginProcess,
		                                                         mHockeyStreamsExecutor,
		                                                         createVetoer( mLoginPanelModel, 
		                                                                       LOGIN_VALIDATOR ) ) );
		
        mIpExceptionActionExecutable = d( createIpExceptionActionExecutable( mIpExceptionProcess, 
                                                                             mHockeyStreamsExecutor,
                                                                             createVetoer( mHockeyStreamsModel, 
                                                                                           IP_EXCEPTION_VALIDATOR ) ) );
        mGetLiveStreamsActionExecutable = d( createGetLiveStreamsActionExecutable( mGetLiveStreamsProcess, 
                                                                                   mHockeyStreamsExecutor,
                                                                                   createVetoer( mHockeyStreamsModel, 
                                                                                                 GET_LIVE_VALIDATOR ) ) );
        
        mView = doBuildView( mLoginPanelModel, 
                             mHockeyStreamsModel, 
                             mProgressPanelModel, 
                             mLoginActionExecutable, 
                             mIpExceptionActionExecutable, 
                             mGetLiveStreamsActionExecutable );
	}
	
	@Override
	public View getView()
	{
	    return mView;
	}
	
	private static ResultHandler<LoginResponse> createLoginResultHandler( final ProgressPanelModel progressPanelModel )
	{
		return new ResultHandler<LoginResponse>()
		{
			@Override
			public void handleResult( LoginResponse result )
			{
				String status = result.getStatus();
				if ( StringUtils.equals( "Failed", status ) )
					progressPanelModel.setValue( MESSAGE, "Login Failed" );
				else if ( StringUtils.equals( "success", result.getStatus() ) )
					progressPanelModel.setValue( MESSAGE, "Login Succeeded" );
				else
					progressPanelModel.setValue( MESSAGE, "Unknown Login result: " + result.getStatus() );
					
			}
		};
	}
    
    private static ActionExecutable createLoginActionExecutable( ExecutableProcess<LoginResponse> loginActionExecutable,
                                                                 Executor executor,
                                                                 Vetoer vetoer )
    {
        return buildActionExecutable( loginActionExecutable, createActionModel( LOGIN, vetoer ), executor );
    }

	private static ActionExecutable createGetLiveStreamsActionExecutable( ExecutableProcess<GetLiveResponse> getLiveStreamsAction, 
	                                                                      Executor executor,
	                                                                      Vetoer vetoer )
	{
	    return buildActionExecutable( getLiveStreamsAction, createActionModel( LIVE_STREAMS, vetoer ), executor );
	}

	private static ActionExecutable createIpExceptionActionExecutable( ExecutableProcess<IpExceptionResponse> ipExceptionAction, 
	                                                                   Executor executor,
	                                                                   Vetoer vetoer )
	{
	    return buildActionExecutable( ipExceptionAction, createActionModel( IP_EXCEPTION, vetoer ), executor );
	}

	private static ProcessListener<GetLiveResponse> createGetLiveListener()
	{
		return new ProcessListenerAdapter<GetLiveResponse>()
		{
			@Override
			public void handleResult( GetLiveResponse result )
			{
				LOGGER.info( "Live Streams: " + result );
			}
	    };
	}

	private static ProcessListener<IpExceptionResponse> createIpExceptionListener()
	{
		return new ProcessListenerAdapter<IpExceptionResponse>()
		{
			private static final String FAILURE_MESSAGE = "Failed to generate ip exception.";
			
			@Override
			public void handleResult( IpExceptionResponse result )
			{
				if ( ! "succeeded".equals( result.getStatus() ) )
					LOGGER.info( "Ip exception generated successfully." );
				else
					LOGGER.info( FAILURE_MESSAGE ); // TODO: possibly display a message to the user.
			}
		};
	}

	private static ProcessListener<LoginResponse> createLoginListener( final HockeyStreamsModel model, 
			                                                           final java.awt.Component messageDialogParent )
	{
		return new ProcessListenerAdapter<LoginResponse>()
		{
			@Override
			public void handleResult( LoginResponse result )
			{
				if ( "Success".equals( result.getStatus() ) )
				{
					model.setValue( HockeyStreamsModel.SESSION_TOKEN, result.getToken() );
				}
				else
				{
					JOptionPane.showMessageDialog( messageDialogParent, 
											       "Login Failed.\n" + result.getMsg(),
											       "Login Error...", 
											       JOptionPane.ERROR_MESSAGE );
				}
			}
		};
	}

	private static GetLiveStreamsContext createGetLiveContext( final HockeyStreamsModel model )
    {
        return new GetLiveStreamsContext()
        {
            @Override
            public HockeyStreamsModel getModel()
            {
                return model;
            }
        };
    }

    private static IpExectionsContext createIpExceptionContext( final HockeyStreamsModel model )
    {
        return new IpExectionsContext()
        {
            @Override
            public HockeyStreamsModel getModel()
            {
                return model;
            }
        };
    }

    private static LoginContext createLoginContext( final LoginPanelModel loginPanelModel )
    {
        return new LoginContext()
        {
            @Override
            public LoginPanelModel getLoginPanelModel()
            {
                return loginPanelModel;
            }
        };
    }

    private static View doBuildView( LoginPanelModel loginPanelModel, 
    								 HockeyStreamsModel hockeyStreamsModel,
    								 ProgressPanelModel progressPanelModel,
    								 ActionExecutable loginActionExecutable, 
    								 ActionExecutable ipExceptionActionExecutable,
    								 ActionExecutable getLiveStreamsActionExecutable )
	{
		final JPanel panel = new JPanel( new BorderLayout() );
		
        Action loginAction = createAction( loginActionExecutable, "Login" );
        Action ipExceptionAction = createAction( ipExceptionActionExecutable, "Generate Ip Exception" );
        Action getLiveStreamsAction = createAction( getLiveStreamsActionExecutable, "Get Live Streams" );
		
		final LoginPanel loginPanel = new LoginPanel( loginPanelModel, loginAction );
		panel.add( loginPanel.getComponent(), BorderLayout.NORTH );
		
		final ActionPanel actionPanel = new ActionPanel( ipExceptionAction, getLiveStreamsAction );
		panel.add( actionPanel.getComponent(), BorderLayout.CENTER );
		
		final ProgressPanel progressPanel = new ProgressPanel( progressPanelModel );
		panel.add( progressPanel.getComponent(), BorderLayout.SOUTH );
		
		return new ViewBuilder.ViewImpl( new Destroyable()
		{
			@Override
			public void destroy()
			{
				panel.removeAll();
				
				loginPanel.destroy();
				actionPanel.destroy();
				progressPanel.destroy();
			}
		}, panel );
	}
}