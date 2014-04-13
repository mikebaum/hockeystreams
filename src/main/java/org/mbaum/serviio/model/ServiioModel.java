package org.mbaum.serviio.model;

import static org.mbaum.common.model.Model.IdBuilder.createId;

import org.mbaum.common.model.Model;
import org.mbaum.serviio.net.transferobject.RepositoryResponse;

public interface ServiioModel extends Model<ServiioModel>
{
    static final String DEFAULT_HOSTNAME = "localhost";
    static final String DEFAULT_PORT = "23423";
    
	static final ModelValueId<ServiioModel, String> HOST_NAME = createId( "Hostname", DEFAULT_HOSTNAME );
	static final ModelValueId<ServiioModel, String> PORT = createId( "Port", DEFAULT_PORT );
	static final ModelValueId<ServiioModel, RepositoryResponse> REPOSITORY_RESPONSE = createId( "Repository Response", new RepositoryResponse() );
}
