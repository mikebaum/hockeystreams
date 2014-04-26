package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;

public class LoginPanelModel implements ModelSpec
{
    public static final ModelValueId<LoginPanelModel, String> USERNAME = createId( "Username", "mbaum" );
    public static final ModelValueId<LoginPanelModel, String> PASSWORD = createId( "Pasword", "" );
    public static final ModelValueId<LoginPanelModel, String> API_KEY = createId( "Api Key", "1dd7bceb51c69ba4190a5be6d59ee41e" );
    
    private LoginPanelModel() {}
}