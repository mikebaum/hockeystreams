package org.mbaum.common.value;

import org.apache.commons.lang3.ObjectUtils;

public class VolatileValue<T> implements Value<T>
{
	private volatile T mValue;
	
	public VolatileValue( T value )
	{
		mValue = value;
	}

	@Override
    public T get()
    {
	    return mValue;
    }

	@Override
    public boolean set( T value )
    {
		if ( ObjectUtils.equals( value, mValue ) )
			return false;
		
		mValue = value;
		return true;
    }
}
