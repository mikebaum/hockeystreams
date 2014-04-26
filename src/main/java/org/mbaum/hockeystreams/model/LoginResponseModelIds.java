package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.ModelSpec.IdBuilder.createId;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;

public class LoginResponseModelIds implements ModelSpec
{
    public static final ModelValueId<LoginResponseModelIds, String> STATUS     = createId( "status", "" );
    public static final ModelValueId<LoginResponseModelIds, String> UID        = createId( "uid", "" );
    public static final ModelValueId<LoginResponseModelIds, String> USERNAME   = createId( "username", "" );
    public static final ModelValueId<LoginResponseModelIds, String> FAV_TEAM   = createId( "favteam", "" );
    public static final ModelValueId<LoginResponseModelIds, String> MEMBERSHIP = createId( "membership", "" );
    public static final ModelValueId<LoginResponseModelIds, String> TOKEN      = createId( "token", "" );
    public static final ModelValueId<LoginResponseModelIds, String> MESSAGE    = createId( "msg", "" );

    private LoginResponseModelIds(){}
}
