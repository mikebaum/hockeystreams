package org.mbaum.common.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.mbaum.common.model.ModelListener;
import org.mbaum.common.model.ProgressPanelModel;

public class ProgressPanel
{
    private final JProgressBar mProgressBar;
    private final JComponent mPanel;
    
    public ProgressPanel( ProgressPanelModel model )
    {
        mProgressBar = createProgressBar();
        mPanel = initGui( mProgressBar );
        model.addListener( createProgressModelListener( mProgressBar ) );
    }

	public JComponent getComponent()
    {
        return mPanel;
    }

	private static JProgressBar createProgressBar()
	{
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted( true );
		progressBar.setString( "" );
		return progressBar;
	}
    
    private static JComponent initGui( JProgressBar progressBar )
    {
        JPanel panel = new JPanel( new BorderLayout() );
        
        panel.add( progressBar, BorderLayout.CENTER );
        panel.setBorder( BorderFactory.createTitledBorder( "Status" ) );
        
        return panel;
    }
    
    private static ModelListener<ProgressPanelModel> createProgressModelListener( final JProgressBar progressBar )
	{
		return new ModelListener<ProgressPanelModel>()
		{
			@Override
			public void modelChanged( ProgressPanelModel model )
			{
				progressBar.setIndeterminate( model.isIndeterminant() );
				progressBar.setString( model.getMessage() );
			}
		};
	}
}
