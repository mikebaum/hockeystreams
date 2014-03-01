package org.mbaum.hockeystreams.view;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.model.LoginPanelModelImpl;

public class LoginPanel
{
    private final JTextField mUsernameField;
    private final JPasswordField mPasswordField;
    private JPanel mPanel;

    public LoginPanel( LoginPanelModel model, Action loginAction )
    {
        mUsernameField = new JTextField( 10 );
        mPasswordField = new JPasswordField( 10 );
        mPanel = createLoginPanel( mUsernameField, mPasswordField, model, loginAction );
    }
    
    public JComponent getCompnent()
    {
        return mPanel;
    }
    
    private static JPanel createLoginPanel( JTextField usernameField,
                                            JPasswordField passwordField,
                                            LoginPanelModel model,
                                            Action loginAction )
    {
        JPanel authenticationPanel = new JPanel();
        
        authenticationPanel.add( new JLabel( "username: " ) );
        usernameField.setText( model.getUsername() );
        authenticationPanel.add( usernameField );
        usernameField.addKeyListener( createUsernameFieldKeyListener( model, usernameField ) );
        
        authenticationPanel.add( new JLabel( "password: " ) );
        passwordField.setText( model.getPassword() );
        authenticationPanel.add( passwordField );
        passwordField.addKeyListener( createPasswordFieldKeyListener( model, passwordField ) );
        
        authenticationPanel.add( new JButton( loginAction ) );
        
        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( authenticationPanel, BorderLayout.WEST );
        
        return topPanel;
    }
    
    private static KeyListener createPasswordFieldKeyListener( final LoginPanelModel model, 
                                                               final JPasswordField passwordField )
    {
        return new KeyAdapter()
        {
            @Override
            public void keyReleased( KeyEvent e )
            {
                model.setPassword( new String ( passwordField.getPassword() ) );
            }
        };
    }

    private static KeyListener createUsernameFieldKeyListener( final LoginPanelModel model, 
                                                               final JTextField usernameTextField )
    {
        return new KeyAdapter()
        {
            @Override
            public void keyReleased( KeyEvent e )
            {
                model.setUsername( usernameTextField.getText() );
            }
        };
    }
}
