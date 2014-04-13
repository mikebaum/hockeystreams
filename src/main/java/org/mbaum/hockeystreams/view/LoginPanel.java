package org.mbaum.hockeystreams.view;

import static org.mbaum.common.model.Model.Builder.createModel;
import static org.mbaum.common.view.JTextFieldBuilders.passwordFieldBuilder;
import static org.mbaum.common.view.JTextFieldBuilders.textFieldBuilder;
import static org.mbaum.hockeystreams.model.LoginPanelModel.PASSWORD;
import static org.mbaum.hockeystreams.model.LoginPanelModel.USERNAME;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.mbaum.common.model.Model;
import org.mbaum.common.view.View;
import org.mbaum.hockeystreams.model.LoginPanelModel;

public class LoginPanel implements View
{
    private JPanel mPanel;
	private static View mUsernameView;
	private static View mPasswordView;

    public LoginPanel( Model<LoginPanelModel> model, Action loginAction )
    {
        mPanel = createLoginPanel( model, loginAction );
    }
    
    @SuppressWarnings("serial")
    public LoginPanel()
    {
    	this( createModel( LoginPanelModel.class ), new AbstractAction( "Login" )
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
    
    private static JPanel createLoginPanel( Model<LoginPanelModel> model, Action loginAction )
    {
        JPanel authenticationPanel = new JPanel();
        
        mUsernameView = textFieldBuilder( model.getModelValue( USERNAME ) ).buildView();
		authenticationPanel.add( mUsernameView.getComponent() );
		
        mPasswordView = passwordFieldBuilder( model.getModelValue( PASSWORD ) );
		authenticationPanel.add( mPasswordView.getComponent() );
        
        authenticationPanel.add( new JButton( loginAction ) );
        
        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( authenticationPanel, BorderLayout.WEST );
        
        return topPanel;
    }
}
