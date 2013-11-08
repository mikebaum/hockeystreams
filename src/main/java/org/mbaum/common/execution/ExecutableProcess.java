package org.mbaum.common.execution;

public interface ExecutableProcess<R>
{
    void execute();
    
    void addListener( ProcessListener<R> listener );
    
    void removeListener( ProcessListener<R> listener );
}
