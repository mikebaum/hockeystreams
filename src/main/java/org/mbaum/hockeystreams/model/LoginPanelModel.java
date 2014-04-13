package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

import org.mbaum.common.model.Model;

public interface LoginPanelModel extends Model<LoginPanelModel>
{
	static final ModelValueId<LoginPanelModel, String> USERNAME = createId( "Username", "mbaum" );
	static final ModelValueId<LoginPanelModel, String> PASSWORD = createId( "Pasword", "" );
	static final ModelValueId<LoginPanelModel, String> API_KEY = createId( "Api Key", "1dd7bceb51c69ba4190a5be6d59ee41e" );
}