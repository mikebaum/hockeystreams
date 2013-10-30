package org.mbaum.hockeystreams.net.hockeystreams.transferobject;

public class IpExceptionResponse
{
    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "IpExceptionResponse [status=" + status + "]";
    }
}
