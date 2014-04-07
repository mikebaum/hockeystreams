package org.mbaum.common.model;

import org.mbaum.common.model.Model.ModelValueId;


public interface ProgressPanelModel extends Model<ProgressPanelModel.Id<?>, ProgressPanelModel>
{
	interface Id<T> extends ModelValueId<T>{}
	
	public static final Id<String> MESSAGE = new Id<String>(){};
	public static final Id<Boolean> INDETERMINATE = new Id<Boolean>(){};
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
}