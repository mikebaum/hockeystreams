package org.mbaum.common.action;

import java.awt.event.ActionEvent;
import java.util.concurrent.Executor;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import org.mbaum.common.execution.ExecutableProcess;

public final class ActionUtils
{
    private ActionUtils(){}
    
    public static ActionExecutable buildActionExecutable( ExecutableProcess executableProcess, Executor executor )
    {
        return new ActionExecutableImpl( executableProcess, executor );
    }

    @SuppressWarnings("serial")
    public static Action createAction( final ActionExecutable executable, 
                                       String buttonText, 
                                       Icon icon )
    {
        AbstractAction action = new AbstractAction( buttonText, icon )
        {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                executable.execute();
            }
        };
        
        executable.setListener( createActionExecutableListener( action, executable ) );
        
        return action;
    }

    public static Action createAction( ActionExecutable executable, String buttonText )
    {
        return createAction( executable, buttonText, null );
    }

    public static Action createAction( ActionExecutable executable, Icon icon )
    {
        return createAction( executable, null, icon );
    }

    private static ActionExecutableListener createActionExecutableListener( final AbstractAction action,
                                                                            final ActionExecutable actionExecutable )
    {
        return new ActionExecutableListener()
        {
            @Override
            public void actionEnabledChanged()
            {
                action.setEnabled( actionExecutable.isEnabled() );
            }
        };
    }
}
