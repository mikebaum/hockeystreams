package org.mbaum.common.veto;

import org.mbaum.common.Destroyable;


public interface Vetoer extends Destroyable
{
	boolean isVetoing();
	
	void setListener( VetoListener listener );
}
