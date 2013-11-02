package org.mbaum.hockeystreams.net;

public class HockeyStreamsApiException extends Exception
{
    public HockeyStreamsApiException()
    {
        super();
    }

    public HockeyStreamsApiException( String arg0, Throwable arg1 )
    {
        super( arg0, arg1 );
    }

    public HockeyStreamsApiException( String arg0 )
    {
        super( arg0 );
    }

    public HockeyStreamsApiException( Throwable arg0 )
    {
        super( arg0 );
    }
}
