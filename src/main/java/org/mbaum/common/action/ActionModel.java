package org.mbaum.common.action;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.veto.Vetoer;

public interface ActionModel extends ModelSpec, MutableModel<ActionModel>
{
    ModelValueId<ActionModel, Boolean> ENABLED     = createId( "Enabled", false );
    ModelValueId<ActionModel, String>  DESCRIPTION = createId( "Description", "" );
	
	void addVetoer( Vetoer vetoer );
}
