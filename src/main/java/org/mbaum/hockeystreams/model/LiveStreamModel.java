package org.mbaum.hockeystreams.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;

public class LiveStreamModel implements ModelSpec
{
    public static final ModelValueId<LiveStreamModel, String> ID           = createId( "id", "" );
    public static final ModelValueId<LiveStreamModel, String> EVENT        = createId( "event", "" );
    public static final ModelValueId<LiveStreamModel, String> HOME_TEAM    = createId( "homeTeam", "" );
    public static final ModelValueId<LiveStreamModel, String> HOME_SCORE   = createId( "homeScore", "" );
    public static final ModelValueId<LiveStreamModel, String> AWAY_TEAM    = createId( "awayTeam", "" );
    public static final ModelValueId<LiveStreamModel, String> AWAY_SCORE   = createId( "awayScore", "" );
    public static final ModelValueId<LiveStreamModel, String> START_TIME   = createId( "startTime", "" );
    public static final ModelValueId<LiveStreamModel, String> PERIOD       = createId( "period", "" );
    public static final ModelValueId<LiveStreamModel, String> IS_HD        = createId( "isHd", "0" );
    public static final ModelValueId<LiveStreamModel, String> IS_PLAYING   = createId( "isPlaying", "0" );
    public static final ModelValueId<LiveStreamModel, String> IS_WMV       = createId( "isWMV", "0" );
    public static final ModelValueId<LiveStreamModel, String> IS_FLASH     = createId( "isFlash", "0" );
    public static final ModelValueId<LiveStreamModel, String> IS_STREAM    = createId( "isStream", "0" );
    public static final ModelValueId<LiveStreamModel, String> IS_ISTREAM   = createId( "isiStream", "0" );
    // TODO: we need to allow null default values
    public static final ModelValueId<LiveStreamModel, String> FEED_TYPE    = createId( "feedType", "null" );
    public static final ModelValueId<LiveStreamModel, String> SRC_URL      = createId( "srcUrl", "" );
    public static final ModelValueId<LiveStreamModel, String> HD_URL       = createId( "hdUrl", "" );
    public static final ModelValueId<LiveStreamModel, String> SD_URL       = createId( "sdUrl", "" );
    public static final ModelValueId<LiveStreamModel, String> TRUE_LIVE_SD = createId( "TrueLiveSD", "" );
    public static final ModelValueId<LiveStreamModel, String> TRUE_LIVE_HD = createId( "TrueLiveHD", "" );
    
    private LiveStreamModel() {}
}
