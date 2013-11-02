package org.mbaum.serviio.net.transferobject;

public class PingResponse
{
    private String errorCode;
    private String httpCode;
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode( String errorCode )
    {
        this.errorCode = errorCode;
    }
    
    public String getHttpCode()
    {
        return httpCode;
    }
    
    public void setHttpCode( String httpCode )
    {
        this.httpCode = httpCode;
    }

    @Override
    public String toString()
    {
        return "PingResponse [errorCode=" + errorCode + ", httpCode=" + httpCode + "]";
    }
}
