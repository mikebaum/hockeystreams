package org.mbaum.common.view;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ActionPanel
{
    private final JComponent mActionToolBar;
    
    public ActionPanel( Action... actions )
    {
        mActionToolBar = buildActionPanel( actions );
    }
    
    public JComponent getComponent()
    {
        return mActionToolBar;
    }

    private static JComponent buildActionPanel( Action... actions )
    {
        JPanel panel = new JPanel( new BorderLayout() );
        panel.setBorder( BorderFactory.createTitledBorder( "HockeyStreams Actions" ) );
        
        JPanel buttonPanel = new JPanel( new BorderLayout() );
        JPanel buttons = new JPanel();
        
        for ( Action action : actions )
        	buttons.add( new JButton( action ) );
        
        buttonPanel.add( buttons, BorderLayout.NORTH );
        
        panel.add( buttonPanel, BorderLayout.WEST );
        
        return panel;
    }
}
