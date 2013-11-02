package org.mbaum.common.model;

import org.apache.commons.lang3.StringUtils;

public class ProgressPanelModel extends AbstractModel<ProgressPanelModel>
{
	private static boolean DEFAULT_INDETERMINANT = false;
	private static String DEFAULT_MESSAGE = "";
	
	private String mMessage = DEFAULT_MESSAGE;
	private boolean mIndeterminant = DEFAULT_INDETERMINANT;
	
	public String getMessage()
	{
		return mMessage;
	}
	
	public void setMessage( String message )
	{
		if ( StringUtils.equals( message, mMessage ) )
			return;
		
		mMessage = message;
		notifyListeners( this );
	}
	
	public boolean isIndeterminant()
	{
		return mIndeterminant;
	}
	
	public void setIndeterminant( boolean indeterminant )
	{
		if ( indeterminant == mIndeterminant )
			return;
		
		mIndeterminant = indeterminant;
		notifyListeners( this );
	}
	
	public void reset()
	{
		boolean sendNotification = false;
		
		if ( mIndeterminant != DEFAULT_INDETERMINANT )
		{
			mIndeterminant = DEFAULT_INDETERMINANT;
			sendNotification = true;
		}
		
		if ( ! StringUtils.equals( DEFAULT_MESSAGE, mMessage ) )
		{
			mMessage = DEFAULT_MESSAGE;
			sendNotification = true;
		}
		
		if ( sendNotification )
			notifyListeners( this );
	}
}
