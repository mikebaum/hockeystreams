package org.mbaum.common.model;

import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.Value;
import org.mbaum.common.value.ValueImpl;

import com.google.common.base.Preconditions;

public interface MutableModelValue<T> extends Listenable<T, Listener<T>>, ModelValue<T>
{
	void set( T value );
	
	void reset();
	
	public static class Builder<T>
	{
		private final String mDescription;
		private final T mDefaultValue;
		private Value<T> mCurrentValue = Value.EmptyValue.emptyValue();
		
		public Builder( String description, T defaultValue )
        {
			mDescription = description;
			mDefaultValue = Preconditions.checkNotNull( defaultValue );
        }

		public MutableModelValue<T> build()
		{
			if ( mCurrentValue.isEmpty() )
				mCurrentValue = new ValueImpl<T>( mDefaultValue );
				
			return new MutableModelValueImpl<T>( mCurrentValue, mDefaultValue, mDescription );
		}

		public Builder<T> setCurrentValue( Value<T> currentValue )
		{
			mCurrentValue = currentValue;
			return this;
		}
		
		public static <T> Builder<T> create( String description, T defaultValue )
		{
			return new Builder<T>( description, defaultValue );
		}
	}
}
