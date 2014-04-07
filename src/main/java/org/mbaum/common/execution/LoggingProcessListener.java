package org.mbaum.common.execution;

import org.apache.log4j.Logger;

public class LoggingProcessListener<R> implements ProcessListener<R>
{
    private final Logger mLogger;
    private final String mDescription;
	private final ProcessListener<R> mListener;
    
    public LoggingProcessListener( Logger logger, String description, ProcessListener<R> listener )
    {
        mLogger = logger;
        mDescription = description;
		mListener = listener;
    }
    
    @Override
    public void handleStarted()
    {
        mLogger.info( "Process [" + mDescription + "] started" );
        mListener.handleStarted();
    }
    
    @Override
    public void handleResult( R result )
    {
        mLogger.info( "Process [" + mDescription + "] succeeded" );
        mListener.handleResult( result );
    }
    
    @Override
    public void handleFailed( Exception exception )
    {
        mLogger.error( "Process [" + mDescription + "] failed, reason: " + exception );
        mListener.handleFailed( exception );
    }
    
    @Override
    public void handleFinished()
    {
        mLogger.info( "Process [" + mDescription + "] finished" );
        mListener.handleFinished();
    }
}