package org.mbaum.common.action;

import static org.mbaum.common.action.ActionExecutableImpl.createActionExecutable;

import java.awt.event.ActionEvent;
import java.util.concurrent.Executor;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.veto.Vetoer;

public final class ActionUtils
{
    private ActionUtils(){}
    
    public static ActionExecutable buildActionExecutable( ExecutableProcess<?> executableProcess, 
                                                          ActionModel actionModel, 
                                                          Executor executor )
    {
        return createActionExecutable( executableProcess, actionModel, executor );
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
        
        action.setEnabled( executable.isEnabled() );
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
    
    public static ActionModel createActionModel( Vetoer... vetoers )
    {
        ActionModel actionModel = new ActionModelImpl();
        
        for ( Vetoer vetoer : vetoers )
            actionModel.addVetoer( vetoer );
        
        return actionModel;
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
