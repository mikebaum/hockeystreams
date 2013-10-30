package org.mbaum.common.execution;

import org.apache.log4j.Logger;

public interface ProcessListener
{
    void processStarted();
    
    void processSucceeded();
    
    void processFailed( Exception exception );
    
    void processFinished();
    
    public static class ProcessListenerAdapter implements ProcessListener
    {
        @Override
        public void processStarted() {}

        @Override
        public void processSucceeded() {}

        @Override
        public void processFailed( Exception exception ) {}
        
        @Override
        public void processFinished() {}
    }
    
    public static class LoggingProcessListener implements ProcessListener
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
        public void processSucceeded()
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
