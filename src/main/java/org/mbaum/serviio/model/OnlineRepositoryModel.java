package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue;

public interface OnlineRepositoryModel extends Model<OnlineRepositoryModel.Id<?>, OnlineRepositoryModel>
{
	public interface Id<T> extends ModelValueId<T>{}
	
	public static final Id<Integer> ID = new Id<Integer>(){};
	public static final Id<String> REPOSITORY_TYPE = new Id<String>(){};
	public static final Id<String> CONTENT_URL = new Id<String>(){};
	public static final Id<String> FILE_TYPE = new Id<String>(){};
	public static final Id<String> REPOSITORY_NAME = new Id<String>(){};
	public static final Id<Boolean> ENABLED = new Id<Boolean>(){};
	public static final Id<List<Integer>> ACCESS_GROUP_IDS = new Id<List<Integer>>(){};
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
}