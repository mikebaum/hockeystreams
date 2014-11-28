package org.mbaum.hockeystreams.view;

import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;
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

import org.mbaum.common.model.MutableModel;
import org.mbaum.common.view.View;
import org.mbaum.hockeystreams.model.LoginPanelModel;

public class LoginPanel implements View
{
    private JPanel mPanel;
    private View mUsernameView;
    private View mPasswordView;

    public LoginPanel( MutableModel<LoginPanelModel> model, Action loginAction )
    {
        mUsernameView = textFieldBuilder( model.getModelValue( USERNAME ) ).buildView();
        mPasswordView = passwordFieldBuilder( model.getModelValue( PASSWORD ) ).buildView();
        mPanel = createLoginPanel( mUsernameView, mPasswordView, loginAction );
    }

    @SuppressWarnings("serial")
    public LoginPanel()
    {
        this( createMutableModel( LoginPanelModel.class ), new AbstractAction( "Login" )
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

    private static JPanel createLoginPanel( View usernameView, View passwordView, Action loginAction )
    {
        JPanel authenticationPanel = new JPanel();

        authenticationPanel.add( usernameView.getComponent() );
        authenticationPanel.add( passwordView.getComponent() );
        authenticationPanel.add( new JButton( loginAction ) );

        JPanel topPanel = new JPanel( new BorderLayout() );
        topPanel.add( authenticationPanel, BorderLayout.WEST );

        return topPanel;
    }
}
