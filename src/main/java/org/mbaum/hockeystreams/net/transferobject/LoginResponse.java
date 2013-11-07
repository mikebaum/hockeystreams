package org.mbaum.hockeystreams.net.transferobject;


public class LoginResponse
{
    private String status;
    private String uid;
    private String username;
    private String favteam;
    private String membership;
    private String token;
    private String msg;
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus( String status )
    {
        this.status = status;
    }
    
    public String getUid()
    {
        return uid;
    }
    
    public void setUid( String uid )
    {
        this.uid = uid;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername( String username )
    {
        this.username = username;
    }
    
    public String getFavteam()
    {
        return favteam;
    }
    
    public void setFavteam( String favteam )
    {
        this.favteam = favteam;
    }
    
    public String getMembership()
    {
        return membership;
    }
    
    public void setMembership( String membership )
    {
        this.membership = membership;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken( String token )
    {
        this.token = token;
    }

    public String getMsg()
	{
		return msg;
	}

	public void setMsg( String msg )
	{
		this.msg = msg;
	}

	@Override
	public String toString()
	{
		return "LoginResponse [status=" + status + ", uid=" + uid
				+ ", username=" + username + ", favteam=" + favteam
				+ ", membership=" + membership + ", token=" + token + ", msg="
				+ msg + "]";
	}
}
