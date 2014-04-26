package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;

public class HockeyStreamsModel implements ModelSpec
{
	public static final ModelValueId<HockeyStreamsModel, String> SESSION_TOKEN = createId( "Token", "" );
	
	private HockeyStreamsModel() {}
}