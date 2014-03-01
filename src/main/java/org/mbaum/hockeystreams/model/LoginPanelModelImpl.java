package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.ModelValue.Builder.newModelValue;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.ModelValue;
import org.mbaum.common.model.Notifier;

public class LoginPanelModelImpl extends AbstractModel<LoginPanelModel> implements LoginPanelModel
{
	private final ModelValue<String> mUsername;
	private final ModelValue<String> mPassword;
	private final ModelValue<String> mApiKey;
	
	public LoginPanelModelImpl()
    {
		mUsername = newModelValue( "mbaum" );
		mPassword = newModelValue( "" );
		mApiKey = newModelValue( "1dd7bceb51c69ba4190a5be6d59ee41e" );
    }
	
	public String getUsername()
	{
		return mUsername.get();
	}
	
	public boolean canAuthenticate()
	{
		return ! StringUtils.isBlank( getUsername() ) &&
		       ! StringUtils.isBlank( getPassword() );
	}

	@Override
    public void setUsername( String username )
	{
		mUsername.set( username );
	}
	
	public String getPassword()
	{
		return mPassword.get();
	}
	
	@Override
    public void setPassword( String password )
	{
		mPassword.set( password );
	}
	
	public String getApiKey()
	{
		return mApiKey.get();
	}

	@Override
	public String toString()
	{
		return "ExceptionPanelModel [mUsername=" + mUsername
				+ ", mPassword=" + mPassword + "]";
	}
}