package org.mbaum.common.view;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.model.MutableModelValue;

public class ModelView<M extends ModelSpec> implements View
{
	private final MutableModel<M> mModel;
	private final JComponent mPanel;
	private Listener<MutableModel<M>> mModelListener;
	
	public ModelView( MutableModel<M> model )
    {
		mModel = model;
		mPanel = buildPanel( mModel );
		mModelListener = createModelListener();
		mModel.addListener( mModelListener );
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

	private JComponent buildPanel( MutableModel<M> model )
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
		
		for ( MutableModelValue<M, ?> value : model )
		{
			JLabel label = new JLabel( value.getId().getName() + " :" );
			JComponent view = ModelValueViewBuilderFactory.Factories.getModelValueUIBuilder( value )
			                                                        .buildView()
			                                                        .getComponent();
			
			hLabelGroup.addComponent( label );
			hValueGroup.addComponent( view );
			
			vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
		                       	   .addComponent( label )
		                       	   .addComponent( view ) );
		}
		
		hGroup.addGroup( hLabelGroup );
		hGroup.addGroup( hValueGroup );

		layout.setHorizontalGroup( hGroup );
		layout.setVerticalGroup( vGroup );

		return panel;
    }
	
	private Listener<MutableModel<M>> createModelListener()
    {
	    return new Listener<MutableModel<M>>()
	    {
			@Override
            public void handleChanged( MutableModel<M> newValue )
            {
				updatePanel();
            }
	    };
    }
	
	private void updatePanel()
    {
        // TODO Auto-generated method stub
        
    }
}
