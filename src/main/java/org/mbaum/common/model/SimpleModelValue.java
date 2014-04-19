package org.mbaum.common.model;

import org.mbaum.common.value.Value;

class SimpleModelValue<M extends Model<M>, T> implements ModelValue<M, T>
{
	private final ModelValueId<M, T> mId;
	private final Value<T> mCurrentValue;

	public SimpleModelValue( ModelValueId<M, T> id, Value<T> currentValue )
	{
		mId = id;
		mCurrentValue = currentValue;
	}

	@Override
	public T get()
	{
		return mCurrentValue.get();
	}

	@Override
	public ModelValueId<M, T> getId()
	{
		return mId;
	}
}
