package org.mbaum.common.model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

public interface ProgressPanelModel extends Model<ProgressPanelModel>
{
    static final boolean DEFAULT_INDETERMINANT = false;
    static final String DEFAULT_MESSAGE = "";
    
	static final ModelValueId<ProgressPanelModel, String> MESSAGE = createId( "Message", DEFAULT_MESSAGE );
	static final ModelValueId<ProgressPanelModel, Boolean> INDETERMINATE = createId( "Indeterminate", DEFAULT_INDETERMINANT );
}