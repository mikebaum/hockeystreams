package org.mbaum.common.net.parse;

import org.mbaum.common.model.Model;
import org.mbaum.common.serialization.json.JsonDeserializers;

public final class ResponseParsers
{
	private ResponseParsers() {}

	public static <R> ResponseParser<R> newJsonParser( Class<R> resultClass )
	{
		return new JsonResponseParser<R>( resultClass );
	}
	
	public static <R extends Model<R>> ResponseParser<Model<R>> newModelJsonParser( Class<R> modelResultClass )
	{
	    return new JsonResponseParser<Model<R>>( JsonDeserializers.createModelDeserializer( modelResultClass ) );
	}
}
