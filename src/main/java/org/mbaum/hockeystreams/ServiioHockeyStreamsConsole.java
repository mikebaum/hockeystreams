package org.mbaum.hockeystreams;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

public class ServiioHockeyStreamsConsole
{
	static final Logger LOGGER = Logger.getLogger( ServiioHockeyStreamsConsole.class );
	private static final int CONSOLE_MIN_HEIGHT = 120;

	private final JFrame mFrame;
	private final HockeyStreamsComponent mHockeyStremsComponent;

    public ServiioHockeyStreamsConsole()
	{
    	mFrame = new JFrame( "Serviio HockeyStreams Console" );
        mHockeyStremsComponent = new HockeyStreamsComponent( mFrame );
        buildAndShowConsole( mFrame, mHockeyStremsComponent);
	}
    
	private static void buildAndShowConsole( JFrame frame, HockeyStreamsComponent hockeyStreamsComponent )
    {
	    initGui( frame, hockeyStreamsComponent );
	    frame.pack();
	    frame.setVisible( true );
    }

	private static void initGui( JFrame frame, HockeyStreamsComponent hockeyStreamsComponent )
	{
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    JComponent hockeyStreamsPanel = hockeyStreamsComponent.getComponent();
	    frame.setContentPane( hockeyStreamsPanel );
	    frame.setMinimumSize( new Dimension( hockeyStreamsPanel.getPreferredSize().width,
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
