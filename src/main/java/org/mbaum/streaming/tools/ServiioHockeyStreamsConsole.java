package org.mbaum.streaming.tools;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.mbaum.hockeystreams.HockeyStreamsComponent;
import org.mbaum.serviio.ServiioComponent;

public class ServiioHockeyStreamsConsole
{
	static final Logger LOGGER = Logger.getLogger( ServiioHockeyStreamsConsole.class );
	private static final int CONSOLE_MIN_HEIGHT = 120;

	private final JFrame mFrame;
	private final HockeyStreamsComponent mHockeyStremsComponent;
	private final JTabbedPane mConsoleTabbedPane;
	private final ServiioComponent mServiioComponent;

    public ServiioHockeyStreamsConsole()
	{
    	mFrame = new JFrame( "Serviio HockeyStreams Console" );
    	mConsoleTabbedPane = new JTabbedPane();
        mHockeyStremsComponent = new HockeyStreamsComponent( mFrame );
        mServiioComponent = new ServiioComponent( mFrame );
        
        mFrame.addWindowListener( createOnCloseListener() );
        
        buildAndShowConsole( mFrame, mConsoleTabbedPane, mHockeyStremsComponent, mServiioComponent );
	}
    
	private WindowListener createOnCloseListener()
    {
		return new WindowAdapter()
		{
			@Override
			public void windowClosing( WindowEvent e )
			{
				mHockeyStremsComponent.destroy();
				mServiioComponent.destroy();
			}
		};
    }

	private static void buildAndShowConsole( JFrame frame, 
											 JTabbedPane consoleTabbedPane, 
											 HockeyStreamsComponent hockeyStreamsComponent, 
											 ServiioComponent serviioComponent )
    {
	    initGui( frame, consoleTabbedPane, hockeyStreamsComponent, serviioComponent );
	    frame.pack();
	    frame.setVisible( true );
    }

	private static void initGui( JFrame frame, 
							     JTabbedPane consoleTabbedPane, 
							     HockeyStreamsComponent hockeyStreamsComponent, 
							     ServiioComponent serviioComponent )
	{
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    
	    JComponent hockeyStreamsView = hockeyStreamsComponent.getView().getComponent();
		consoleTabbedPane.addTab( "HockeyStreams", hockeyStreamsView );
		
	    JComponent serviioView = serviioComponent.getView().getComponent();
		consoleTabbedPane.addTab( "Serviio", serviioView );
	    
	    frame.setContentPane( consoleTabbedPane );
	    frame.setMinimumSize( new Dimension( Math.max( hockeyStreamsView.getPreferredSize().width, 
	                                                   serviioView.getPreferredSize().width ),
	                                         CONSOLE_MIN_HEIGHT ) );
	}
	
	public static void main( String[] args )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
			    new ServiioHockeyStreamsConsole();
			}
		} );
	}
}
