package org.mbaum.common.model;

import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.Value;
import org.mbaum.common.value.ValueImpl;

public interface MutableModelValue<M extends Model<M>, T> extends Listenable<T, Listener<T>>, ModelValue<M, T>
{
	void set( T value );
	
	void reset();
	
	public static class Builder<M extends Model<M>, T>
	{
		private final ModelValueId<M, T> mId;
		private final T mDefaultValue;
		private Value<T> mCurrentValue = Value.EmptyValue.emptyValue();
		
		public Builder( ModelValueId<M, T> id, T defaultValue )
        {
			mId = id;
			mDefaultValue = defaultValue;
        }

		public MutableModelValue<M, T> build()
		{
			if ( mCurrentValue.isEmpty() )
				mCurrentValue = new ValueImpl<T>( mDefaultValue );
				
			return new MutableModelValueImpl<M, T>( mId, mCurrentValue );
		}

		public Builder<M, T> setCurrentValue( Value<T> currentValue )
		{
			mCurrentValue = currentValue;
			return this;
		}
		
		public static <M extends Model<M>, T> MutableModelValue<M, T> newModelValue( ModelValueId<M, T> id )
		{
		    return new Builder<M, T>( id, id.getDefaultValue() ).build();
		}
	}
}
