package org.mbaum.common.execution;

import org.apache.log4j.Logger;

public interface ProcessListener<R>
{
    void processStarted();
    
    void processSucceeded( R result );
    
    void processFailed( Exception exception );
    
    void processFinished();
    
    public static class ProcessListenerAdapter<R> implements ProcessListener<R>
    {
        @Override
        public void processStarted() {}

        @Override
        public void processSucceeded( R result ) {}

        @Override
        public void processFailed( Exception exception ) {}
        
        @Override
        public void processFinished() {}
    }
    
    public static class LoggingProcessListener<R> implements ProcessListener<R>
    {
        private final Logger mLogger;
        private final String mDescription;
        
        public LoggingProcessListener( Logger logger, String description )
        {
            mLogger = logger;
            mDescription = description;
        }
        
        @Override
        public void processStarted()
        {
            mLogger.info( "Process [" + mDescription + "] started" );
        }
        
        @Override
        public void processSucceeded( R result )
        {
            mLogger.info( "Process [" + mDescription + "] succeeded" );
        }
        
        @Override
        public void processFailed( Exception exception )
        {
            mLogger.error( "Process [" + mDescription + "] failed, reason: " + exception );
        }
        
        @Override
        public void processFinished()
        {
            mLogger.info( "Process [" + mDescription + "] finished" );
        }
    }
}
