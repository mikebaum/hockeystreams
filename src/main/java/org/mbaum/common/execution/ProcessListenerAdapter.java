package org.mbaum.common.execution;

public class ProcessListenerAdapter<R> implements ProcessListener<R>
{
    @Override
    public void handleStarted() {}

    @Override
    public void handleResult( R result ) {}

    @Override
    public void handleFailed( Exception exception ) {}
    
    @Override
    public void handleFinished() {}
}