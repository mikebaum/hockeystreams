package org.mbaum.common;

import java.util.LinkedList;

import com.google.common.collect.Lists;

public class DestroyableSupport implements Destroyable
{
	private final LinkedList<Destroyable> mDestroyables = Lists.newLinkedList();

	@Override
    public void destroy()
    {
    	for ( Destroyable destroyable : mDestroyables )
    		destroyable.destroy();
    }

	public <D extends Destroyable> D addDestroyable( D destroyable )
    {
    	mDestroyables.addFirst( destroyable );
    	return destroyable;
    }
}
