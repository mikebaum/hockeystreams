package org.mbaum.common.net.parse;

import org.apache.http.HttpResponse;

public interface ResponseParser<R>
{
	R parseResponse( HttpResponse response ) throws ResponseParseException;
}
