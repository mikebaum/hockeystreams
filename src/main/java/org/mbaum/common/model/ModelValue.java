package org.mbaum.common.model;

import org.mbaum.common.model.Model.ModelValueId;

import com.google.common.base.Supplier;

public interface ModelValue<M extends Model<M>, T> extends Supplier<T>
{
	ModelValueId<M, T> getId();
	
//	String toJSON();
//	
//	ModelValue<M, T> fromJSON( String jsonString );
}