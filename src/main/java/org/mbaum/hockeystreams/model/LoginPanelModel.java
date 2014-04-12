package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.Model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

public interface LoginPanelModel extends Model<LoginPanelModel>
{
	static final ModelValueId<LoginPanelModel, String> USERNAME = createId();
	static final ModelValueId<LoginPanelModel, String> PASSWORD = createId();
	static final ModelValueId<LoginPanelModel, String> API_KEY = createId();
}