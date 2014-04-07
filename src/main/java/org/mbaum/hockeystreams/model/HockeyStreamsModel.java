package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue;

public interface HockeyStreamsModel extends Model<HockeyStreamsModel.Id<?>, HockeyStreamsModel>
{
	public interface Id<T> extends ModelValueId<T>{};
	
	public static final Id<String> SESSION_TOKEN = new Id<String>(){};
	
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
}