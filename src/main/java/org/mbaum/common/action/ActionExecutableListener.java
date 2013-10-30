package org.mbaum.common.action;

import org.apache.log4j.Logger;

public interface ActionExecutableListener
{
    void actionEnabledChanged();
    
    
    public static final ActionExecutableListener WARNING_LISTENER = new ActionExecutableListener()
    {
        private final Logger mLogger = Logger.getLogger( ActionExecutableListener.class );
        
        @Override
        public void actionEnabledChanged()
        {
            mLogger.warn( "No listener attached to action executable. This is probably an error." );
        }
    };
}
