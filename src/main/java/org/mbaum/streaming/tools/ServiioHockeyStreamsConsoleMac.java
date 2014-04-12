package org.mbaum.streaming.tools;

import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.apple.eawt.AppEvent.QuitEvent;

public class ServiioHockeyStreamsConsoleMac
{
    public static void main( String[] args )
    {
        final ServiioHockeyStreamsConsole console = ServiioHockeyStreamsConsole.buildConsole();
        
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
}
