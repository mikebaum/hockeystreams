package org.mbaum.serviio;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.action.ActionUtils.createAction;
import static org.mbaum.common.action.ActionUtils.createActionModel;
import static org.mbaum.common.execution.ProgressPanelExecutor.createProgressPanelExecutor;
import static org.mbaum.common.veto.Vetoers.createVetoer;
import static org.mbaum.serviio.net.ServiioApiActions.ACTION_PROCESS;
import static org.mbaum.serviio.net.ServiioApiActions.PING_PROCESS;
import static org.mbaum.serviio.net.ServiioApiActions.REPOSITORY_PROCESS;
import static org.mbaum.serviio.net.ServiioApiActions.UPDATE_REPOSITORY_PROCESS;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.mbaum.common.action.ActionExecutable;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessListener.ProcessListenerAdapter;
import org.mbaum.common.model.ModelValidator;
import org.mbaum.common.model.ProgressPanelModelImpl;
import org.mbaum.common.view.ActionPanel;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.ActionContext;
import org.mbaum.serviio.net.ServiioApiActions.PingContext;
import org.mbaum.serviio.net.ServiioApiActions.RepositoryContext;
import org.mbaum.serviio.net.transferobject.PingResponse;
import org.mbaum.serviio.net.transferobject.RepositoryResponse;
import org.mbaum.serviio.net.transferobject.ServiioAction;
import org.mbaum.serviio.view.ServiioPanel;

import com.google.common.collect.Lists;

public class ServiioComponent
{
	private static final Logger LOGGER = Logger.getLogger( ServiioComponent.class );
	
	private final ServiioModel mServiioModel;
	private final JComponent mComponent;
	private final JFrame mFrame;
	private final ExecutableProcess<PingResponse> mPingAction;
	private final ExecutableProcess<RepositoryResponse> mRepositoryProcess;
	private final ExecutableProcess<PingResponse> mUpdateRepositoryProcess;
	private final ProgressPanelModelImpl mProgressPanelModel;
	private final ProcessExecutorService mServiioProcessExecutor;
    private final ActionExecutable mPingActionExecutable;
	private final ActionExecutable mRepositoryActionExecutable;
	private final ActionExecutable mUpdateRepositoryActionExecutable;

	private final ExecutableProcess<PingResponse> mRefreshRepoActionExecutable;

	public ServiioComponent( JFrame frame )
	{
		mFrame = frame;
		mServiioModel = new ServiioModel();
		mProgressPanelModel = new ProgressPanelModelImpl();
		mServiioProcessExecutor = createProgressPanelExecutor( mProgressPanelModel );

		mPingAction = mServiioProcessExecutor.buildExecutableProcess( PING_PROCESS, 
		                                                              createPingContext( mServiioModel ), 
		                                                              createPingProcessListener() );
		
		mPingActionExecutable = buildActionExecutable( mPingAction, 
													   createActionModel( createVetoer( mServiioModel, 
															                            createServiioModelValidator() ) ),
						                               mServiioProcessExecutor );
		
		mRepositoryProcess = mServiioProcessExecutor.buildExecutableProcess( REPOSITORY_PROCESS, 
																			 createRepositoryContext( mServiioModel ), 
																			 createRepositoryProcessListener( mServiioModel ) );

		
		mRepositoryActionExecutable = buildActionExecutable( mRepositoryProcess, 
				   											 createActionModel( createVetoer( mServiioModel, 
				   													 						  createServiioModelValidator() ) ),
				   										     mServiioProcessExecutor );
		
		mRefreshRepoActionExecutable = mServiioProcessExecutor.buildExecutableProcess( ACTION_PROCESS, 
																					   createActionContext( mServiioModel ), 
																					   createPingProcessListener() );
		
		mUpdateRepositoryProcess = mServiioProcessExecutor.buildExecutableProcess( UPDATE_REPOSITORY_PROCESS, 
				 																   createRepositoryContext( mServiioModel ), 
				 																   createUpdateRepositoryProcessListener( mRefreshRepoActionExecutable ) );


		mUpdateRepositoryActionExecutable = buildActionExecutable( mUpdateRepositoryProcess, 
																   createActionModel( createVetoer( mServiioModel, 
																		   							createServiioModelValidator() ) ),
																   mServiioProcessExecutor );
		
        mComponent = buildGui( mServiioModel, mPingActionExecutable, mRepositoryActionExecutable, mUpdateRepositoryActionExecutable );
	}

	private ActionContext createActionContext( ServiioModel serviioModel )
    {
//		List<OnlineRepository> onlineRepositories = serviioModel.getRepositoryResponse().getOnlineRepositories();
//		
//		String id = onlineRepositories.get( onlineRepositories.size() - 1 ).getId().toString();
		
	    return new ActionContext( new ServiioAction( "forceOnlineResourceRefresh", Lists.newArrayList( "39" ) ), serviioModel );
    }

	public JComponent getComponent()
	{
		return mComponent;
	}

	private static ProcessListener<PingResponse> createPingProcessListener()
	{
		return new ProcessListenerAdapter<PingResponse>()
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
	
	private static ProcessListener<RepositoryResponse> createRepositoryProcessListener( final ServiioModel model )
	{
		return new ProcessListenerAdapter<RepositoryResponse>()
		{
			@Override
			public void processSucceeded( RepositoryResponse result )
			{
				LOGGER.info( "repository: " + result );
				model.setRepositoryResponse( result );
			}
			
			@Override
			public void processFailed( Exception exception )
			{
				LOGGER.error( "Failed to get repositories", exception );
			}
		};
	}
	
	private static ProcessListener<PingResponse> createUpdateRepositoryProcessListener( final ExecutableProcess<PingResponse> refreshRepoExecutable )
	{
		return new ProcessListenerAdapter<PingResponse>()
		{
			@Override
			public void processSucceeded( PingResponse result )
			{
				LOGGER.info( " update repository: " + result );
				//refreshRepoExecutable.execute();
			}
			
			@Override
			public void processFailed( Exception exception )
			{
				LOGGER.error( "Failed to get repositories", exception );
			}
		};
	}
	
	private static RepositoryContext createRepositoryContext( final ServiioModel serviioModel )
	{
		return new RepositoryContext()
		{
			@Override
			public ServiioModel getServiioModel()
			{
				return serviioModel;
			}
		};
	}

	private static JComponent buildGui( ServiioModel serviioModel, 
									    ActionExecutable pingActionExecutable,
									    ActionExecutable repositoryActionExecutable, 
									    ActionExecutable updateRepositoryActionExecutable )
	{
		JPanel serviioPanel = new JPanel( new BorderLayout() );
		
		Action pingAction = createAction( pingActionExecutable, "Ping" );
		Action repositoryAction = createAction( repositoryActionExecutable, "Repository" );
		Action updateRepositoryAction = createAction( updateRepositoryActionExecutable, "Update Repository" );
		

		serviioPanel.add( new ServiioPanel( serviioModel ).getComponent(), BorderLayout.NORTH );
		serviioPanel.add( new ActionPanel( pingAction, repositoryAction, updateRepositoryAction ).getComponent(), BorderLayout.CENTER );

		return serviioPanel;
	}

	private static ModelValidator<ServiioModel> createServiioModelValidator()
	{
		return new ModelValidator<ServiioModel>()
        {
            @Override
            public boolean isValid( ServiioModel model )
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
