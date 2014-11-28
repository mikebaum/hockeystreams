package org.mbaum.common.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.reflections.Reflections;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class ComponentViewer
{
    private final JFrame mFrame;
    private final Set<Class<? extends View>> mViews;
    private JPanel mContentPane;
    private JPanel mViewSelectorPanel;
    private JComboBox<?> mViewComboBox;

    public ComponentViewer( Set<Class<? extends View>> views )
    {
        mViews = views;
        mFrame = new JFrame( "Ui Viewer" );
        buildGui();
    }

    private void buildGui()
    {
        mFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        mFrame.setSize( new Dimension( 500, 500 ) );

        mViewSelectorPanel = new JPanel();
        mViewSelectorPanel.add( new JLabel( "Select View: " ) );
        mViewComboBox = new JComboBox<Object>( mViews.toArray() );
        mViewSelectorPanel.add( mViewComboBox );
        mViewSelectorPanel.add( new JButton( createRefreshViewAction() ) );

        mContentPane = new JPanel( new BorderLayout() );
        mContentPane.add( mViewSelectorPanel, BorderLayout.NORTH );

        mFrame.setContentPane( mContentPane );
        mFrame.pack();
        mFrame.setVisible( true );
    }

    private void updateView( View  view )
    {
        if ( mCurrentView != null )
            mContentPane.remove( mCurrentView );

        mCurrentView = view.getComponent();

        System.out.println("preferred size: " + mCurrentView.getPreferredSize());
        System.out.println("maximum size: " + mCurrentView.getMaximumSize());
        System.out.println("minimum size: " + mCurrentView.getMinimumSize());

        mContentPane.add( mCurrentView, BorderLayout.WEST );
        mFrame.validate();
        mFrame.pack();
        mFrame.repaint();

        System.out.println("size size: " + mCurrentView.getSize());
    }

    @SuppressWarnings("serial")
    private AbstractAction createRefreshViewAction()
    {
        return new AbstractAction( "Load View" )
        {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                try
                {
                    @SuppressWarnings("unchecked")
                    Class<? extends View> clazz  = 
                    (Class<? extends View>) mViewComboBox.getSelectedItem();
                    updateView ( clazz.newInstance() );
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }};
    }

    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                new ComponentViewer( findViews() );
            }
        } );
    }

    private static Set<Class<? extends View>> findViews()
    {
        return FluentIterable.from( new Reflections().getSubTypesOf( View.class ) )
        .filter( HAS_DEFAULT_CONSTRUCTOR_PREDICATE )
        .toSet();
    }

    private static Predicate<Class<? extends View>> HAS_DEFAULT_CONSTRUCTOR_PREDICATE =
    new Predicate<Class<? extends View>>()
    {
        @Override
        public boolean apply( Class<? extends View> input )
        {
            for ( Constructor<?> constructor :  input.getConstructors() )
                if ( constructor.getParameterTypes().length == 0 )
                    return true;

            return false;
        }
    };
    private JComponent mCurrentView;
}
