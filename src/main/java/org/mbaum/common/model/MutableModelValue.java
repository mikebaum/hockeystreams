package org.mbaum.common.model;

import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.Value;
import org.mbaum.common.value.ValueImpl;

public interface MutableModelValue<I extends ModelSpec, T> extends Listenable<T, Listener<T>>, ModelValue<I, T>
{
	void set( T value );
	
	void reset();
	
	public static class Builder<I extends ModelSpec, T>
	{
		private final ModelValueId<I, T> mId;
		private final T mDefaultValue;
		private Value<T> mCurrentValue = Value.EmptyValue.emptyValue();
		
		public Builder( ModelValueId<I, T> id )
        {
			mId = id;
			mDefaultValue = id.getDefaultValue();
        }

		public MutableModelValue<I, T> build()
		{
			if ( mCurrentValue.isEmpty() )
				mCurrentValue = new ValueImpl<T>( mDefaultValue );
				
			return new MutableModelValueImpl<I, T>( mId, mCurrentValue );
		}

		public Builder<I, T> setCurrentValue( Value<T> currentValue )
		{
			mCurrentValue = currentValue;
			return this;
		}
		
		public static <I extends ModelSpec, T> MutableModelValue<I, T> newModelValue( ModelValueId<I, T> id )
		{
		    return new Builder<I, T>( id ).build();
		}
	}
}
