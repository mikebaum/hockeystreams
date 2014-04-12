package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.Model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

public interface HockeyStreamsModel extends Model<HockeyStreamsModel>
{
	final ModelValueId<HockeyStreamsModel, String> SESSION_TOKEN = createId();
}