package org.mbaum.common.execution;


public interface ProcessListener<R>
{
    void handleStarted();
    
    void handleResult( R result );
    
    void handleFailed( Exception exception );
    
    void handleFinished();
}
