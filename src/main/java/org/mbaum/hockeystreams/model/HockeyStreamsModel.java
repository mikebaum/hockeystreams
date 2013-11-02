package org.mbaum.hockeystreams.model;

import org.apache.commons.lang3.ObjectUtils;
import org.mbaum.common.model.AbstractModel;

public class HockeyStreamsModel extends AbstractModel<HockeyStreamsModel>
{
    private String mToken;

    public void setSessionToken( String token )
    {
        if ( ObjectUtils.equals( mToken, token ) )
            return;
        
        mToken = token;
        notifyListeners( this );
    }
    
    public String getToken()
    {
        return mToken;
    }
}
