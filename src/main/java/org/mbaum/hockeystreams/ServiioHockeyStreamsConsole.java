package org.mbaum.hockeystreams;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.execution.ProgressPanelExecutor.createProgressPanelExecutor;
import static org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.GENERATE_IP_EXCEPTION_ACTION;
import static org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.GET_LIVE_STREAMS_ACTION;
import static org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.LOGIN_ACTION;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.log4j.Logger;
import org.mbaum.common.action.ActionBuilder;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessListener.ProcessListenerAdapter;
import org.mbaum.common.execution.ResultHandler;
import org.mbaum.common.model.ModelListener;
import org.mbaum.common.model.ProgressPanelModel;
import org.mbaum.common.view.ProgressPanel;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.GetLiveStreamsContext;
import org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.IpExectionsContext;
import org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.LoginContext;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.GetLiveResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.IpExceptionResponse;
import org.mbaum.hockeystreams.net.hockeystreams.transferobject.LoginResponse;
import org.mbaum.hockeystreams.view.HockeyStreamsActionPanel;
import org.mbaum.hockeystreams.view.LoginPanel;

public class ServiioHockeyStreamsConsole
{
	private static final Logger LOGGER = Logger.getLogger( ServiioHockeyStreamsConsole.class );
	private static final int CONSOLE_MIN_HEIGHT = 120;

	private final JFrame mFrame;
	private final HockeyStreamsComponent mHockeyStremsComponent;

    public ServiioHockeyStreamsConsole()
	{
    	mFrame = new JFrame( "Serviio HockeyStreams Console" );
        mHockeyStremsComponent = new HockeyStreamsComponent( mFrame );
        buildAndShowConsole( mFrame, mHockeyStremsComponent);
	}
    
	private static void buildAndShowConsole( JFrame frame, HockeyStreamsComponent hockeyStreamsComponent )
    {
	    initGui( frame, hockeyStreamsComponent );
	    frame.pack();
	    frame.setVisible( true );
    }

	private static void initGui( JFrame frame, HockeyStreamsComponent hockeyStreamsComponent )
	{
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    JComponent hockeyStreamsPanel = hockeyStreamsComponent.getComponent();
	    frame.setContentPane( hockeyStreamsPanel );
	    frame.setMinimumSize( new Dimension( hockeyStreamsPanel.getPreferredSize().width,
	                                         CONSOLE_MIN_HEIGHT ) );
	}
	
