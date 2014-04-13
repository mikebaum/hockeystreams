package org.mbaum.serviio.net;

import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.model.Model;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.transferobject.ServiioAction;

public class ActionContext implements ProcessContext
{
	private final ServiioAction mAction;
	private final Model<ServiioModel> mServiioModel;
	
	public ActionContext( ServiioAction action, Model<ServiioModel> serviioModel )
    {
	    mAction = action;
		mServiioModel = serviioModel;
    }
	
	public ServiioAction getAction()
    {
	    return mAction;
    }
	
	public Model<ServiioModel> getServiioModel()
    {
	    return mServiioModel;
    }
}
