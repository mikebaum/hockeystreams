package org.mbaum.hockeystreams.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;

public class HockeyStreamsModelImpl extends AbstractModel<HockeyStreamsModel> implements HockeyStreamsModel
{
    public HockeyStreamsModelImpl()
    {
    	super();
        newModelValue( HockeyStreamsModel.SESSION_TOKEN, "", "Token", this );
    }
    
    @Override
    protected ListenableSupport<HockeyStreamsModel, Listener<HockeyStreamsModel>> createListenableSupport()
    {
        return createModelListenerSupport( (HockeyStreamsModel) this );
    }
}
