package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.Model;

public interface HockeyStreamsModel extends Model<HockeyStreamsModel>
{
	void setSessionToken( String token );

	String getToken();

}