package org.mbaum.common.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.mbaum.common.execution.AbstractProcess;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.net.parse.ResponseParser;

public abstract class HttpProcess<C extends ProcessContext, R> extends AbstractProcess<C, R> implements Process<C, R>
{
	private static final Logger LOGGER = Logger.getLogger( HttpProcess.class );
	private final ResponseParser<R> mResponseParser;
	
	protected HttpProcess( ResponseParser<R> responseParser, String description )
	{
		super( description );
		mResponseParser = responseParser;
	}
	
	@Override
	public final R execute( C context ) throws Exception
	{
		HttpClient client = HttpClients.createDefault();
		HttpUriRequest request = buildRequest( context );
		
		String requestLogString = request.getMethod() + " request: " + request.getURI().getPath();
		LOGGER.info( "Executing " + requestLogString );
		
		HttpResponse response = client.execute( request );
		
		LOGGER.info( requestLogString + " complete" );
		
		return mResponseParser.parseResponse( response );
	}
	
	protected abstract HttpUriRequest buildRequest( C context );
}
