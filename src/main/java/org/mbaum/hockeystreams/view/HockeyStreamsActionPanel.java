package org.mbaum.hockeystreams.view;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.mbaum.hockeystreams.model.HockeyStreamsModel;

public class HockeyStreamsActionPanel
{
    private final JComponent mActionToolBar;
    
    public HockeyStreamsActionPanel( Action ipExceptionAction, 
                                     Action getLiveStreamsAction, 
                                     HockeyStreamsModel model )
    {
        mActionToolBar = buildActionPanel( ipExceptionAction, getLiveStreamsAction, model );
    }
    
    public JComponent getComponent()
    {
        return mActionToolBar;
    }

    private static JComponent buildActionPanel( Action ipExceptionAction,
                                                Action getLiveStreamsAction,
                                                HockeyStreamsModel model )
    {
        JPanel panel = new JPanel( new BorderLayout() );
        panel.setBorder( BorderFactory.createTitledBorder( "HockeyStreams Actions" ) );
        
        JPanel buttonPanel = new JPanel( new BorderLayout() );
        JPanel buttons = new JPanel();
        
        buttons.add( new JButton( ipExceptionAction ) );
        buttons.add( new JButton( getLiveStreamsAction ) );
        
        buttonPanel.add( buttons, BorderLayout.NORTH );
        
        panel.add( buttonPanel, BorderLayout.WEST );
        
        return panel;
    }
}
