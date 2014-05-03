package org.mbaum.common.listener;

import org.mbaum.common.Destroyable;

public interface Listenable<T, L extends Listener<T>> extends Destroyable
{
	void addListener( L listener );
	
	void removeListener( L listener );
}
