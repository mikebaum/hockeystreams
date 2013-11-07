package org.mbaum.hockeystreams;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
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
        
        buildAndShowConsole( mFrame, mConsoleTabbedPane, mHockeyStremsComponent, mServiioComponent );
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
	    
	    consoleTabbedPane.addTab( "HockeyStreams", hockeyStreamsComponent.getComponent() );
	    consoleTabbedPane.addTab( "Serviio", serviioComponent.getComponent() );
	    
	    frame.setContentPane( consoleTabbedPane );
	    frame.setMinimumSize( new Dimension( hockeyStreamsComponent.getComponent().getPreferredSize().width,
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
