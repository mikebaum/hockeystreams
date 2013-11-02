package org.mbaum.common.execution;

public class ProcessListenerWrapper<R> implements ProcessListener<R>
{
    private final Invoker mInvoker;
    private ProcessListener<R> mListener;
    
    public ProcessListenerWrapper( Invoker invoker, ProcessListener<R> listener )
    {
        mInvoker = invoker;
        mListener = listener;
    }
    
    @Override
    public void processStarted()
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.processStarted();
            }
        } );
    }

    @Override
    public void processSucceeded( final R result )
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.processSucceeded( result );
            }
        } );
    }

    @Override
    public void processFailed( final Exception exception )
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.processFailed( exception );
            }
        } );
    }

    @Override
    public void processFinished()
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.processFinished();
            }
        } );
    }
}
