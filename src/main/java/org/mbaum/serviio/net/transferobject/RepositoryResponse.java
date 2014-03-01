package org.mbaum.serviio.net.transferobject;

import java.util.List;

public class RepositoryResponse
{
	private List<SharedFolder> sharedFolders;
	private boolean searchHiddenFiles;
	private boolean searchForUpdates;
	private boolean automaticLibraryUpdate;
	private int automaticLibraryUpdateInterval;
	private List<OnlineRepository> onlineRepositories;
	private int maxNumberOfItemsForOnlineFeeds;
	private int onlineFeedExpiryInterval;
	private String onlineContentPreferredQuality;

	public List<SharedFolder> getSharedFolders()
	{
		return sharedFolders;
	}

	public void setSharedFolders( List<SharedFolder> sharedFolders )
	{
		this.sharedFolders = sharedFolders;
	}

	public boolean isSearchHiddenFiles()
	{
		return searchHiddenFiles;
	}

	public void setSearchHiddenFiles( boolean searchHiddenFiles )
	{
		this.searchHiddenFiles = searchHiddenFiles;
	}

	public boolean isSearchForUpdates()
	{
		return searchForUpdates;
	}

	public void setSearchForUpdates( boolean searchForUpdates )
	{
		this.searchForUpdates = searchForUpdates;
	}

	public boolean isAutomaticLibraryUpdate()
	{
		return automaticLibraryUpdate;
	}

	public void setAutomaticLibraryUpdate( boolean automaticLibraryUpdate )
	{
		this.automaticLibraryUpdate = automaticLibraryUpdate;
	}

	public int getAutomaticLibraryUpdateInterval()
	{
		return automaticLibraryUpdateInterval;
	}

	public void setAutomaticLibraryUpdateInterval(
			int automaticLibraryUpdateInterval )
	{
		this.automaticLibraryUpdateInterval = automaticLibraryUpdateInterval;
	}

	public List<OnlineRepository> getOnlineRepositories()
	{
		return onlineRepositories;
	}

	public void setOnlineRepositories( List<OnlineRepository> onlineRepositories )
	{
		this.onlineRepositories = onlineRepositories;
	}

	public int getMaxNumberOfItemsForOnlineFeeds()
	{
		return maxNumberOfItemsForOnlineFeeds;
	}

	public void setMaxNumberOfItemsForOnlineFeeds(
			int maxNumberOfItemsForOnlineFeeds )
	{
		this.maxNumberOfItemsForOnlineFeeds = maxNumberOfItemsForOnlineFeeds;
	}

	public int getOnlineFeedExpiryInterval()
	{
		return onlineFeedExpiryInterval;
	}

	public void setOnlineFeedExpiryInterval( int onlineFeedExpiryInterval )
	{
		this.onlineFeedExpiryInterval = onlineFeedExpiryInterval;
	}

	public String getOnlineContentPreferredQuality()
	{
		return onlineContentPreferredQuality;
	}

	public void setOnlineContentPreferredQuality(
			String onlineContentPreferredQuality )
	{
		this.onlineContentPreferredQuality = onlineContentPreferredQuality;
	}

	@Override
	public String toString()
	{
		return "RepositoryResponse [sharedFolders=" + sharedFolders
				+ ", searchHiddenFiles=" + searchHiddenFiles
				+ ", searchForUpdates=" + searchForUpdates
				+ ", automaticLibraryUpdate=" + automaticLibraryUpdate
				+ ", automaticLibraryUpdateInterval="
				+ automaticLibraryUpdateInterval + ", onlineRepositories="
				+ onlineRepositories + ", maxNumberOfItemsForOnlineFeeds="
				+ maxNumberOfItemsForOnlineFeeds
				+ ", onlineFeedExpiryInterval=" + onlineFeedExpiryInterval
				+ ", onlineContentPreferredQuality="
				+ onlineContentPreferredQuality + "]";
	}
}
