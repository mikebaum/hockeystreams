package org.mbaum.common.model;

public interface Listenable<L extends Listener>
{
	void addListener( L listener );
	
	void removeListener( L listener );
}
