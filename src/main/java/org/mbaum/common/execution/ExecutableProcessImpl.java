package org.mbaum.common.execution;

import java.util.List;

import com.google.common.collect.Lists;


class ExecutableProcessImpl<C extends ProcessContext> implements ExecutableProcess
{
    private final Process<C> mProcess;
    private final C mContext;
    private final ProcessListenerSupport mProcessListeners;
    
    private ExecutableProcessImpl( Process<C> process, C context )
    {
        mProcess = process;
        mContext = context;
        mProcessListeners = new ProcessListenerSupport();
    }
    
    @Override
    public void execute()
    {
        mProcessListeners.fireProcessStared();
        
        try
        {                    
            mProcess.execute( mContext );
            mProcessListeners.fireProcessSucceeded();
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
    public void addListener( ProcessListener listener )
    {
        mProcessListeners.addListener( listener );
    }
    
    @Override
    public void removeListener( ProcessListener listener )
    {
        mProcessListeners.removeListener( listener );
    }
    
    static <C extends ProcessContext> ExecutableProcess createExecutableProcess( Process<C> process, 
                                                                                 C context, 
                                                                                 ProcessListener... listeners )
    {
        ExecutableProcessImpl<C> executableProcess = new ExecutableProcessImpl<C>( process, context );
        
        for ( ProcessListener listener : listeners )
            executableProcess.addListener( listener );
        
        return executableProcess;
    }
    
    private static class ProcessListenerSupport
    {
        private final List<ProcessListener> mListeners = Lists.newArrayList();
        private final Invoker mInvoker;
        
        public ProcessListenerSupport()
        {
            mInvoker = ProcessUtils.getInvoker();
        }
        
        public void addListener( ProcessListener listener )
        {
            mListeners.add( listener );
        }
        
        public void removeListener( ProcessListener listener )
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
                    for( ProcessListener listener : Lists.newArrayList( mListeners ) )
                        listener.processStarted();
                }
            } );
        }
        
        private void fireProcessSucceeded()
        {
            mInvoker.invoke( new Runnable()
            {
                @Override
                public void run()
                {
                    for( ProcessListener listener : Lists.newArrayList( mListeners ) )
                        listener.processSucceeded();
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
                    for( ProcessListener listener : Lists.newArrayList( mListeners ) )
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
                    for( ProcessListener listener : Lists.newArrayList( mListeners ) )
                        listener.processFinished();
                }
            } );
        }
    }
}
