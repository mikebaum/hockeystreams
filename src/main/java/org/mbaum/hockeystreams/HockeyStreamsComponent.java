package org.mbaum.hockeystreams;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.action.ActionUtils.createAction;
import static org.mbaum.common.action.ActionUtils.createActionModel;
import static org.mbaum.common.execution.ProgressPanelExecutor.createProgressPanelExecutor;
import static org.mbaum.common.veto.Vetoers.createVetoer;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.GET_LIVE_STREAMS_ACTION;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.GET_LIVE_VALIDATOR;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.IP_EXCEPTION_PROCESS;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.IP_EXCEPTION_VALIDATOR;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.LOGIN_PROCESS;
import static org.mbaum.hockeystreams.net.action.HockeyStreamsApiProcesses.LOGIN_VALIDATOR;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.action.ActionExecutable;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessListener.ProcessListenerAdapter;
import org.mbaum.common.execution.ResultHandler;
import org.mbaum.common.model.ProgressPanelModel;
import org.mbaum.common.model.ProgressPanelModelImpl;
import org.mbaum.common.veto.Vetoer;
import org.mbaum.common.view.ActionPanel;
import org.mbaum.common.view.ProgressPanel;
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

class HockeyStreamsComponent
{
	private final LoginPanelModel mLoginPanelModel;
    private final HockeyStreamsModel mHockeyStreamsModel;
    private final JComponent mPanel;
    private final ExecutableProcess<LoginResponse> mLoginProcess;
    private final ExecutableProcess<IpExceptionResponse> mIpExceptionProcess;
    private final ExecutableProcess<GetLiveResponse> mGetLiveStreamsProcess;
    private final ActionExecutable mLoginActionExecutable;
    private final ActionExecutable mIpExceptionActionExecutable;
    private final ActionExecutable mGetLiveStreamsActionExecutable;
    private final ProcessExecutorService mHockeyStreamsExecutor;
	private final ProgressPanelModel mProgressPanelModel;
	
	public HockeyStreamsComponent( JFrame parent )
	{
		mLoginPanelModel = new LoginPanelModelImpl();
		mHockeyStreamsModel = new HockeyStreamsModelImpl();
		mProgressPanelModel = new ProgressPanelModelImpl();
		mHockeyStreamsExecutor = createProgressPanelExecutor( mProgressPanelModel );
		
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
        
		mLoginActionExecutable = createLoginActionExecutable( mLoginProcess,
		                                                      mHockeyStreamsExecutor,
		                                                      createVetoer( mLoginPanelModel, 
		                                                                    LOGIN_VALIDATOR ) );
		
        mIpExceptionActionExecutable = createIpExceptionActionExecutable( mIpExceptionProcess, 
                                                                          mHockeyStreamsExecutor,
                                                                          createVetoer( mHockeyStreamsModel, 
                                                                                        IP_EXCEPTION_VALIDATOR ) );
        mGetLiveStreamsActionExecutable = createGetLiveStreamsActionExecutable( mGetLiveStreamsProcess, 
                                                                                mHockeyStreamsExecutor,
                                                                                createVetoer( mHockeyStreamsModel, 
                                                                                              GET_LIVE_VALIDATOR ) );
        mPanel = buildGui( mLoginPanelModel, 
		                   mHockeyStreamsModel, 
		                   mProgressPanelModel,
		                   mLoginActionExecutable, 
		                   mIpExceptionActionExecutable, 
		                   mGetLiveStreamsActionExecutable );
	}

