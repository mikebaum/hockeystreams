package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

import org.mbaum.common.model.Model;

public interface HockeyStreamsModel extends Model<HockeyStreamsModel>
{
	final ModelValueId<HockeyStreamsModel, String> SESSION_TOKEN = createId( "Token" );
}