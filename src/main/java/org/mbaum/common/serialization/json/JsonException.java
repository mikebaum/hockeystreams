package org.mbaum.common.serialization.json;

public class JsonException extends Exception
{
    public JsonException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}
