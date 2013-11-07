package org.mbaum.serviio;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.execution.ProgressPanelExecutor.createProgressPanelExecutor;
import static org.mbaum.serviio.net.ServiioApiActions.PING_PROCESS;

import java.awt.BorderLayout;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mbaum.common.action.ActionBuilder;
import org.mbaum.common.action.ActionEnabler;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.model.ModelListener;
import org.mbaum.common.model.ProgressPanelModel;
import org.mbaum.common.view.ActionPanel;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.ServiioApiActions.PingContext;
import org.mbaum.serviio.net.transferobject.PingResponse;

public class ServiioComponent
{
	private final ServiioModel mServiioModel;
	private final JComponent mComponent;
	private final JFrame mFrame;
	private final ExecutableProcess<PingResponse> mPingAction;
	private final ProgressPanelModel mProgressPanelModel;
	private final ProcessExecutorService mServiioProcessExecutor;

	public ServiioComponent( JFrame frame )
	{
		mFrame = frame;
		mServiioModel = new ServiioModel();
		mProgressPanelModel = new ProgressPanelModel();
		mServiioProcessExecutor = createProgressPanelExecutor( mProgressPanelModel );

		mPingAction = mServiioProcessExecutor.buildExecutableProcess( PING_PROCESS, 
				createPingContext( mServiioModel ), 
				createPigProcessListener() );



		mComponent = buildGui( mServiioModel, createPingAction( mPingAction, mServiioModel, mServiioProcessExecutor ) );
	}

	public JComponent getComponent()
	{
		return mComponent;
	}

	private static ProcessListener<PingResponse> createPigProcessListener()
	{
		return new ProcessListener.ProcessListenerAdapter<PingResponse>()
				{
			@Override
			public void processSucceeded( PingResponse result )
			{
				super.processSucceeded( result );
			}			
				};
	}

	private static PingContext createPingContext( final ServiioModel serviioModel )
	{
		return new PingContext()
		{
			@Override
			public ServiioModel getServiioModel()
			{
				return serviioModel;
			}
		};
	}

	private static JComponent buildGui( ServiioModel serviioModel, Action pingAction )
	{
		JPanel serviioPanel = new JPanel( new BorderLayout() );

		serviioPanel.add( new ServiioPanel( serviioModel ).getComponent(), BorderLayout.NORTH );
		serviioPanel.add( new ActionPanel( pingAction ).getComponent(), BorderLayout.CENTER );

		return serviioPanel;
	}

	private static Action createPingAction( ExecutableProcess<PingResponse> pingExecutable,
											ServiioModel model,
											Executor executor )
	{
		final ActionEnabler<ServiioModel> actionEnabler = createPingActionEnabler();
		final Action action = ActionBuilder.Builder( buildActionExecutable( pingExecutable, 
																			executor ) )
										   .setButtonText( "Ping" )
										   .setInitiallyEnabled( actionEnabler.isEnabled( model ) )
										   .build();

		model.addListener( new ModelListener<ServiioModel>()
		{
			@Override
			public void modelChanged( ServiioModel model )
			{
				action.setEnabled( actionEnabler.isEnabled( model ) );
			}
		} );

		return action;
	}
	
	private static ActionEnabler<ServiioModel> createPingActionEnabler()
	{
		return new ActionEnabler<ServiioModel>()
		{
			@Override
			public boolean isEnabled( ServiioModel model )
			{
				if ( StringUtils.isBlank( model.getHostName() ) )
					return false;
				
				if ( StringUtils.isBlank( model.getPort() ) )
					return false;
				
				return NumberUtils.isNumber( model.getPort() );
			}
		};
	}
}
