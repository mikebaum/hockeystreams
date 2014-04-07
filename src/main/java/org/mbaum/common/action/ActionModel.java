package org.mbaum.common.action;

import org.mbaum.common.action.ActionModel.Id;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.veto.Vetoer;

public interface ActionModel extends Model<Id<?>, ActionModel>
{
	public interface Id<T> extends ModelValueId<T>{};
	
	public static final Id<Boolean> ENABLED = new Id<Boolean>(){};
	
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
	
	void addVetoer( Vetoer vetoer );
	
	String getDescription();
}
