package org.mbaum.common.model;

import static org.mbaum.common.listener.ListenableSupport.createListenableSupport;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.Value;

class MutableModelValueImpl<T> implements MutableModelValue<T>
{
	private final Value<T> mValue;
	private final T mDefaultValue;
	private final String mDescription;
	private final ListenableSupport<T, Listener<T>> mListenableSupport;

	public MutableModelValueImpl( Value<T> currentValue, T defaultValue, String description )
	{
		mValue = currentValue;
		mDefaultValue = defaultValue;
		mDescription = description;
		mListenableSupport = createListenableSupport( this );
	}

	@Override
	public T get()
	{
		return mValue.get();
	}

	@Override
	public void set( T value )
	{
		if ( mValue.set( value ) )
			mListenableSupport.notifyListeners();
	}

	@Override
    public void reset()
    {
		set( mDefaultValue );
    }
	
	@Override
	public String getDescription()
	{
	    return mDescription;
	}

	@Override
    public void addListener( Listener<T> listener )
    {
		mListenableSupport.addListener( listener );
    }

	@Override
    public void removeListener( Listener<T> listener )
    {
		mListenableSupport.addListener( listener );
    }

	@Override
    public void clearListeners()
    {
		mListenableSupport.clearListeners();
    }
}
