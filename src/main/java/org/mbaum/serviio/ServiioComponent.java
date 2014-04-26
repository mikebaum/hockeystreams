package org.mbaum.serviio;

import static org.mbaum.common.action.ActionUtils.buildActionExecutable;
import static org.mbaum.common.action.ActionUtils.createAction;
import static org.mbaum.common.action.ActionUtils.createActionModel;
import static org.mbaum.common.execution.ProgressPanelExecutor.createProgressPanelExecutor;
import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;
import static org.mbaum.common.veto.Vetoers.createVetoer;
import static org.mbaum.serviio.model.ServiioModel.HOST_NAME;
import static org.mbaum.serviio.model.ServiioModel.PORT;
import static org.mbaum.serviio.model.ServiioModel.REPOSITORY_RESPONSE;
import static org.mbaum.serviio.net.ServiioApiActions.ACTION_PROCESS;
import static org.mbaum.serviio.net.ServiioApiActions.PING_PROCESS;
import static org.mbaum.serviio.net.ServiioApiActions.REPOSITORY_PROCESS;
import static org.mbaum.serviio.net.ServiioApiActions.UPDATE_REPOSITORY_PROCESS;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.mbaum.common.Component;
import org.mbaum.common.Destroyable;
import org.mbaum.common.action.ActionExecutable;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessListenerAdapter;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValidator;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.model.ProgressPanelModel;
import org.mbaum.common.view.ActionPanel;
import org.mbaum.common.view.View;
import org.mbaum.common.view.ViewBuilder;
import org.mbaum.hockeystreams.AbstractComponent;
import org.mbaum.serviio.model.RepositoryModel;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.ActionContext;
import org.mbaum.serviio.net.ServiioApiActions.PingContext;
import org.mbaum.serviio.net.ServiioApiActions.RepositoryContext;
import org.mbaum.serviio.net.transferobject.PingResponse;
import org.mbaum.serviio.net.transferobject.ServiioAction;
import org.mbaum.serviio.view.ServiioPanel;

import com.google.common.collect.Lists;

public class ServiioComponent extends AbstractComponent implements Component
{
	private static final String PING = "PING";
	private static final String UPDATE_REPOSITORY = "UPDATE_REPOSITORY";
	private static final String GET_REPOSITORY = "GET_REPOSITORY";

	private static final Logger LOGGER = Logger.getLogger( ServiioComponent.class );
	
	private final MutableModel<ServiioModel> mServiioModel;
	private final View mView;
	private final ExecutableProcess<PingResponse> mPingAction;
	private final ExecutableProcess<MutableModel<RepositoryModel>> mRepositoryProcess;
	private final ExecutableProcess<PingResponse> mUpdateRepositoryProcess;
	private final MutableModel<ProgressPanelModel> mProgressPanelModel;
	private final ProcessExecutorService mServiioProcessExecutor;
    private final ActionExecutable mPingActionExecutable;
	private final ActionExecutable mRepositoryActionExecutable;
	private final ActionExecutable mUpdateRepositoryActionExecutable;

	private final ExecutableProcess<PingResponse> mRefreshRepoActionExecutable;
	
	public ServiioComponent()
	{
		mServiioModel = d( createMutableModel( ServiioModel.class ) );
		mProgressPanelModel = d( createMutableModel( ProgressPanelModel.class ) );
		mServiioProcessExecutor = createProgressPanelExecutor( mProgressPanelModel, "ServiioRESTApiExecutor" );

		mPingAction = d( mServiioProcessExecutor.buildExecutableProcess( PING_PROCESS, 
		                                                                 createPingContext( mServiioModel ), 
		                                                                 createPingProcessListener() ) );
		
		mPingActionExecutable = d( buildActionExecutable( mPingAction, 
								        				  createActionModel( PING, 
										    			                     createVetoer( mServiioModel, 
											    		                                   createServiioModelValidator() ) ),
						                                  mServiioProcessExecutor ) );
		
		mRepositoryProcess = d( mServiioProcessExecutor.buildExecutableProcess( REPOSITORY_PROCESS, 
																			    createRepositoryContext( mServiioModel ), 
																			    createRepositoryProcessListener( mServiioModel ) ) );

		
		mRepositoryActionExecutable = d( buildActionExecutable( mRepositoryProcess, 
				   						        				createActionModel( GET_REPOSITORY, 
				   								    			                   createVetoer( mServiioModel, 
				   												    	 						 createServiioModelValidator() ) ),
				   										        mServiioProcessExecutor ) );
		
		mRefreshRepoActionExecutable = d( mServiioProcessExecutor.buildExecutableProcess( ACTION_PROCESS, 
																					      createActionContext( mServiioModel ), 
																					      createPingProcessListener() ) );
		
		mUpdateRepositoryProcess = 
		    d( mServiioProcessExecutor.buildExecutableProcess( UPDATE_REPOSITORY_PROCESS, 
		                                                       createRepositoryContext( mServiioModel ), 
		                                                       createUpdateRepositoryProcessListener( mRefreshRepoActionExecutable ) ) );


		mUpdateRepositoryActionExecutable = d( buildActionExecutable( mUpdateRepositoryProcess, 
		                                                              createActionModel( UPDATE_REPOSITORY, 
		                                                                                 createVetoer( mServiioModel, 
		                                                                                               createServiioModelValidator() ) ),
		                                                                                 mServiioProcessExecutor ) );
		
        mView = d( buildView( mServiioModel, mPingActionExecutable, mRepositoryActionExecutable, mUpdateRepositoryActionExecutable ) );
	}
	
