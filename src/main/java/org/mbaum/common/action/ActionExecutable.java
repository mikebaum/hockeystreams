package org.mbaum.common.action;

public interface ActionExecutable
{
    void execute();
    
    boolean isEnabled();
    
    void setListener( ActionExecutableListener listener );
}
