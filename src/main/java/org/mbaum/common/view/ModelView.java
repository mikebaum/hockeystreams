package org.mbaum.common.view;

import static org.mbaum.common.view.ModelValueViewBuilderFactory.Factories.getModelValueUIBuilder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue;

public class ModelView<M extends Model<? extends ModelValueId<?>, M>> implements View
{
	private final M mModel;
	private final JComponent mPanel;
	private Listener<M> mModelListener;
	
	public ModelView( M model )
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

	private JComponent buildPanel( M model )
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
			JComponent view = getModelValueUIBuilder( value ).buildView().getComponent();
			
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
	
	private Listener<M> createModelListener()
    {
	    return new Listener<M>()
	    {
			@Override
            public void handleChanged( M newValue )
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
