package org.mbaum.common.net.parse;

@SuppressWarnings("serial")
public class ResponseParseException extends Exception
{
	public ResponseParseException() {}

	public ResponseParseException( String message )
	{
		super( message );
	}

	public ResponseParseException( Throwable throwable )
	{
		super( throwable );
	}

	public ResponseParseException( String message, Throwable throwable )
	{
		super( message, throwable );
	}
}
