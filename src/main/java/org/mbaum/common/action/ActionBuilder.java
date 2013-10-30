package org.mbaum.common.action;

import static org.mbaum.common.action.ActionUtils.createAction;

import javax.swing.Action;
import javax.swing.Icon;

public class ActionBuilder
{
    private final ActionExecutable mActionExecutable;
    private String mActionText = null;
    private Icon mIcon = null;
    private boolean mEnabled = false;

    private ActionBuilder( ActionExecutable actionExecutable )
    {
        mActionExecutable = actionExecutable;
    }

    public ActionBuilder setButtonText( String buttonText )
    {
        mActionText = buttonText;
        return this;
    }

    public ActionBuilder setIcon( Icon icon )
    {
        mIcon = icon;
        return this;
    }

    public ActionBuilder setInitiallyEnabled( boolean isEnabledInitially )
    {
        mEnabled = isEnabledInitially;
        return this;
    }
    
    public Action build()
    {
        Action action = createAction( mActionExecutable, mActionText, mIcon );
        action.setEnabled( mEnabled );
        return action;
    }
    
    public static ActionBuilder Builder( ActionExecutable actionExecutable )
    {
        return new ActionBuilder( actionExecutable );
    }
}
