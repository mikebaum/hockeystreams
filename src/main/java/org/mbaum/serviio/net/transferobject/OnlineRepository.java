package org.mbaum.serviio.net.transferobject;

import java.util.List;

public class OnlineRepository
{
	private Integer id;
	private String repositoryType;
	private String contentUrl;
	private String fileType;
	private String repositoryName;
	private boolean enabled;
	private List<Integer> accessGroupIds;
	
	public OnlineRepository(){}
	
	private OnlineRepository( Integer id, 
							  String repositoryType,
							  String contentUrl, 
							  String fileType, 
							  String repositoryName,
							  boolean enabled, 
							  List<Integer> accessGroupIds )
	{
		this.id = id;
		this.repositoryType = repositoryType;
		this.contentUrl = contentUrl;
		this.fileType = fileType;
		this.repositoryName = repositoryName;
		this.enabled = enabled;
		this.accessGroupIds = accessGroupIds;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public String getRepositoryType()
	{
		return repositoryType;
	}

	public void setRepositoryType( String repositoryType )
	{
		this.repositoryType = repositoryType;
	}

	public String getContentUrl()
	{
		return contentUrl;
	}

	public void setContentUrl( String contentUrl )
	{
		this.contentUrl = contentUrl;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType( String fileType )
	{
		this.fileType = fileType;
	}

	public String getRepositoryName()
	{
		return repositoryName;
	}

	public void setRepositoryName( String repositoryName )
	{
		this.repositoryName = repositoryName;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled( boolean enabled )
	{
		this.enabled = enabled;
	}

	public List<Integer> getAccessGroupIds()
	{
		return accessGroupIds;
	}

	public void setAccessGroupIds( List<Integer> accessGroupIds )
	{
		this.accessGroupIds = accessGroupIds;
	}

	@Override
	public String toString()
	{
		return "OnlineRepository [id=" + id + ", repositoryType="
				+ repositoryType + ", contentUrl=" + contentUrl + ", fileType="
				+ fileType + ", repositoryName=" + repositoryName
				+ ", enabled=" + enabled + ", accessGroupIds=" + accessGroupIds
				+ "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return new OnlineRepository( id, 
		                             repositoryType, 
		                             contentUrl, 
		                             fileType, 
		                             repositoryName, 
		                             enabled, 
		                             accessGroupIds );
	}
}
