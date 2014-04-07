package org.mbaum.serviio.model;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.serviio.net.transferobject.RepositoryResponse;

public class ServiioModelImpl extends AbstractModel<ServiioModel.Id<?>, ServiioModel> implements ServiioModel
{
	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final String DEFAULT_PORT = "23423";
	
	public ServiioModelImpl()
    {
		super();
		newModelValue( HOST_NAME, DEFAULT_HOSTNAME, "Hostname", this );
		newModelValue( PORT, DEFAULT_PORT, "Port", this );
		newModelValue( REPOSITORY_RESPONSE, new RepositoryResponse(), "Repository Response", this );
    }
	
	@Override
	public <T> MutableModelValue<T> getModelValue( Id<T> id )
	{
		return super.getModelValue( id );
	}
	
	@Override
	public <T> T getValue( Id<T> id )
	{
		return getModelValue( id ).get();
	}
	
	@Override
	public <T> void setValue( Id<T> id, T value )
	{
		getModelValue( id ).set( value );
	}
	
	@Override
    protected ListenableSupport<ServiioModel, Listener<ServiioModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (ServiioModel) this );
    }
}
