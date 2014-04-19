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
import org.mbaum.common.model.Model;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.common.view.ViewBuilder.ViewImpl;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class JTextFieldBuilders
{
	private static final Function<String, String> STRING_IDENTITY_EXTRACTOR = Functions.identity();
	
	public static <M extends Model<M>, T> ViewBuilder textFieldBuilder( final MutableModelValue<M, T> modelValue, 
	                                                                    final Function<String, T> modelValueExtractor )
	{
		return new ViewBuilder()
        {
            @Override
            public View buildView()
            {
                return buildTextField( modelValue, modelValueExtractor, new JTextField() );
            }
        };
	}
	
	public static <M extends Model<M>> ViewBuilder textFieldBuilder( MutableModelValue<M, String> modelValue )
	{
		return textFieldBuilder( modelValue, STRING_IDENTITY_EXTRACTOR );
	}
	
	public static <M extends Model<M>> View passwordFieldBuilder( MutableModelValue<M, String> modelValue )
	{
		return buildTextField( modelValue, STRING_IDENTITY_EXTRACTOR, new JPasswordField() );
	}
	
	private static <M extends Model<M>, T> View buildTextField( final MutableModelValue<M, T> modelValue,
	                                                            Function<String, T> modelValueExtractor, 
	                                                            final JTextField textField )
	{
		final JPanel panel = new JPanel();
		
		String value = modelValue.get().toString();
		textField.setText( value );
		textField.setColumns( Math.max( 10, value.length() ) );
		
		panel.add( new JLabel( modelValue.getId().getName() + ": " ) );
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
	
	private static <M extends Model<M>, T> DocumentListener 
	    createDocumentListener( final JTextField textField, 
	                            final MutableModelValue<M, T> modelValue,
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
