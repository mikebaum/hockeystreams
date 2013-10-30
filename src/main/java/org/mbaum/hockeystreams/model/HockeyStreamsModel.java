package org.mbaum.hockeystreams.model;

import org.apache.commons.lang3.ObjectUtils;

public class HockeyStreamsModel extends AbstractModel<HockeyStreamsModel>
{
    private String mToken;

    public void setSessionToken( String token )
    {
        if ( ObjectUtils.equals( mToken, token ) )
            return;
        
        mToken = token;
        notifyListener( this );
    }
    
    public String getToken()
    {
        return mToken;
    }
}
