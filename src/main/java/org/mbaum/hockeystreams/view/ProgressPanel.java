package org.mbaum.hockeystreams.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressPanel
{
    private final JProgressBar mProgressBar;
    private final JComponent mPanel;
    
    public ProgressPanel()
    {
        mProgressBar = new JProgressBar();
        mPanel = initGui( mProgressBar );
    }
    
    public JComponent getComponent()
    {
        return mPanel;
    }
    
    private static JComponent initGui( JProgressBar progressBar )
    {
        JPanel panel = new JPanel( new BorderLayout() );
        
        panel.add( progressBar, BorderLayout.CENTER );
        panel.setBorder( BorderFactory.createTitledBorder( "Status" ) );
        
        return panel;
    }
}