	public static void main( String[] args )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
			    new ServiioHockeyStreamsConsole();
			}
		} );
	}
	
	private static class HockeyStreamsComponent
	{
		private final LoginPanelModel mLoginPanelModel;
        private final HockeyStreamsModel mHockeyStreamsModel;
        private final ExecutableProcess<LoginResponse> mLoginAction;
        private final JComponent mPanel;
        private final ExecutableProcess<IpExceptionResponse> mIpExceptionAction;
        private final ExecutableProcess<GetLiveResponse> mGetLiveStreamsAction;
        private final ProcessExecutorService mHockeyStreamsExecutor;
		private ProgressPanelModel mProgressPanelModel;
		
		public HockeyStreamsComponent( JFrame parent )
		{
			mLoginPanelModel = new LoginPanelModel();
			mHockeyStreamsModel = new HockeyStreamsModel();
			mProgressPanelModel = new ProgressPanelModel();
			mHockeyStreamsExecutor = createProgressPanelExecutor( mProgressPanelModel );
			
            mLoginAction = 
            		mHockeyStreamsExecutor.buildExecutableProcess( LOGIN_ACTION,
                                         						   createLoginContext( mHockeyStreamsModel, 
                                         								               mLoginPanelModel ),
                                         				           createLoginListener( mHockeyStreamsModel, parent ),
                                         				           createLoginResultHandler( mProgressPanelModel ) );
            mIpExceptionAction = 
            		mHockeyStreamsExecutor.buildExecutableProcess( GENERATE_IP_EXCEPTION_ACTION, 
                                         						   createIpExceptionContext( mHockeyStreamsModel ),
                                         						   createIpExceptionListener() );
            mGetLiveStreamsAction = 
            		mHockeyStreamsExecutor.buildExecutableProcess( GET_LIVE_STREAMS_ACTION, 
                                         						   createGetLiveContext( mHockeyStreamsModel ),
                                         						   createGetLiveListener() );
            
			mPanel = buildGui( mLoginPanelModel, 
			                   mHockeyStreamsModel, 
			                   mProgressPanelModel,
			                   createLoginAction( mLoginAction, 
			                                      mLoginPanelModel, 
			                                      mHockeyStreamsExecutor ), 
			                   createIpExceptionAction( mIpExceptionAction, 
			                                            mHockeyStreamsModel, 
			                                            mHockeyStreamsExecutor ) , 
			                   createGetLiveStreamsAction( mGetLiveStreamsAction, 
			                                               mHockeyStreamsModel, 
			                                               mHockeyStreamsExecutor ) );
		}

		private static ProcessListener<GetLiveResponse> createGetLiveListener()
		{
			return new ProcessListenerAdapter<GetLiveResponse>()
			{
				@Override
				public void processSucceeded( GetLiveResponse result )
				{
					LOGGER.info( "Succeded to get live streams" );
					LOGGER.info( "Live Streams: " + result );
				}
	
				@Override
				public void processFailed( Exception exception )
				{
					LOGGER.error( "Failed to get live streams.", exception );
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
						LOGGER.info( "Ip exception generated successfully." );
					else
						LOGGER.info( FAILURE_MESSAGE ); // TODO: possibly display a message to the user.
				}
				
				@Override
				public void processFailed( Exception exception )
				{
					LOGGER.error( FAILURE_MESSAGE, exception );
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
					if ( "succeeded".equals( result.getStatus() ) )
					{
						LOGGER.info( "Login Succeeded." );
						model.setSessionToken( result.getToken() );
					}
					else
					{
						LOGGER.info( "Login Failed." );
						JOptionPane.showMessageDialog( messageDialogParent, 
												       "Login Failed.\n" + result.getMsg(),
												       "Login Error...", 
												       JOptionPane.ERROR_MESSAGE );
					}
				}
				
				@Override
				public void processFailed( Exception exception )
				{
					LOGGER.error( "Login Failed.", exception );
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

        private static LoginContext createLoginContext( final HockeyStreamsModel hockeyStreamsModel, 
                                                        final LoginPanelModel loginPanelModel )
        {
            return new LoginContext()
            {
                @Override
                public HockeyStreamsModel getHockeyStreamsModel()
                {
                    return hockeyStreamsModel;
                }

                @Override
                public LoginPanelModel getLoginPanelModel()
                {
                    return loginPanelModel;
                }
            };
        }

        public JComponent getComponent()
        {
            return mPanel;
        }

        private static JComponent buildGui( LoginPanelModel loginPanelModel, 
                                            HockeyStreamsModel hockeyStreamsModel,
                                            ProgressPanelModel progressPanelModel,
		                                    Action loginAction, 
		                                    Action ipExceptionAction,
		                                    Action getLiveStreamsAction )
		{
			JPanel panel = new JPanel( new BorderLayout() );
			
			panel.add( new LoginPanel( loginPanelModel, loginAction ).getCompnent(), BorderLayout.NORTH );
			panel.add( new HockeyStreamsActionPanel( ipExceptionAction, 
			                                         getLiveStreamsAction, 
			                                         hockeyStreamsModel ).getComponent(), 
			           BorderLayout.CENTER );
			panel.add( new ProgressPanel( progressPanelModel ).getComponent(), BorderLayout.SOUTH );
			
			return panel;
		}
        
        private static Action createLoginAction( ExecutableProcess<LoginResponse> loginActionExecutable,
                                                 LoginPanelModel model,
                                                 Executor executor )
        {
            final Action action = ActionBuilder.Builder( buildActionExecutable( loginActionExecutable, 
                                                                                executor ) )
                                               .setButtonText( "Login" )
                                               .setInitiallyEnabled( model.canAuthenticate() )
                                               .build();
            
            model.addListener( new ModelListener<LoginPanelModel>()
            {
                @Override
                public void modelChanged( LoginPanelModel model )
                {
                    action.setEnabled( model.canAuthenticate() );
                }
            } );
            
            return action;
        }
	}

    private static Action createIpExceptionAction( ExecutableProcess<IpExceptionResponse> ipExceptionAction, HockeyStreamsModel model, Executor executor )
    {
        final Action action = ActionBuilder.Builder( buildActionExecutable( ipExceptionAction, executor ) )
                                           .setButtonText( "Generate Ip Exception" )
                                           .setInitiallyEnabled( ! StringUtils.isBlank( model.getToken() ) )
                                           .build();
        model.addListener( new ModelListener<HockeyStreamsModel>()
        {
            @Override
            public void modelChanged( HockeyStreamsModel model )
            {
                action.setEnabled( ! StringUtils.isBlank( model.getToken() ) );
            }
        } );
        
        return action;
    }
    
    private static Action createGetLiveStreamsAction( ExecutableProcess<GetLiveResponse> getLiveStreamsAction, 
                                                      HockeyStreamsModel model,
                                                      Executor executor )
    {
        final Action action = ActionBuilder.Builder( buildActionExecutable( getLiveStreamsAction, executor ) )
                                           .setButtonText( "Get Live Streams" )
                                           .setInitiallyEnabled( ! StringUtils.isBlank( model.getToken() ) )
                                           .build();
        model.addListener( new ModelListener<HockeyStreamsModel>()
        {
            @Override
            public void modelChanged( HockeyStreamsModel model )
            {
                action.setEnabled( ! StringUtils.isBlank( model.getToken() ) );
            }
        } );
        
        return action;
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
}
