package org.mbaum.common;

import javax.swing.SwingUtilities;

public interface Invoker
{
    void invoke( Runnable runnable );
    
    public static Invoker SYNCHRONOUS_INVOKER = new Invoker()
    {
        @Override
        public void invoke( Runnable runnable )
        {
            runnable.run();
        }
    };
    
    public static Invoker EDT_INVOKER = new Invoker()
    {
        @Override
        public void invoke( Runnable runnable )
        {
            SwingUtilities.invokeLater( runnable );
        }
    };
}
