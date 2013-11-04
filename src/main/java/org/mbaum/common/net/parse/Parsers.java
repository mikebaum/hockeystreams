package org.mbaum.common.net.parse;

public final class Parsers
{
	private Parsers() {}

	public static <R> ResponseParser<R> newJsonParser( Class<R> resultClass )
	{
		return new JsonResponseParser<R>( resultClass );
	}
}
