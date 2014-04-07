package org.mbaum.serviio.view;

import static org.mbaum.common.view.ModelValueViewBuilderFactory.Factories.getModelValueUIBuilder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.common.view.View;
import org.mbaum.serviio.model.OnlineRepositoryModel;
import org.mbaum.serviio.model.OnlineRepositoryModelImpl;

public class OnlineRepositoryPanel implements View
{
	private final JComponent mPanel;
	private final OnlineRepositoryModel mModel;
	private Listener<OnlineRepositoryModel> mModelListener;
	
	public OnlineRepositoryPanel( OnlineRepositoryModel model )
	{
		mModel = model;
		mPanel = buildPanel( mModel );
		mModelListener = createModelListener();
		mModel.addListener( mModelListener );
	}
	
	public OnlineRepositoryPanel()
	{
		this( new OnlineRepositoryModelImpl() );
	}
	
	@Override
	public void destroy()
	{
		mModel.removeListener( mModelListener );
	}
	
	@Override
	public JComponent getComponent()
    {
	    return mPanel;
    }
	
	private Listener<OnlineRepositoryModel> createModelListener()
    {
	    return new Listener<OnlineRepositoryModel>()
	    {
	    	@Override
	    	public void handleChanged( OnlineRepositoryModel model )
	    	{
	    		updatePanel();
	    	}
	    };
    }

	private JComponent buildPanel( OnlineRepositoryModel model )
    {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout( panel );
		layout.setAutoCreateGaps( true );
		layout.setAutoCreateContainerGaps( true );

		panel.setLayout( layout );
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		ParallelGroup hLabelGroup = layout.createParallelGroup( GroupLayout.Alignment.TRAILING );
		ParallelGroup hValueGroup = layout.createParallelGroup();
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		
		for ( MutableModelValue<?> value : model )
		{
			JLabel label = new JLabel( value.getDescription() + " :" );
			JComponent valueComponent = getModelValueUIBuilder( value ).buildView().getComponent();
			
			hLabelGroup.addComponent( label );
			hValueGroup.addComponent( valueComponent );
			
			vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
		                       	   .addComponent( label )
		                       	   .addComponent( valueComponent ) );
		}
		
		hGroup.addGroup( hLabelGroup );
		hGroup.addGroup( hValueGroup );

		layout.setHorizontalGroup( hGroup );
		layout.setVerticalGroup( vGroup );

		return panel;
    }
	
	private void updatePanel()
    {
		
    }
}
