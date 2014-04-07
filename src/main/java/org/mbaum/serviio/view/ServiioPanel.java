package org.mbaum.serviio.view;

import static org.mbaum.serviio.model.ServiioModel.HOST_NAME;
import static org.mbaum.serviio.model.ServiioModel.PORT;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.mbaum.common.view.View;
import org.mbaum.serviio.model.ServiioModel;

public class ServiioPanel implements View
{
	private static final String SERVIIO_HOSTNAME_LABEL_TEXT = "Serviio Host Name:";
	private static final String SERVIIO_PORT_LABEL_TEXT = "Port:";
	
	private final ServiioModel mServiioModel;
	private final JTextField mServerHostName;
	private final JComponent mPanel;
	private final JTextField mServiioPortField;
	
	public ServiioPanel( ServiioModel serviioModel )
	{
		mServiioModel = serviioModel;
		mServerHostName = new JTextField( 10 );
		mServiioPortField = new JTextField( 5 );
		
		mPanel = buildPanel( mServerHostName, mServiioPortField, mServiioModel );
	}

	private static JComponent buildPanel( JTextField serviioHostNameField, 
										  JTextField serviioPortField, 
										  ServiioModel model )
	{
		JPanel urlPanel = new JPanel();

		JLabel servvioHostNameLabel = new JLabel( SERVIIO_HOSTNAME_LABEL_TEXT );
		urlPanel.add( servvioHostNameLabel );
		serviioHostNameField.setText( model.getValue( HOST_NAME ) );
		serviioHostNameField.addKeyListener( createHostNameFieldKeyListener( model, serviioHostNameField ) );
		urlPanel.add( serviioHostNameField );
		
		JLabel serviioPortLabel = new JLabel( SERVIIO_PORT_LABEL_TEXT );
		urlPanel.add( serviioPortLabel );
		serviioPortField.setText( model.getValue( PORT ) );
		serviioPortField.addKeyListener( createPortFieldKeyListener( model, serviioPortField ) );
		urlPanel.add( serviioPortField );
		
        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( urlPanel, BorderLayout.WEST );
        
        topPanel.revalidate();
		
		return topPanel;
	}
	
	@Override
    public void destroy()
    {
	    // TODO Auto-generated method stub
    }
	
	@Override
	public JComponent getComponent()
	{
		return mPanel;
	}
    
    private static KeyListener createPortFieldKeyListener( final ServiioModel model, 
    													   final JTextField portField )
    {
        return new KeyAdapter()
        {
            @Override
            public void keyReleased( KeyEvent e )
            {
                model.setValue( PORT, new String ( portField.getText() ) );
            }
        };
    }

    private static KeyListener createHostNameFieldKeyListener( final ServiioModel model, 
                                                               final JTextField hostNameField )
    {
        return new KeyAdapter()
        {
            @Override
            public void keyReleased( KeyEvent e )
            {
                model.setValue( HOST_NAME, hostNameField.getText() );
            }
        };
    }
}
