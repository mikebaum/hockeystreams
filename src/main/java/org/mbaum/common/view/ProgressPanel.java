package org.mbaum.common.view;

import static org.mbaum.common.model.ProgressPanelModel.INDETERMINATE;
import static org.mbaum.common.model.ProgressPanelModel.MESSAGE;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.model.ProgressPanelModel;

public class ProgressPanel implements View
{
    private final JProgressBar mProgressBar;
    private final JComponent mPanel;
    
    public ProgressPanel( MutableModel<ProgressPanelModel> model )
    {
        mProgressBar = createProgressBar();
        mPanel = initGui( mProgressBar );
        model.addListener( createProgressModelListener( mProgressBar ) );
    }
    
	@Override
    public void destroy()
    {
	    // TODO Auto-generated method stub
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
    
    private static Listener<MutableModel<ProgressPanelModel>> createProgressModelListener( final JProgressBar progressBar )
	{
		return new Listener<MutableModel<ProgressPanelModel>>()
		{
			@Override
            public void handleChanged( MutableModel<ProgressPanelModel> model )
            {
				progressBar.setIndeterminate( model.getValue( INDETERMINATE ) );
				progressBar.setString( model.getValue( MESSAGE ) );
            }
		};
	}
}
