package org.mbaum.hockeystreams.model;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.model.AbstractModel;

public class LoginPanelModel extends AbstractModel<LoginPanelModel>
{
	private String mUsername = "mbaum";
	private String mPassword = "";
	private final String mApiKey = "1dd7bceb51c69ba4190a5be6d59ee41e";
	
	public String getUsername()
	{
		return mUsername;
	}
	
	public boolean canAuthenticate()
	{
		return ! StringUtils.isBlank( getUsername() ) &&
		       ! StringUtils.isBlank( getPassword() );
	}

	public void setUsername( String username )
	{
		if ( StringUtils.equals( username, mUsername ) )
			return;
		
		mUsername = username;
		notifyListeners( this );
	}
	
	public String getPassword()
	{
		return mPassword;
	}
	
	public void setPassword( String password )
	{
		if ( StringUtils.equals( password, mPassword ) )
			return;
		
		mPassword = password;
		notifyListeners( this );
	}
	
	public String getApiKey()
	{
		return mApiKey;
	}

	@Override
	public String toString()
	{
		return "ExceptionPanelModel [mUsername=" + mUsername
				+ ", mPassword=" + mPassword + "]";
	}		
}