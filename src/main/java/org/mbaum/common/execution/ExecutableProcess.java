package org.mbaum.common.execution;

public interface ExecutableProcess
{
    void execute();
    
    boolean canExecute();
    
    void addListener( ProcessListener listener );
    
    void removeListener( ProcessListener listener );
}
