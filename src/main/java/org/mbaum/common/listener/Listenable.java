package org.mbaum.common.listener;

public interface Listenable<T, L extends Listener<T>>
{
	void addListener( L listener );
	
	void removeListener( L listener );
	
	void clearListeners();
}
