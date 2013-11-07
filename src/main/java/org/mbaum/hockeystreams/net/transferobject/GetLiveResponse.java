package org.mbaum.hockeystreams.net.transferobject;

import java.util.List;

public class GetLiveResponse
{
    private String status;
    private List<LiveStream> schedule;
    
    public String getStatus()
    {
        return status;
    }
    public void setStatus( String status )
    {
        this.status = status;
    }
    public List<LiveStream> getSchedule()
    {
        return schedule;
    }
    public void setSchedule( List<LiveStream> schedule )
    {
        this.schedule = schedule;
    }
    
    @Override
    public String toString()
    {
        return "GetLiveResponse [status=" + status + ", schedule=" + schedule + "]";
    } 
}
