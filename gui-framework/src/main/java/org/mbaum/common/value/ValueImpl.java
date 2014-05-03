package org.mbaum.common.value;

import org.apache.commons.lang3.ObjectUtils;

public class ValueImpl<T> implements Value<T>
{
	private T mValue;
	
	public ValueImpl( T value )
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
	
	@Override
	public boolean isEmpty()
	{
	    return mValue == null;
	}

    @Override
    public String toString()
    {
        return "ValueImpl [mValue=" + mValue + "]";
    }
}
