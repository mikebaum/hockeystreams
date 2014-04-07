package org.mbaum.serviio.model;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.serviio.model.ServiioModel.Id;
import org.mbaum.serviio.net.transferobject.RepositoryResponse;

public interface ServiioModel extends Model<Id<?>, ServiioModel>
{
	public interface Id<T> extends ModelValueId<T>{}
	
	public static final Id<String> HOST_NAME = new Id<String>(){};
	public static final Id<String> PORT = new Id<String>(){};
	public static final Id<RepositoryResponse> REPOSITORY_RESPONSE = new Id<RepositoryResponse>(){};
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
}
