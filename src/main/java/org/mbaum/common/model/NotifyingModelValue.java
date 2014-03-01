package org.mbaum.common.model;

import org.mbaum.common.value.Value;

public class NotifyingModelValue<T> implements ModelValue<T>
{
	private final Value<T> mValue;
	private final T mDefaultValue;
	private final Notifier mNotifier;

	public NotifyingModelValue( Value<T> currentValue, T defaultValue, Notifier notifier )
	{
		mValue = currentValue;
		mDefaultValue = defaultValue;
		mNotifier = notifier;
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
			mNotifier.notifyListeners();
	}

	@Override
    public void reset()
    {
		set( mDefaultValue );
    }
}
