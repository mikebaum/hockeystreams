package org.mbaum.common.execution;

public class ProcessListenerWrapper implements ProcessListener
{
    private final Invoker mInvoker;
    private ProcessListener mListener;
    
    public ProcessListenerWrapper( Invoker invoker, ProcessListener listener )
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
    public void processSucceeded()
    {
        mInvoker.invoke( new Runnable()
        {
            @Override
            public void run()
            {
                mListener.processSucceeded();
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
