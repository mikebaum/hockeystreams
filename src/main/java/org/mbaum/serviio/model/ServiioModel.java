package org.mbaum.serviio.model;

import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.ModelValue;
import org.mbaum.serviio.net.transferobject.RepositoryResponse;

public class ServiioModel extends AbstractModel<ServiioModel>
{
	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final String DEFAULT_PORT = "23423";
	
	private final ModelValue<String> mHostName;
	private final ModelValue<String> mPort;
	
	private RepositoryResponse mRepositoryResponse;
	
	public ServiioModel()
    {
		mHostName = newModelValue( DEFAULT_HOSTNAME );
		mPort = newModelValue( DEFAULT_PORT );
    }
	
	public String getHostName()
	{
		return mHostName.get();
	}
	
	public void setHostName( String hostName )
	{
		mHostName.set( hostName );
	}
	
	public String getPort()
	{
		return mPort.get();
	}
	
	public void setPort( String port )
	{
		mPort.set( port );
	}
	
	public RepositoryResponse getRepositoryResponse()
	{
		return mRepositoryResponse;
	}
	
	public void setRepositoryResponse( RepositoryResponse repositoryResponse )
	{
		mRepositoryResponse = repositoryResponse;
	}
}
