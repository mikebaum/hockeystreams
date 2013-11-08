package org.mbaum.common.action;

import org.mbaum.common.Destroyable;

public interface ActionExecutable extends Destroyable
{
    void execute();
    
    boolean isEnabled();
    
    void setListener( ActionExecutableListener listener );
}
