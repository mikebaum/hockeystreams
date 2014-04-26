package org.mbaum.common.view;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.awt.EventQueue.isDispatchThread;

import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.StringUtils;
import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.common.view.ViewBuilder.ViewImpl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

public class JTextFieldBuilders
{
	private static final Function<String, String> STRING_IDENTITY_EXTRACTOR = Functions.identity();
	
	public static final Supplier<JTextField> TEXT_FIELD_SUPPLIER = 
    	new Supplier<JTextField>()
        {
            @Override
            public JTextField get()
            {
                return new JTextField();
            }
        };
        
    public static final Supplier<JTextField> PASSOWRD_FIELD_SUPPLIER = 
        new Supplier<JTextField>()
        {
            @Override
            public JTextField get()
            {
                return new JPasswordField();
            }
        };
	
	public static <M extends ModelSpec, T> ViewBuilder textFieldBuilder( final MutableModelValue<M, T> modelValue, 
	                                                                      final Function<String, T> modelValueExtractor,
	                                                                      final Supplier<JTextField> textComponentSupplier )
	{
		return new ViewBuilder()
        {
            @Override
            public View buildView()
            {
                Preconditions.checkState( SwingUtilities.isEventDispatchThread(),
                                          "Cannot construct a text field view unless you are on the EDT." );
                return buildTextField( modelValue, modelValueExtractor, textComponentSupplier.get() );
            }
        };
	}
	
	public static <M extends ModelSpec> ViewBuilder textFieldBuilder( MutableModelValue<M, String> modelValue )
	{
		return textFieldBuilder( modelValue, STRING_IDENTITY_EXTRACTOR, TEXT_FIELD_SUPPLIER );
	}
	
	public static <M extends ModelSpec> ViewBuilder passwordFieldBuilder( MutableModelValue<M, String> modelValue )
	{
		return textFieldBuilder( modelValue, STRING_IDENTITY_EXTRACTOR, PASSOWRD_FIELD_SUPPLIER );
	}
	
	private static <M extends ModelSpec, T> View buildTextField( final MutableModelValue<M, T> modelValue,
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
	
	private static <M extends ModelSpec, T> DocumentListener 
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
