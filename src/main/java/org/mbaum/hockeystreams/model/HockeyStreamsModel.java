package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValueId;

public interface HockeyStreamsModel extends Model<HockeyStreamsModel>
{
	final ModelValueId<HockeyStreamsModel, String> SESSION_TOKEN = createId( "Token", "" );
}