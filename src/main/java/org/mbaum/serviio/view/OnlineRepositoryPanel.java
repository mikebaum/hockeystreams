package org.mbaum.serviio.view;

import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;
import static org.mbaum.common.view.ModelValueViewBuilderFactory.Factories.getModelValueUIBuilder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.common.view.View;
import org.mbaum.serviio.model.OnlineRepositoryModel;

public class OnlineRepositoryPanel implements View
{
    private final JComponent mPanel;
    private final MutableModel<OnlineRepositoryModel> mModel;
    private Listener<MutableModel<OnlineRepositoryModel>> mModelListener;

    public OnlineRepositoryPanel( MutableModel<OnlineRepositoryModel> model )
    {
        mModel = model;
        mPanel = buildPanel( mModel );
        mModelListener = createModelListener();
        mModel.addListener( mModelListener );
    }

    public OnlineRepositoryPanel()
    {
        this( createMutableModel( OnlineRepositoryModel.class ) );
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

    private Listener<MutableModel<OnlineRepositoryModel>> createModelListener()
    {
        return new Listener<MutableModel<OnlineRepositoryModel>>()
        {
            @Override
            public void handleChanged( MutableModel<OnlineRepositoryModel> model )
            {
                updatePanel();
            }
        };
    }

    private JComponent buildPanel( MutableModel<OnlineRepositoryModel> model )
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

        for ( MutableModelValue<OnlineRepositoryModel, ?> value : model )
        {
            JLabel label = new JLabel( value.getId().getName() + " :" );
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
