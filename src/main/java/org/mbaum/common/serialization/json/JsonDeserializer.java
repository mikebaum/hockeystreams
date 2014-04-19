package org.mbaum.common.serialization.json;


public interface JsonDeserializer<R>
{
    R deserialize( String jsonString ) throws JsonException;
}
