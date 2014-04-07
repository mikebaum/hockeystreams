package org.mbaum.common.view;

import javax.swing.JComponent;

import org.mbaum.common.Destroyable;


public interface ViewBuilder
{
	View buildView();
	
	static class ViewImpl implements View
	{
		private final Destroyable mDestroyable;
		private final JComponent mComponent;
		
		public ViewImpl( Destroyable destroyable, JComponent component )
        {
	        mDestroyable = destroyable;
	        mComponent = component;
        }

		@Override
        public void destroy()
        {
			mDestroyable.destroy();
        }

		@Override
        public JComponent getComponent()
        {
	        return mComponent;
        }
	}
}
