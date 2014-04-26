package org.mbaum.common.model;

import org.mbaum.common.value.Value;
import org.mbaum.common.value.ValueImpl;

class SimpleModelValue<I extends ModelSpec, T> implements ModelValue<I, T>
{
	private final ModelValueId<I, T> mId;
	private final Value<T> mCurrentValue;

	public SimpleModelValue( ModelValueId<I, T> id, Value<T> currentValue )
	{
		mId = id;
		mCurrentValue = currentValue;
	}
	
	public SimpleModelValue( ModelValueId<I, T> id, T currentValue )
    {
        mId = id;
        mCurrentValue = new ValueImpl<T>( currentValue );
    }

	@Override
	public T get()
	{
		return mCurrentValue.get();
	}

	@Override
	public ModelValueId<I, T> getId()
	{
		return mId;
	}
}
