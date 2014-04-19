package org.mbaum.common.action;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValueId;
import org.mbaum.common.veto.Vetoer;

public interface ActionModel extends Model<ActionModel>
{
	static final ModelValueId<ActionModel, Boolean> ENABLED = createId( "Enabled", false );
	static final ModelValueId<ActionModel, String> DESCRIPTION = createId( "Description", "" );
	
	void addVetoer( Vetoer vetoer );
}
