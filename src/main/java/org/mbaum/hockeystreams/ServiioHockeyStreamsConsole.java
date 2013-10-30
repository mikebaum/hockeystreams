package org.mbaum.hockeystreams;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.execution.ProcessUtils.createExecutableProcess;
import static org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.GENERATE_IP_EXCEPTION_ACTION;
import static org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.GET_LIVE_STREAMS_ACTION;
import static org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.LOGIN_ACTION;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.action.ActionBuilder;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.hockeystreams.model.HockeyStreamsModel;
import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.model.ModelListener;
import org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.GetLiveStreamsContext;
import org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.IpExectionsContext;
import org.mbaum.hockeystreams.net.hockeystreams.action.HockeyStreamsApiActions.LoginContext;
import org.mbaum.hockeystreams.view.HockeyStreamsActionPanel;
import org.mbaum.hockeystreams.view.LoginPanel;
import org.mbaum.hockeystreams.view.ProgressPanel;

public class ServiioHockeyStreamsConsole
{
	private static final int CONSOLE_MIN_HEIGHT = 120;

	private HockeyStreamsComponent mHockeyStremsComponent;

    public ServiioHockeyStreamsConsole()
	{
        mHockeyStremsComponent = new HockeyStreamsComponent();
        buildAndShowConsole();
	}

	private void buildAndShowConsole()
    {
	    JFrame frame = createFrame();
	    frame.pack();
	    frame.setVisible( true );
    }

	private JFrame createFrame()
	{
	    JFrame frame = new JFrame( "Serviio HockeyStreams Console" );
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    JComponent hockeyStreamsPanel = mHockeyStremsComponent.getComponent();
	    frame.setContentPane( hockeyStreamsPanel );
	    frame.setMinimumSize( new Dimension( hockeyStreamsPanel.getPreferredSize().width,
	                                         CONSOLE_MIN_HEIGHT ) );
	    return frame;
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
        private final ExecutableProcess mLoginAction;
        private final JComponent mPanel;
        private final ExecutableProcess mIpExceptionAction;
        private final ExecutableProcess mGetLiveStreamsAction;
        private final ExecutorService mHockeyStreamsExecutor;
		
		public HockeyStreamsComponent()
		{
			mLoginPanelModel = new LoginPanelModel();
			mHockeyStreamsModel = new HockeyStreamsModel();
			mHockeyStreamsExecutor = Executors.newFixedThreadPool( 1 );
			
            mLoginAction = 
                createExecutableProcess( LOGIN_ACTION,
                                         createLoginContext( mHockeyStreamsModel, 
                                                             mLoginPanelModel ) );
            mIpExceptionAction = 
                createExecutableProcess( GENERATE_IP_EXCEPTION_ACTION, 
                                         createIpExceptionContext( mHockeyStreamsModel ) );
            mGetLiveStreamsAction = 
                createExecutableProcess( GET_LIVE_STREAMS_ACTION, 
                                         createGetLiveContext( mHockeyStreamsModel ) );
            
			mPanel = buildGui( mLoginPanelModel, 
			                   mHockeyStreamsModel, 
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
			panel.add( new ProgressPanel().getComponent(), BorderLayout.SOUTH );
			
			return panel;
		}
        
        private static Action createLoginAction( ExecutableProcess loginActionExecutable,
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

    private static Action createIpExceptionAction( ExecutableProcess ipExceptionAction, HockeyStreamsModel model, Executor executor )
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
    
    private static Action createGetLiveStreamsAction( ExecutableProcess getLiveStreamsAction, 
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
}
