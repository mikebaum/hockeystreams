package org.mbaum.hockeystreams.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;

public class LoginPanelModelImpl extends AbstractModel<LoginPanelModel> implements LoginPanelModel
{
	public LoginPanelModelImpl()
    {
		super();
		newModelValue( USERNAME, "mbaum", "Username", this );
		newModelValue( PASSWORD, "", "Pasword", this );
		newModelValue( API_KEY, "1dd7bceb51c69ba4190a5be6d59ee41e", "Api Key", this );
    }
	
	@Override
    protected ListenableSupport<LoginPanelModel, Listener<LoginPanelModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (LoginPanelModel) this );
    }
}