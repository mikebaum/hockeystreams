package org.mbaum.common.component;

import org.mbaum.common.Destroyable;
import org.mbaum.common.DestroyableSupport;

public abstract class AbstractComponent implements Component
{
	private final DestroyableSupport mDestroyables = new DestroyableSupport();

	protected AbstractComponent() {}

	@Override
    public void destroy()
    {
		mDestroyables.destroy();
    }

	protected <D extends Destroyable> D d( D destroyable )
    {
    	return mDestroyables.addDestroyable( destroyable );
    }
}