package org.mbaum.hockeystreams.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.MutableModelValue;

public class HockeyStreamsModelImpl extends AbstractModel<HockeyStreamsModel.Id<?>, HockeyStreamsModel> implements HockeyStreamsModel
{
    public HockeyStreamsModelImpl()
    {
    	super();
        newModelValue( HockeyStreamsModel.SESSION_TOKEN, "", "Token", this );
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
    protected ListenableSupport<HockeyStreamsModel, Listener<HockeyStreamsModel>> createListenableSupport()
    {
        return createModelListenerSupport( (HockeyStreamsModel) this );
    }
}
