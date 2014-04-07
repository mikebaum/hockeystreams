package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue;

public interface LoginPanelModel extends Model<LoginPanelModel.Id<?>, LoginPanelModel>
{
	public interface Id<T> extends ModelValueId<T>{};
	
	public static final Id<String> USERNAME = new Id<String>(){};
	public static final Id<String> PASSWORD = new Id<String>(){};
	public static final Id<String> API_KEY = new Id<String>(){};
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
	
}