package org.mbaum.hockeystreams;

import java.util.LinkedList;

import org.mbaum.common.Component;
import org.mbaum.common.Destroyable;
import org.mbaum.common.view.View;
import org.mbaum.common.view.ViewBuilder;

import com.google.common.collect.Lists;

public abstract class AbstractComponent implements Component
{
	//private final ViewBuilder mViewBuilder;
	private final LinkedList<Destroyable> mDestroyables = Lists.newLinkedList();
	
	//private View mView;

	protected AbstractComponent() {}

	@Override
    public void destroy()
    {
    	for ( Destroyable destroyable : mDestroyables )
    		destroyable.destroy();
    }

	protected <D extends Destroyable> D d( D destroyable )
    {
    	mDestroyables.addFirst( destroyable );
    	return destroyable;
    }

//	@Override
//    public View getView()
//    {
//		if ( mView == null )
//			mView = mViewBuilder.buildView();
//		
//        return mView;
//    }
}