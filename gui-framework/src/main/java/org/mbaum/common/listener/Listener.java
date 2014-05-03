package org.mbaum.common.listener;

public interface Listener<T>
{
	void handleChanged( T newValue );
}
