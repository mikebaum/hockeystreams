package org.mbaum.common.execution;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;


public class ProcessUtils
{
    private static final Logger LOGGER = Logger.getLogger( ProcessUtils.class );
    
    public static <C extends ProcessContext> ExecutableProcess createExecutableProcess( final Process<C> process,
                                                                                        final C context,
                                                                                        final ProcessListener... listeners )
    {
        return ExecutableProcessImpl.createExecutableProcess( process, context, listeners );
    }
    
    public static Runnable createExecutbleProcessRunnable( final ExecutableProcess executableProcess )
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                executableProcess.execute();
            }
        };
    }

    public static Invoker getInvoker()
    {
        if ( SwingUtilities.isEventDispatchThread() )
            return Invoker.EDT_INVOKER;
        
        LOGGER.warn( "Could not find a gui event thread, returning a synchronous invoker. " +
                     "This is probably an error." );
        
        return Invoker.SYNCHRONOUS_INVOKER;
    }
}
