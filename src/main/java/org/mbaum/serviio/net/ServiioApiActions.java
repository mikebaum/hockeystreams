package org.mbaum.serviio.net;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.net.HttpProcess;
import org.mbaum.common.net.parse.Parsers;
import org.mbaum.serviio.model.ServiioModel;
import org.mbaum.serviio.net.transferobject.PingResponse;

public final class ServiioApiActions
{
	private static final String PING_PATH = "/rest/ping";
	private static final String REPOSITORY_PATH = "/rest/repository";
	
	public static final Process<PingContext, PingResponse> 			 PING_PROCESS           = createPingProcess();
	
	private ServiioApiActions() {}

	private static Process<PingContext, PingResponse> createPingProcess()
	{
		return new HttpProcess<PingContext, PingResponse>( Parsers.newJsonParser( PingResponse.class ), "ping" )
		{
			@Override
			protected HttpUriRequest buildRequest( PingContext context ) 
			    throws NumberFormatException, URISyntaxException
			{
				ServiioModel serviioModel = context.getServiioModel();
				URI uri = new URIBuilder().setHost( serviioModel.getHostName() )
				                		  .setPort( Integer.parseInt( serviioModel.getPort() ) )
				                		  .setScheme( "http" )
				                		  .setPath( PING_PATH )
				                		  .build();
				
				return RequestBuilder.get().setUri( uri )
						                   .addHeader( "Accept", "application/json" )
						                   .build();
			}
		};
	}
	
	public static interface PingContext extends ProcessContext
	{
		ServiioModel getServiioModel();
	}
}
