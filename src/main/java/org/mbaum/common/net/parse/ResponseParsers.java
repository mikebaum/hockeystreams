package org.mbaum.common.net.parse;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.serialization.json.JsonDeserializers;

public final class ResponseParsers
{
	private ResponseParsers() {}

	public static <R> ResponseParser<R> newJsonParser( Class<R> resultClass )
	{
		return new JsonResponseParser<R>( resultClass );
	}
	
	public static <M extends ModelSpec> ResponseParser<MutableModel<M>> newMutableModelJsonParser( Class<M> modelResultClass )
	{
	    return new JsonResponseParser<MutableModel<M>>( JsonDeserializers.createMutableModelDeserializer( modelResultClass ) );
	}
}
