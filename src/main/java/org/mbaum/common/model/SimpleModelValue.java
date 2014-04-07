package org.mbaum.common.model;

import org.mbaum.common.value.Value;

class SimpleModelValue<T> implements ModelValue<T>
{
	private final String mDescription;
	private final Value<T> mCurrentValue;

	public SimpleModelValue( String description, Value<T> currentValue )
	{
		mDescription = description;
		mCurrentValue = currentValue;
	}

	@Override
	public T get()
	{
		return mCurrentValue.get();
	}

	@Override
	public String getDescription()
	{
		return mDescription;
	}
}
