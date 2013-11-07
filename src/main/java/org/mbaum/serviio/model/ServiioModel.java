package org.mbaum.serviio.model;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.model.AbstractModel;

public class ServiioModel extends AbstractModel<ServiioModel>
{
	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final String DEFAULT_PORT = "23423";
	
	private String mHostName = DEFAULT_HOSTNAME;
	private String mPort = DEFAULT_PORT;
	
	public String getHostName()
	{
		return mHostName;
	}
	
	public void setHostName( String hostName )
	{
		if ( StringUtils.equals( mHostName, hostName ) )
			return;
		
		mHostName = hostName;
		notifyListeners( this );
	}
	
	public String getPort()
	{
		return mPort;
	}
	
	public void setPort( String port )
	{
		if ( StringUtils.equals( mPort, port ) )
			return;
		
		mPort = port;
		notifyListeners( this );
	}
}