    public JComponent getComponent()
    {
        return mPanel;
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
					progressPanelModel.setMessage( "Login Failed" );
				else if ( StringUtils.equals( "success", result.getStatus() ) )
					progressPanelModel.setMessage( "Login Succeeded" );
				else
					progressPanelModel.setMessage( "Unknown Login result: " + result.getStatus() );
					
			}
		};
	}
    
    private static ActionExecutable createLoginActionExecutable( ExecutableProcess<LoginResponse> loginActionExecutable,
                                                                 Executor executor,
                                                                 Vetoer vetoer )
    {
        return buildActionExecutable( loginActionExecutable, createActionModel( vetoer ), executor );
    }

	private static ActionExecutable createGetLiveStreamsActionExecutable( ExecutableProcess<GetLiveResponse> getLiveStreamsAction, 
	                                                                      Executor executor,
	                                                                      Vetoer vetoer )
	{
	    return buildActionExecutable( getLiveStreamsAction, createActionModel( vetoer ), executor );
	}

	private static ActionExecutable createIpExceptionActionExecutable( ExecutableProcess<IpExceptionResponse> ipExceptionAction, 
	                                                                   Executor executor,
	                                                                   Vetoer vetoer )
	{
	    return buildActionExecutable( ipExceptionAction, createActionModel( vetoer ), executor );
	}

	private static ProcessListener<GetLiveResponse> createGetLiveListener()
	{
		return new ProcessListenerAdapter<GetLiveResponse>()
		{
			@Override
			public void processSucceeded( GetLiveResponse result )
			{
				ServiioHockeyStreamsConsole.LOGGER.info( "Succeded to get live streams" );
				ServiioHockeyStreamsConsole.LOGGER.info( "Live Streams: " + result );
			}

			@Override
			public void processFailed( Exception exception )
			{
				ServiioHockeyStreamsConsole.LOGGER.error( "Failed to get live streams.", exception );
			}
	    };
	}

	private static ProcessListener<IpExceptionResponse> createIpExceptionListener()
	{
		return new ProcessListenerAdapter<IpExceptionResponse>()
		{
			private static final String FAILURE_MESSAGE = "Failed to generate ip exception.";
			
			@Override
			public void processSucceeded( IpExceptionResponse result )
			{
				if ( ! "succeeded".equals( result.getStatus() ) )
					ServiioHockeyStreamsConsole.LOGGER.info( "Ip exception generated successfully." );
				else
					ServiioHockeyStreamsConsole.LOGGER.info( FAILURE_MESSAGE ); // TODO: possibly display a message to the user.
			}
			
			@Override
			public void processFailed( Exception exception )
			{
				ServiioHockeyStreamsConsole.LOGGER.error( FAILURE_MESSAGE, exception );
			}
		};
	}

	private static ProcessListener<LoginResponse> createLoginListener( final HockeyStreamsModel model, 
			                                                           final Component messageDialogParent )
	{
		return new ProcessListenerAdapter<LoginResponse>()
		{
			@Override
			public void processSucceeded( LoginResponse result )
			{
				if ( "Success".equals( result.getStatus() ) )
				{
					ServiioHockeyStreamsConsole.LOGGER.info( "Login Succeeded." );
					model.setSessionToken( result.getToken() );
				}
				else
				{
					ServiioHockeyStreamsConsole.LOGGER.info( "Login Failed." );
					JOptionPane.showMessageDialog( messageDialogParent, 
											       "Login Failed.\n" + result.getMsg(),
											       "Login Error...", 
											       JOptionPane.ERROR_MESSAGE );
				}
			}
			
			@Override
			public void processFailed( Exception exception )
			{
				ServiioHockeyStreamsConsole.LOGGER.error( "Login Failed.", exception );
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

    private static JComponent buildGui( LoginPanelModel loginPanelModel, 
                                        HockeyStreamsModel hockeyStreamsModel,
                                        ProgressPanelModel progressPanelModel,
	                                    ActionExecutable loginActionExecutable, 
	                                    ActionExecutable ipExceptionActionExecutable,
	                                    ActionExecutable getLiveStreamsActionExecutable )
	{
		JPanel panel = new JPanel( new BorderLayout() );
		
        Action loginAction = createAction( loginActionExecutable, "Login" );
        Action ipExceptionAction = createAction( ipExceptionActionExecutable, "Generate Ip Exception" );
        Action getLiveStreamsAction = createAction( getLiveStreamsActionExecutable, "Get Live Streams" );
		
		panel.add( new LoginPanel( loginPanelModel, loginAction ).getCompnent(), BorderLayout.NORTH );
		panel.add( new ActionPanel( ipExceptionAction, getLiveStreamsAction ).getComponent(), 
		           BorderLayout.CENTER );
		panel.add( new ProgressPanel( progressPanelModel ).getComponent(), BorderLayout.SOUTH );
		
		return panel;
	}
}