package org.mbaum.common.execution;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mbaum.common.model.ProgressPanelModel;

public class ProgressPanelExecutor implements ProcessExecutorService
{
	private final ProgressPanelModel mProgressPanelModel;
	private final ExecutorService mExecutorService;

	private ProgressPanelExecutor( ProgressPanelModel progressPanelModel, ExecutorService executorService )
	{
		mProgressPanelModel = progressPanelModel;
		mExecutorService = executorService;
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
		buildExecutableProcess( final Process<C, R> process, 
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
		createDefaultResultHandler( final ProgressPanelModel progressPanelModel, 
									final Process<C, R> process )
	{
		return new ResultHandler<R>()
		{
			@Override
			public void handleResult( R result )
			{
				progressPanelModel.setMessage( process.getDescription() + " complete" );
			}
		};
	}
	
	private static <R> ProcessListener<R> createProcessListener( final ProgressPanelModel progressPanelModel, 
																 final String processDescription,
																 final ResultHandler<R> resultHander )
	{
		return new ProcessListener<R>()
		{
			@Override
			public void processStarted()
			{
				progressPanelModel.setMessage( "Performing: " + processDescription );
				progressPanelModel.setIndeterminant( true );
			}

			@Override
			public void processSucceeded( R result )
			{
				resultHander.handleResult( result );
			}

			@Override
			public void processFailed( Exception exception )
			{
				progressPanelModel.setMessage( processDescription + " failed" );
			}

			@Override
			public void processFinished()
			{
				progressPanelModel.reset();
			}
		};
	}

	public static ProcessExecutorService createProgressPanelExecutor( ProgressPanelModel progressPanelModel )
	{
		return new ProgressPanelExecutor( progressPanelModel, newFixedThreadPool( 1 ) );
	}
}
