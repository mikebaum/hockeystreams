package org.mbaum.hockeystreams.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.MutableModelValue;

public class LoginPanelModelImpl extends AbstractModel<LoginPanelModel.Id<?>, LoginPanelModel> implements LoginPanelModel
{
	public LoginPanelModelImpl()
    {
		super();
		newModelValue( USERNAME, "mbaum", "Username", this );
		newModelValue( PASSWORD, "", "Pasword", this );
		newModelValue( API_KEY, "1dd7bceb51c69ba4190a5be6d59ee41e", "Api Key", this );
    }
	
	public <T> MutableModelValue<T> getModelValue( Id<T> id )
	{
	    return super.getModelValue( id );
	}
	
	@Override
	public <T> T getValue( Id<T> id )
	{
	    return getModelValue( id ).get();
	}
	
	@Override
	public <T> void setValue( Id<T> id, T value )
	{
		getModelValue( id ).set( value );
	}

	@Override
    protected ListenableSupport<LoginPanelModel, Listener<LoginPanelModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (LoginPanelModel) this );
    }
}