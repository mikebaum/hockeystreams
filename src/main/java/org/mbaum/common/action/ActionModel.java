package org.mbaum.common.action;

import org.mbaum.common.model.Model;
import org.mbaum.common.veto.Vetoer;

public interface ActionModel extends Model<ActionModel>
{
	static final ModelValueId<ActionModel, Boolean> ENABLED = new ModelValueId<ActionModel, Boolean>(){};
	
	void addVetoer( Vetoer vetoer );
	
	String getDescription();
}
