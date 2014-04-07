package org.mbaum.common.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;


public class ProgressPanelModelImpl extends AbstractModel<ProgressPanelModel.Id<?>, ProgressPanelModel> implements ProgressPanelModel
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
    protected ListenableSupport<ProgressPanelModel, Listener<ProgressPanelModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (ProgressPanelModel) this );
    }
}