	private ActionContext createActionContext( Model<ServiioModel> serviioModel )
    {
//		List<OnlineRepository> onlineRepositories = serviioModel.getRepositoryResponse().getOnlineRepositories();
//		
//		String id = onlineRepositories.get( onlineRepositories.size() - 1 ).getId().toString();
		
	    return new ActionContext( new ServiioAction( "forceOnlineResourceRefresh", Lists.newArrayList( "39" ) ), serviioModel );
    }

	private static ProcessListener<PingResponse> createPingProcessListener()
	{
		return new ProcessListenerAdapter<PingResponse>()
		{
			@Override
			public void handleResult( PingResponse result )
			{
				super.handleResult( result );
			}			
		};
	}

	private static PingContext createPingContext( final Model<ServiioModel> serviioModel )
	{
		return new PingContext()
		{
			@Override
			public Model<ServiioModel> getServiioModel()
			{
				return serviioModel;
			}
		};
	}
	
	private static ProcessListener<MutableModel<RepositoryModel>> createRepositoryProcessListener( final MutableModel<ServiioModel> model )
	{
		return new ProcessListenerAdapter<MutableModel<RepositoryModel>>()
		{
			@Override
			public void handleResult( MutableModel<RepositoryModel> result )
			{
				model.setValue( REPOSITORY_RESPONSE, result );
			}
			
			@Override
			public void handleFailed( Exception exception )
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
			public void handleResult( PingResponse result )
			{
				LOGGER.info( " update repository: " + result );
				//refreshRepoExecutable.execute();
			}
		};
	}
	
	private static RepositoryContext createRepositoryContext( final Model<ServiioModel> serviioModel )
	{
		return new RepositoryContext()
		{
			@Override
			public Model<ServiioModel> getServiioModel()
			{
				return serviioModel;
			}
		};
	}

	private static View buildView( MutableModel<ServiioModel> serviioModel, 
	                               ActionExecutable pingActionExecutable,
	                               ActionExecutable repositoryActionExecutable, 
	                               ActionExecutable updateRepositoryActionExecutable )
	{
		final JPanel serviioPanel = new JPanel( new BorderLayout() );
		
		Action pingAction = createAction( pingActionExecutable, "Ping" );
		Action repositoryAction = createAction( repositoryActionExecutable, "Repository" );
		Action updateRepositoryAction = createAction( updateRepositoryActionExecutable, "Update Repository" );
		

		serviioPanel.add( new ServiioPanel( serviioModel ).getComponent(), BorderLayout.NORTH );
		serviioPanel.add( new ActionPanel( pingAction, repositoryAction, updateRepositoryAction ).getComponent(), BorderLayout.CENTER );

		return new ViewBuilder.ViewImpl( new Destroyable()
		{
			@Override
			public void destroy()
			{
				serviioPanel.removeAll();
			}
		}, serviioPanel );
	}

	private static ModelValidator<ServiioModel> createServiioModelValidator()
	{
		return new ModelValidator<ServiioModel>()
        {
            @Override
            public boolean isValid( Model<ServiioModel> model )
            {
                if ( StringUtils.isBlank( model.getValue( HOST_NAME ) ) )
                    return false;
                
                String port = model.getValue( PORT );
                
				if ( StringUtils.isBlank( port ) )
                    return false;
                
                return NumberUtils.isNumber( port );
            }
        };
	}

	@Override
    public View getView()
    {
	    return mView;
    }
}
