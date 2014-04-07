package org.mbaum.hockeystreams.view;

import static org.mbaum.common.view.JTextFieldBuilder.buildPasswordField;
import static org.mbaum.common.view.JTextFieldBuilder.buildTextField;
import static org.mbaum.hockeystreams.model.LoginPanelModel.PASSWORD;
import static org.mbaum.hockeystreams.model.LoginPanelModel.USERNAME;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.mbaum.common.view.View;
import org.mbaum.hockeystreams.model.LoginPanelModel;
import org.mbaum.hockeystreams.model.LoginPanelModelImpl;

public class LoginPanel implements View
{
    private JPanel mPanel;
	private static View mUsernameView;
	private static View mPasswordView;

    public LoginPanel( LoginPanelModel model, Action loginAction )
    {
        mPanel = createLoginPanel( model, loginAction );
    }
    
    @SuppressWarnings("serial")
    public LoginPanel()
    {
    	this( new LoginPanelModelImpl(), new AbstractAction( "Login" )
    	{
			@Override
            public void actionPerformed( ActionEvent e ){}
		} );
    }
    
	@Override
    public void destroy()
    {
		mUsernameView.destroy();
		mPasswordView.destroy();
    }
    
    @Override
    public JComponent getComponent()
    {
        return mPanel;
    }
    
    private static JPanel createLoginPanel( LoginPanelModel model, Action loginAction )
    {
        JPanel authenticationPanel = new JPanel();
        
        mUsernameView = buildTextField( model.getModelValue( USERNAME ) );
		authenticationPanel.add( mUsernameView.getComponent() );
		
        mPasswordView = buildPasswordField( model.getModelValue( PASSWORD ) );
		authenticationPanel.add( mPasswordView.getComponent() );
        
        authenticationPanel.add( new JButton( loginAction ) );
        
        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( authenticationPanel, BorderLayout.WEST );
        
        return topPanel;
    }
}
