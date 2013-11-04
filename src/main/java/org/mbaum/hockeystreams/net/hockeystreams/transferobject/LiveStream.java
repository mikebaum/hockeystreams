package org.mbaum.hockeystreams.net.hockeystreams.transferobject;

import org.codehaus.jackson.annotate.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveStream
{
    private String id;
    private String event;
    private String showScores;
    private String homeTeam;
    private String homeScore;
    private String awayTeam;
    private String awayScore;
    private String startTime;
    private String period;
    private String isHd;
    private String isPlaying;
    private String isWMV;
    private String isFlash;
    private String isStream;
    private String isiStream;
    private String feedType;
    private String srcUrl;
    private String hdUrl;
    private String sdUrl;
    private String TrueLiveSD;
    private String TrueLiveHD;
    
    public String getId()
    {
        return id;
    }
    public void setId( String id )
    {
        this.id = id;
    }
    public String getEvent()
    {
        return event;
    }
    public void setEvent( String event )
    {
        this.event = event;
    }
    public String getShowScores()
    {
        return showScores;
    }
    public void setShowScores( String showScores )
    {
        this.showScores = showScores;
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public void setHomeTeam( String homeTeam )
    {
        this.homeTeam = homeTeam;
    }
    public String getHomeScore()
    {
        return homeScore;
    }
    public void setHomeScore( String homeScore )
    {
        this.homeScore = homeScore;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public void setAwayTeam( String awayTeam )
    {
        this.awayTeam = awayTeam;
    }
    public String getAwayScore()
    {
        return awayScore;
    }
    public void setAwayScore( String awayScore )
    {
        this.awayScore = awayScore;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime( String startTime )
    {
        this.startTime = startTime;
    }
    public String getPeriod()
    {
        return period;
    }
    public void setPeriod( String period )
    {
        this.period = period;
    }
    public String getIsHd()
    {
        return isHd;
    }
    public void setIsHd( String isHd )
    {
        this.isHd = isHd;
    }
    public String getIsPlaying()
    {
        return isPlaying;
    }
    public void setIsPlaying( String isPlaying )
    {
        this.isPlaying = isPlaying;
    }
    public String getIsWMV()
    {
        return isWMV;
    }
    public void setIsWMV( String isWMV )
    {
        this.isWMV = isWMV;
    }
    public String getIsFlash()
    {
        return isFlash;
    }
    public void setIsFlash( String isFlash )
    {
        this.isFlash = isFlash;
    }
    public String getIsStream()
    {
        return isStream;
    }
    public void setIsStream( String isStream )
    {
        this.isStream = isStream;
    }
    public String getIsiStream()
    {
        return isiStream;
    }
    public void setIsiStream( String isiStream )
    {
        this.isiStream = isiStream;
    }
    public String getFeedType()
    {
        return feedType;
    }
    public void setFeedType( String feedType )
    {
        this.feedType = feedType;
    }
    public String getSrcUrl()
    {
        return srcUrl;
    }
    public void setSrcUrl( String srcUrl )
    {
        this.srcUrl = srcUrl;
    }
    public String getHdUrl()
    {
        return hdUrl;
    }
    public void setHdUrl( String hdUrl )
    {
        this.hdUrl = hdUrl;
    }
    public String getSdUrl()
    {
        return sdUrl;
    }
    public void setSdUrl( String sdUrl )
    {
        this.sdUrl = sdUrl;
    }
    
    @JsonProperty("TrueLiveSD")
    public String getTrueLiveSD()
    {
        return TrueLiveSD;
    }
    public void setTrueLiveSD( String trueLiveSD )
    {
        TrueLiveSD = trueLiveSD;
    }
    
    @JsonProperty("TrueLiveHD")
    public String getTrueLiveHD()
    {
        return TrueLiveHD;
    }
    public void setTrueLiveHD( String trueLiveHd )
    {
        TrueLiveHD = trueLiveHd;
    }
    
    @Override
    public String toString()
    {
        return "LiveStream [id=" + id + ", event=" + event + ", showScores=" + showScores +
               ", homeTeam=" + homeTeam + ", homeScore=" + homeScore + ", awayTeam=" + awayTeam +
               ", awayScore=" + awayScore + ", startTime=" + startTime + ", period=" + period +
               ", isHd=" + isHd + ", isPlaying=" + isPlaying + ", isWMV=" + isWMV + ", isFlash=" +
               isFlash + ", isStream=" + isStream + ", feedType=" + feedType + ", srcUrl=" +
               srcUrl + ", hdUrl=" + hdUrl + ", sdUrl=" + sdUrl + ", TrueLiveSD=" + TrueLiveSD +
               ", TrueLiveHd=" + TrueLiveHD + "]";
    }
}
