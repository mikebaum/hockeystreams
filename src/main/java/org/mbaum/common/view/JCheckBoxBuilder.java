package org.mbaum.common.view;

import static java.awt.EventQueue.isDispatchThread;

import java.awt.Container;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.MutableModelValue;

import com.google.common.base.Preconditions;

public class JCheckBoxBuilder implements ViewBuilder
{
	private final MutableModelValue<Boolean> mModelValue;
	
	public JCheckBoxBuilder( MutableModelValue<Boolean> modelValue )
    {
		mModelValue = modelValue;
    }
	
	@Override
	public View buildView()
	{
		final JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected( mModelValue.get() );
		
		final ChangeListener checkBoxChangeListener = createChangeListener( mModelValue, checkBox );
		checkBox.addChangeListener( checkBoxChangeListener );
		
		final Listener<Boolean> listener = createModelValueListener( checkBox );
		mModelValue.addListener( listener );
		
		return new ViewBuilder.ViewImpl( new Destroyable()
		{
			@Override
			public void destroy()
			{
				checkBox.removeChangeListener( checkBoxChangeListener );
				mModelValue.removeListener( listener );
				
				Container parent = checkBox.getParent();
	            if ( parent != null )
	            	parent.remove( checkBox );
			}
		}, checkBox );
	}

	private static ChangeListener createChangeListener( final MutableModelValue<Boolean> modelValue,
														final JCheckBox checkBox )
    {
	    return new ChangeListener()
		{
			@Override
			public void stateChanged( ChangeEvent e )
			{
				modelValue.set( checkBox.isSelected() );
			}
		};
    }

	private static Listener<Boolean> createModelValueListener( final JCheckBox checkBox )
    {
	    return new Listener<Boolean>()
	    {
	    	@Override
	    	public void handleChanged( Boolean newValue )
	    	{
	    		Preconditions.checkState( isDispatchThread(), 
	    		                          "ModelValue changed events must be fired on the EDT" );
	    		Preconditions.checkNotNull( newValue );
	    		
	    		if ( checkBox.isSelected() != newValue )
	    			checkBox.setSelected( newValue );
	    	}
	    };
    }
}
