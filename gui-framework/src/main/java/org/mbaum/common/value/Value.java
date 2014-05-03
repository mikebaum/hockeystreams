package org.mbaum.common.value;

import com.google.common.base.Supplier;

public interface Value<T> extends Supplier<T>
{
	/**
	 * @param value - the new value to set
	 * @return - true if the value was changed, false otherwise
	 */
	boolean set( T value );
	
	boolean isEmpty();
	
	static class EmptyValue<T> implements Value<T>
	{
		public static Value<?> EMPTY_VALUE = new EmptyValue<Object>();
		
		@Override
        public T get()
        {
	        return null;
        }

		@Override
        public boolean set( T value )
        {
	        return false;
        }
		
		@Override
		public boolean isEmpty()
		{
		    return true;
		}
		
		@SuppressWarnings("unchecked")
        public static <T> Value<T> emptyValue()
		{
			return (Value<T>) EMPTY_VALUE;
		}
	};
}
