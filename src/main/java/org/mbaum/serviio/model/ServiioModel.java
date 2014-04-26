package org.mbaum.serviio.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;
import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;
import org.mbaum.common.model.MutableModel;

public class ServiioModel implements ModelSpec
{
    private static final String DEFAULT_HOSTNAME = "localhost";
    private static final String DEFAULT_PORT     = "23423";
    
    public static final ModelValueId<ServiioModel, String>                        HOST_NAME           = createId( "Hostname", DEFAULT_HOSTNAME );
    public static final ModelValueId<ServiioModel, String>                        PORT                = createId( "Port", DEFAULT_PORT );
    public static final ModelValueId<ServiioModel, MutableModel<RepositoryModel>> REPOSITORY_RESPONSE = createId( "Repository Response", createMutableModel( RepositoryModel.class ) );
    
    private ServiioModel() {}
}
