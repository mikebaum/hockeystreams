package org.mbaum.common.execution;

import org.mbaum.common.Destroyable;

public interface ExecutableProcess<R> extends Destroyable
{
    void execute();
    
    void addListener( ProcessListener<R> listener );
    
    void removeListener( ProcessListener<R> listener );
}
