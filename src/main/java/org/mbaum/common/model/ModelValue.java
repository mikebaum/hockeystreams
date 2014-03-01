package org.mbaum.common.model;

import org.mbaum.common.value.VolatileValue;
import org.mbaum.common.value.Value;
import org.mbaum.common.value.ValueImpl;

public interface ModelValue<T>
{
	T get();
	void set( T value );
	void reset();
	
	public static class Builder<T>
	{
		private Value<T> mCurrentValue;
		private T mDefaultValue;
		private Notifier mNotifier;
		
		public ModelValue<T> build()
		{
			return new NotifyingModelValue<T>( mCurrentValue, mDefaultValue, mNotifier );
		}

		public Builder<T> setCurrentValue( Value<T> currentValue )
		{
			mCurrentValue = currentValue;
			return this;
		}

		public Builder<T> setDefaultValue( T defaultValue )
		{
			mDefaultValue = defaultValue;
			return this;
		}

		public Builder<T> setNotifier( Notifier notifier )
		{
			mNotifier = notifier;
			return this;
		}
		
		public static <T> Builder<T> create()
		{
			return new Builder<T>();
		}
		
		public static <T> ModelValue<T> newModelValue( Notifier notifier )
		{
			return new Builder<T>().setNotifier( notifier ).build();
		}
		
		public static <T> ModelValue<T> newModelValue( Notifier notifier, T defaultValue )
		{
			return new Builder<T>().setNotifier( notifier )
					               .setDefaultValue( defaultValue )
					               .setCurrentValue( new ValueImpl<T>( defaultValue ) ).build();
		}
		
		public static <T> ModelValue<T> newVolatileModelValue( Notifier notifier, T defaultValue )
		{
			return new Builder<T>().setNotifier( notifier )
					               .setDefaultValue( defaultValue )
					               .setCurrentValue( new VolatileValue<T>( defaultValue ) ).build();
		}
	}
}
