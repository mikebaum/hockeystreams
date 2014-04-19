package org.mbaum.serviio.model;

import static org.mbaum.common.model.Model.Builder.createModel;
import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValueId;

public interface ServiioModel extends Model<ServiioModel>
{
    static final String DEFAULT_HOSTNAME = "localhost";
    static final String DEFAULT_PORT = "23423";
    
	static final ModelValueId<ServiioModel, String> HOST_NAME = createId( "Hostname", DEFAULT_HOSTNAME );
	static final ModelValueId<ServiioModel, String> PORT = createId( "Port", DEFAULT_PORT );
	static final ModelValueId<ServiioModel, Model<RepositoryModel>> REPOSITORY_RESPONSE = createId( "Repository Response", createModel( RepositoryModel.class ) );
}
