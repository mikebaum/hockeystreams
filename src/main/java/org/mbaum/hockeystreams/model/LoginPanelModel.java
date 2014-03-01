package org.mbaum.hockeystreams.model;

import org.mbaum.common.model.Model;

public interface LoginPanelModel extends Model<LoginPanelModel>
{
	String getUsername();

	boolean canAuthenticate();

	String getPassword();

	String getApiKey();

	public abstract void setPassword( String password );

	public abstract void setUsername( String username );

}