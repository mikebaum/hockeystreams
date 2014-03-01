package org.mbaum.common.value;

public interface Value<T>
{
	T get();
	
	/**
	 * @param value - the new value to set
	 * @return - true if the value was changed, false otherwise
	 */
	boolean set( T value );
}
