package org.mbaum.serviio.model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

import org.mbaum.common.model.Model;
import org.mbaum.serviio.net.transferobject.RepositoryResponse;

public interface ServiioModel extends Model<ServiioModel>
{
	static final ModelValueId<ServiioModel, String> HOST_NAME = createId();
	static final ModelValueId<ServiioModel, String> PORT = createId();
	static final ModelValueId<ServiioModel, RepositoryResponse> REPOSITORY_RESPONSE = createId();
}
