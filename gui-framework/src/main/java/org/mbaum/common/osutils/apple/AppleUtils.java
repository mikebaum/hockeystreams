package org.mbaum.common.osutils.apple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

public class AppleUtils
{
	private static Logger LOGGER = Logger.getLogger( AppleUtils.class );
	
	private static Object getApplication() throws Exception
	{
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Class<?> applicationClass = classLoader.loadClass( "com.apple.eawt.Application" );
        return applicationClass.getMethod( "getApplication" ).invoke( null );
	}

	public static void setAppleQuitHandler( final Runnable quitRunnable )
	{
	    try
	    {
	        Class<?> quitHandlerClass = ClassLoader.getSystemClassLoader().loadClass( "com.apple.eawt.QuitHandler" );
	        
	        Object application = getApplication();
	        Method setQuitHandlerMethod = application.getClass().getMethod( "setQuitHandler", quitHandlerClass );
	        
	        Object quitHandler = Proxy.newProxyInstance( quitHandlerClass.getClassLoader(), 
	                                                     new Class[] { quitHandlerClass }, 
	                                                     new InvocationHandler()
	        {
	        	@Override
	        	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
	        	{
	        		quitRunnable.run();
	        		return null;
	        	}
	        } );
	        
	        setQuitHandlerMethod.invoke( application, quitHandler );
	    }
	    catch ( Exception exception )
	    {
	    	LOGGER.error( "Failed to set Apple QuitHandler.", exception );
	    }
	}

}
