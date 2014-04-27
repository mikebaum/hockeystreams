package org.mbaum.common.execution;

import org.mbaum.common.Invoker;

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
    public void handleStarted()
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.handleStarted();
            }
        } );
    }

    @Override
    public void handleResult( final R result )
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.handleResult( result );
            }
        } );
    }

    @Override
    public void handleFailed( final Exception exception )
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.handleFailed( exception );
            }
        } );
    }

    @Override
    public void handleFinished()
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.handleFinished();
            }
        } );
    }
}
