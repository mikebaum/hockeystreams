package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.ModelValue;

public class HockeyStreamsModelImpl extends AbstractModel<HockeyStreamsModel> implements HockeyStreamsModel
{
    private final ModelValue<String> mToken;
    
    public HockeyStreamsModelImpl()
    {
    	mToken = newModelValue( "" );
    }

    public void setSessionToken( String token )
    {
        mToken.set( token );
    }
    
    public String getToken()
    {
        return mToken.get();
    }
}
