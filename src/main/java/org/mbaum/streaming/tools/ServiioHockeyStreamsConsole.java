package org.mbaum.streaming.tools;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.mbaum.hockeystreams.HockeyStreamsComponent;
import org.mbaum.serviio.ServiioComponent;

import com.apple.eawt.AppEvent.QuitEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;

public class ServiioHockeyStreamsConsole
{
	static final Logger LOGGER = Logger.getLogger( ServiioHockeyStreamsConsole.class );
	private static final int CONSOLE_MIN_HEIGHT = 120;
    private final HockeyStreamsComponent mHockeyStreamsComponent;
    private final ServiioComponent mServiioComponent;

    public ServiioHockeyStreamsConsole()
	{
        JFrame frame = new JFrame( "Serviio HockeyStreams Console" );
        mHockeyStreamsComponent = new HockeyStreamsComponent( frame );
        mServiioComponent = new ServiioComponent( frame );
        
        frame.addWindowListener( createOnCloseListener() );
        
        buildAndShowConsole( frame, mHockeyStreamsComponent, mServiioComponent );
	}
    
    public void destroy()
    {
        mServiioComponent.destroy();
        mHockeyStreamsComponent.destroy();
    }
    
	private WindowListener createOnCloseListener()
    {
		return new WindowAdapter()
		{
			@Override
			public void windowClosing( WindowEvent e )
			{
			    destroy();
			}
		};
    }

	private static void buildAndShowConsole( JFrame frame, 
											 HockeyStreamsComponent hockeyStreamsComponent, 
											 ServiioComponent serviioComponent )
    {
	    initGui( frame, hockeyStreamsComponent, serviioComponent );
	    frame.pack();
	    frame.setVisible( true );
    }

	private static void initGui( JFrame frame, 
							     HockeyStreamsComponent hockeyStreamsComponent, 
							     ServiioComponent serviioComponent )
	{
	    JTabbedPane consoleTabbedPane = new JTabbedPane();
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
	
	public static ServiioHockeyStreamsConsole buildConsole()
	{
	    final AtomicReference<ServiioHockeyStreamsConsole> console = 
	        new AtomicReference<ServiioHockeyStreamsConsole>();
	    
	    try
        {
	        
            SwingUtilities.invokeAndWait( new Runnable()
            {
                @Override
                public void run()
                {
                    console.set( new ServiioHockeyStreamsConsole() );
                }
            } );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
	    
	    return console.get();
	}
	
	public static void main( String[] args )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
			    final ServiioHockeyStreamsConsole console = 
			        new ServiioHockeyStreamsConsole();
			    
	              Application.getApplication().setQuitHandler( new QuitHandler()
                  {
                      @Override
                      public void handleQuitRequestWith( QuitEvent arg0, QuitResponse arg1 )
                      {
                          console.destroy();
                          System.exit( 0 );
                      }
                  } );
			}
		} );
	}
}
