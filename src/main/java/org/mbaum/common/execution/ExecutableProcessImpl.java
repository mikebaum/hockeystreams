package org.mbaum.common.execution;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;


class ExecutableProcessImpl<C extends ProcessContext, R> implements ExecutableProcess<R>
{
	private static final Logger LOGGER = Logger.getLogger( ExecutableProcessImpl.class ); 
	
    private final Process<C, R> mProcess;
    private final C mContext;
    private final ProcessListenerSupport<R> mProcessListeners;
    
    private ExecutableProcessImpl( Process<C, R> process, C context )
    {
        mProcess = process;
        mContext = context;
        mProcessListeners = new ProcessListenerSupport<R>( process.getDescription() );
    }
    
    @Override
    public void execute()
    {
        mProcessListeners.fireProcessStared();
        
        try
        {                    
            R restult = mProcess.execute( mContext );
            mProcessListeners.fireProcessSucceeded( restult );
        }
        catch( Exception exception )
        {
            mProcessListeners.fireProcessFailed( exception );
        }
        finally
        {
            mProcessListeners.fireProcessFinished();
        }
    }

    @Override
    public void addListener( ProcessListener<R> listener )
    {
        mProcessListeners.addListener( listener );
    }
    
    @Override
    public void removeListener( ProcessListener<R> listener )
    {
        mProcessListeners.removeListener( listener );
    }
    
    static <C extends ProcessContext, R> ExecutableProcess<R> createExecutableProcess( Process<C, R> process, 
                                                                                 	   C context, 
                                                                                 	   ProcessListener<R> listener )
    {
        ExecutableProcessImpl<C, R> executableProcess = new ExecutableProcessImpl<C, R>( process, context );
        executableProcess.addListener( listener );
        
        return executableProcess;
    }
    
    private static class ProcessListenerSupport<R>
    {
        private final List<ProcessListener<R>> mListeners = Lists.newArrayList();
        private final Invoker mInvoker;
		private final String mDescription;
        
        public ProcessListenerSupport( String description )
        {
            mDescription = description;
			mInvoker = ProcessUtils.getInvoker();
        }
        
        public void addListener( ProcessListener<R> listener )
        {
            mListeners.add( listener );
        }
        
        public void removeListener( ProcessListener<R> listener )
        {
            mListeners.remove( listener );
        }
        
        private void fireProcessStared()
        {
            mInvoker.invoke( new Runnable()
            {
                @Override
                public void run()
                {
                	LOGGER.info( "Process [" + mDescription + "] started" );
                    for( ProcessListener<R> listener : Lists.newArrayList( mListeners ) )
                        listener.handleStarted();
                }
            } );
        }
        
        private void fireProcessSucceeded( final R result )
        {
            mInvoker.invoke( new Runnable()
            {
                @Override
                public void run()
                {
                    for( ProcessListener<R> listener : Lists.newArrayList( mListeners ) )
                        listener.handleResult( result );
                }
            } );
        }
        
        private void fireProcessFailed( final Exception exception )
        {
            mInvoker.invoke( new Runnable()
            {
                @Override
                public void run()
                {
                    for( ProcessListener<R> listener : Lists.newArrayList( mListeners ) )
                        listener.handleFailed( exception );
                }
            } );
        }
        
        private void fireProcessFinished()
        {
            mInvoker.invoke( new Runnable()
            {
                @Override
                public void run()
                {
                    for( ProcessListener<R> listener : Lists.newArrayList( mListeners ) )
                        listener.handleFinished();
                }
            } );
        }
    }
}
