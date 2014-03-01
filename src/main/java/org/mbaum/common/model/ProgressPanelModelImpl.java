package org.mbaum.common.model;


public class ProgressPanelModelImpl extends AbstractModel<ProgressPanelModel> implements ProgressPanelModel
{
	private static boolean DEFAULT_INDETERMINANT = false;
	private static String DEFAULT_MESSAGE = "";
	
	private final ModelValue<String> mMessage;
	private final ModelValue<Boolean> mIndeterminant;
	
	public ProgressPanelModelImpl()
    {
		mMessage = newModelValue( DEFAULT_MESSAGE );
		mIndeterminant = newModelValue( DEFAULT_INDETERMINANT );
    }
	
	public String getMessage()
	{
		return mMessage.get();
	}
	
	@Override
    public void setMessage( String message )
	{
		mMessage.set( message );
	}
	
	public boolean isIndeterminant()
	{
		return mIndeterminant.get();
	}
	
	@Override
    public void setIndeterminant( boolean indeterminant )
	{
		mIndeterminant.set( indeterminant );
	}
}
