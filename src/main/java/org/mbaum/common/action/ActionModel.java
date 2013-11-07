package org.mbaum.common.action;

import org.mbaum.common.model.Model;
import org.mbaum.common.veto.Vetoer;

public interface ActionModel extends Model<ActionModel>
{
	boolean isEnabled();
	
	void addVetoer( Vetoer vetoer );
}
