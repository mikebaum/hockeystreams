package org.mbaum.common.execution;

import static org.mbaum.common.model.ProgressPanelModel.INDETERMINATE;
import static org.mbaum.common.model.ProgressPanelModel.MESSAGE;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.ProgressPanelModel;

public class ProgressPanelExecutor implements ProcessExecutorService
{
    private static final Logger LOGGER = Logger.getLogger( ProgressPanelExecutor.class );
	
    private final Model<ProgressPanelModel> mProgressPanelModel;
	private final ExecutorService mExecutorService;
    private final String mExecutorName;

	private ProgressPanelExecutor( Model<ProgressPanelModel> progressPanelModel, ExecutorService executorService, String executorName )
	{
		mProgressPanelModel = progressPanelModel;
		mExecutorService = executorService;
        mExecutorName = executorName;
        LOGGER.info( "Created executor thread: " + mExecutorName );
	}
	
	@Override
	public void destroy()
	{
	    LOGGER.info( "Shutting down executor thread: " + mExecutorName );
	    mExecutorService.shutdown();
	    
	    try
        {
	        LOGGER.info( mExecutorName + " waiting for processes to finish." );
	        mExecutorService.awaitTermination( 5, TimeUnit.SECONDS );
        }
        catch ( InterruptedException e )
        {
            LOGGER.error( "Failed to shutdown executor: " + mExecutorName, e );
        }
	}

	@Override
	public void execute( Runnable runnable )
	{
		mExecutorService.execute( runnable );
	}

	@Override
	public boolean awaitTermination( long timeout, TimeUnit unit )
			throws InterruptedException
	{
		return mExecutorService.awaitTermination( timeout, unit );
	}

	@Override
	public <T> List<Future<T>> invokeAll( Collection<? extends Callable<T>> tasks )
			throws InterruptedException
	{
		return mExecutorService.invokeAll( tasks );
	}

	@Override
	public <T> List<Future<T>> invokeAll(
			Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit )
			throws InterruptedException
	{
		return mExecutorService.invokeAll( tasks, timeout, unit );
	}

	@Override
	public <T> T invokeAny( Collection<? extends Callable<T>> tasks )
			throws InterruptedException, ExecutionException
	{
		return mExecutorService.invokeAny( tasks );
	}

	@Override
	public <T> T invokeAny( Collection<? extends Callable<T>> tasks, long timeout,
			TimeUnit unit ) throws InterruptedException, ExecutionException,
			TimeoutException
	{
		return mExecutorService.invokeAny( tasks, timeout, unit );
	}

	@Override
	public boolean isShutdown()
	{
		return mExecutorService.isShutdown();
	}

	@Override
	public boolean isTerminated()
	{
		return mExecutorService.isTerminated();
	}

	@Override
	public void shutdown()
	{
		mExecutorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow()
	{
		return mExecutorService.shutdownNow();
	}

	@Override
	public <T> Future<T> submit( Callable<T> task )
	{
		return mExecutorService.submit( task );
	}

	@Override
	public Future<?> submit( Runnable task )
	{
		return mExecutorService.submit( task );
	}

	@Override
	public <T> Future<T> submit( Runnable task, T result )
	{
		return mExecutorService.submit( task, result );
	}
	
	@Override
	public <C extends ProcessContext, R> ExecutableProcess<R> 
		buildExecutableProcess( Process<C, R> process, 
				                C context, 
				                ProcessListener<R> listener )
	{
		return buildExecutableProcess( process, context, listener, createDefaultResultHandler( mProgressPanelModel, process ) );
	}

	@Override
	public <C extends ProcessContext, R> ExecutableProcess<R> 
		buildExecutableProcess( Process<C, R> process, C context, 
				                ProcessListener<R> listener, 
				                ResultHandler<R> resultHandler )
	{
		ExecutableProcess<R> executableProcess = 
		    ProcessUtils.createExecutableProcess( process, context, listener );
		
		ProcessListener<R> createProcessListener = createProcessListener( mProgressPanelModel, 
																		  process.getDescription(), 
																		  resultHandler );
		executableProcess.addListener( createProcessListener );
		
		return executableProcess;
	}

	private static <R, C extends ProcessContext> ResultHandler<R> 
		createDefaultResultHandler( final Model<ProgressPanelModel> progressPanelModel, 
									final Process<C, R> process )
	{
		return new ResultHandler<R>()
		{
			@Override
			public void handleResult( R result )
			{
				progressPanelModel.setValue( MESSAGE, process.getDescription() + " complete" );
			}
		};
	}
	
	private static <R> ProcessListener<R> createProcessListener( final Model<ProgressPanelModel> progressPanelModel, 
																 final String processDescription,
																 final ResultHandler<R> resultHander )
	{
		return new ProcessListener<R>()
		{
			@Override
			public void handleStarted()
			{
				progressPanelModel.setValue( MESSAGE, "Performing: " + processDescription );
				progressPanelModel.setValue( INDETERMINATE, true );
			}

			@Override
			public void handleResult( R result )
			{
				resultHander.handleResult( result );
			}

			@Override
			public void handleFailed( Exception exception )
			{
				progressPanelModel.setValue( MESSAGE, processDescription + " failed" );
			}

			@Override
			public void handleFinished()
			{
				progressPanelModel.reset();
			}
		};
	}

	public static ProcessExecutorService createProgressPanelExecutor( Model<ProgressPanelModel> progressPanelModel, 
																	  final String executorName )
	{
		ThreadPoolExecutor exector = new ScheduledThreadPoolExecutor( 1, new ThreadFactory()
		{
			@Override
			public Thread newThread( Runnable runnable )
			{
				return new Thread( runnable, executorName );
			}
		} );
		return new ProgressPanelExecutor( progressPanelModel, exector, executorName );
	}
}
