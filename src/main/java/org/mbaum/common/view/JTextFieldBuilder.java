package org.mbaum.common.view;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.awt.EventQueue.isDispatchThread;

import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.MutableModelValue;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;

public class JTextFieldBuilder<T> implements ViewBuilder
{
	private static final Function<String, String> STRING_IDENTITY_EXTRACTOR = Functions.identity();
	
	private final MutableModelValue<T> mModelValue;
	private final Function<String, T> mModelValueExtractor;
	
	public JTextFieldBuilder( MutableModelValue<T> modelValue, Function<String, T> modelValueExtractor )
    {
		mModelValueExtractor = modelValueExtractor;
		mModelValue = Preconditions.checkNotNull( modelValue );
    }
	
	@Override
	public View buildView()
	{
		return buildTextField( mModelValue, mModelValueExtractor );
	}
	
	public static <T> View buildTextField( MutableModelValue<T> modelValue, Function<String, T> modelValueExtractor )
	{
		return buildTextField( modelValue, modelValueExtractor, new JTextField() );
	}
	
	public static View buildTextField( MutableModelValue<String> modelValue )
	{
		return buildTextField( modelValue, STRING_IDENTITY_EXTRACTOR );
	}
	
	public static View buildPasswordField( MutableModelValue<String> modelValue )
	{
		return buildTextField( modelValue, STRING_IDENTITY_EXTRACTOR, new JPasswordField() );
	}
	
	private static <T> View buildTextField( final MutableModelValue<T> modelValue,
			                                Function<String, T> modelValueExtractor, 
			                                final JTextField textField )
	{
		final JPanel panel = new JPanel();
		
		String value = modelValue.get().toString();
		textField.setText( value );
		textField.setColumns( Math.max( 10, value.length() ) );
		
		panel.add( new JLabel( modelValue.getDescription() + ": " ) );
		panel.add( textField );

		final Listener<T> listener = createModelValueListener( textField );
		modelValue.addListener( listener );

		final DocumentListener documentListener = createDocumentListener( textField, modelValue, modelValueExtractor );
		textField.getDocument().addDocumentListener( documentListener );

		return new ViewImpl( new Destroyable()
		{
			@Override
            public void destroy()
            {
	            modelValue.removeListener( listener );
	            textField.getDocument().removeDocumentListener( documentListener );
	            
	            Container parent = panel.getParent();
	            if ( parent != null )
	            	parent.remove( panel );
            }
			
		}, panel );
	}
	
	private static <T> DocumentListener createDocumentListener( final JTextField textField, 
													       	    final MutableModelValue<T> modelValue,
													       	    final Function<String, T> modelValueExtractor )
    {
	    return new DocumentListener()
		{
			@Override
			public void removeUpdate( DocumentEvent arg0 ) 
			{
				modelValue.set( modelValueExtractor.apply( textField.getText() ) );
			}
			
			@Override
			public void insertUpdate( DocumentEvent arg0 ) 
			{
				modelValue.set( modelValueExtractor.apply( textField.getText() ) );
			}
			
			@Override
			public void changedUpdate( DocumentEvent arg0 )
			{
				modelValue.set( modelValueExtractor.apply( textField.getText() ) );
			}
		};
    }

	private static <T> Listener<T> createModelValueListener( final JTextField textField )
    {
	    return new Listener<T>()
	    {
	    	@Override
	    	public void handleChanged( T newValue )
	    	{
	    		checkState( isDispatchThread(), "ModelValue changed events must be fired on the EDT" );
	    		checkNotNull( newValue );
	    		
	    		String newText = newValue.toString();
				if ( StringUtils.equals( newText, textField.getText() ) )
	    			return;
	    		
	    		textField.setText( newText );
	    	};
	    };
    }
}
