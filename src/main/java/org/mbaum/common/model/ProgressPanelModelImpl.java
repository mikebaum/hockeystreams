package org.mbaum.common.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;


public class ProgressPanelModelImpl extends AbstractModel<ProgressPanelModel> implements ProgressPanelModel
{
	private static boolean DEFAULT_INDETERMINANT = false;
	private static String DEFAULT_MESSAGE = "";
	
	public ProgressPanelModelImpl()
    {
		super();
		newModelValue( MESSAGE, DEFAULT_MESSAGE, "Message", this );
		newModelValue( INDETERMINATE, DEFAULT_INDETERMINANT, "Indeterminate", this );
    }
	
	@Override
    protected ListenableSupport<ProgressPanelModel, Listener<ProgressPanelModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (ProgressPanelModel) this );
    }
}
