package org.mbaum.common.model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

public interface ProgressPanelModel extends Model<ProgressPanelModel>
{
	static final ModelValueId<ProgressPanelModel, String> MESSAGE = createId();
	static final ModelValueId<ProgressPanelModel, Boolean> INDETERMINATE = createId();
}