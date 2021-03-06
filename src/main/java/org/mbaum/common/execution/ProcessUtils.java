package org.mbaum.common.execution;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.mbaum.common.Invoker;


public class ProcessUtils
{
    private static final Logger LOGGER = Logger.getLogger( ProcessUtils.class );
    
    public static <C extends ProcessContext, R> ExecutableProcess<R> createExecutableProcess( Process<C, R> process,
    																					      C context,
                                                                                              ProcessListener<R> listener )
    {
        return ExecutableProcessImpl.createExecutableProcess( process, context, listener );
    }
    
    public static <R> Runnable createExecutableProcessRunnable( final ExecutableProcess<R> executableProcess )
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
