package org.mbaum.serviio.net;

import org.mbaum.common.execution.ProcessContext;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.transferobject.ServiioAction;

public class ActionContext implements ProcessContext
{
	private final ServiioAction mAction;
	private final ServiioModel mServiioModel;
	
	public ActionContext( ServiioAction action, ServiioModel serviioModel )
    {
	    mAction = action;
		mServiioModel = serviioModel;
    }
	
	public ServiioAction getAction()
    {
	    return mAction;
    }
	
	public ServiioModel getServiioModel()
    {
	    return mServiioModel;
    }
}
