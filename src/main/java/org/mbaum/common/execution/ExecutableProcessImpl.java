package org.mbaum.common.execution;

import java.util.List;

import com.google.common.collect.Lists;


class ExecutableProcessImpl<C extends ProcessContext, R> implements ExecutableProcess<R>
{
    private final Process<C, R> mProcess;
    private final C mContext;
    private final ProcessListenerSupport<R> mProcessListeners;
    
    private ExecutableProcessImpl( Process<C, R> process, C context )
    {
        mProcess = process;
        mContext = context;
        mProcessListeners = new ProcessListenerSupport<R>();
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
    public boolean canExecute()
    {
        return mProcess.canExecuteWith( mContext );
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
        
        public ProcessListenerSupport()
        {
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
                    for( ProcessListener<R> listener : Lists.newArrayList( mListeners ) )
                        listener.processStarted();
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
                        listener.processSucceeded( result );
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
                        listener.processFailed( exception );
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
                        listener.processFinished();
                }
            } );
        }
    }
}
