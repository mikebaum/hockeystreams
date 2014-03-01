package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.ModelValue;
import org.mbaum.serviio.net.transferobject.OnlineRepository;
import org.mbaum.serviio.net.transferobject.SharedFolder;

public class RepositoryModelImpl extends AbstractModel<RepositoryModel> implements RepositoryModel
{
	private final ModelValue<List<SharedFolder>> mSharedFolders;
	private final ModelValue<Boolean> mSearchHiddenFiles;
	private final ModelValue<Boolean> mSearchForUpdates;
	private final ModelValue<Boolean> mAutomaticLibraryUpdate;
	private final ModelValue<Integer> mAutomaticLibraryUpdateInterval;
	private final ModelValue<List<OnlineRepository>> mOnlineRepositories;
	private final ModelValue<Integer> mMaxNumberOfItemsForOnlineFeeds;
	private final ModelValue<Integer> mOnlineFeedExpiryInterval;
	private final ModelValue<String> mOnlineContentPreferredQuality;
	
	public RepositoryModelImpl()
    {
		mSharedFolders = newModelValue();
		mSearchHiddenFiles = newModelValue();
		mSearchForUpdates = newModelValue();
		mAutomaticLibraryUpdate = newModelValue();
		mAutomaticLibraryUpdateInterval = newModelValue();
		mOnlineRepositories = newModelValue();
		mMaxNumberOfItemsForOnlineFeeds = newModelValue();
		mOnlineFeedExpiryInterval = newModelValue();
		mOnlineContentPreferredQuality = newModelValue();
    }

	public List<SharedFolder> getSharedFolders()
	{
		return mSharedFolders.get();
	}

	public void setSharedFolders( List<SharedFolder> sharedFolders )
	{
		mSharedFolders.set( sharedFolders );
	}

	public Boolean getSearchHiddenFiles()
	{
		return mSearchHiddenFiles.get();
	}

	public void setSearchHiddenFiles( Boolean searchHiddenFiles )
	{
		mSearchHiddenFiles.set( searchHiddenFiles );
	}

	public Boolean getSearchForUpdates()
	{
		return mSearchForUpdates.get();
	}

	public void setSearchForUpdates( Boolean searchForUpdates )
	{
		mSearchForUpdates.set( searchForUpdates );
	}

	public Boolean getAutomaticLibraryUpdate()
	{
		return mAutomaticLibraryUpdate.get();
	}

	public void setAutomaticLibraryUpdate( Boolean automaticLibraryUpdate )
	{
		mAutomaticLibraryUpdate.set( automaticLibraryUpdate );
	}

	public Integer getAutomaticLibraryUpdateInterval()
	{
		return mAutomaticLibraryUpdateInterval.get();
	}

	public void setAutomaticLibraryUpdateInterval( Integer automaticLibraryUpdateInterval )
	{
		mAutomaticLibraryUpdateInterval.set( automaticLibraryUpdateInterval );
	}

	public List<OnlineRepository> getOnlineRepositories()
	{
		return mOnlineRepositories.get();
	}

	public void setOnlineRepositories( List<OnlineRepository> onlineRepositories )
	{
		mOnlineRepositories.set( onlineRepositories );
	}

	public Integer getMaxNumberOfItemsForOnlineFeeds()
	{
		return mMaxNumberOfItemsForOnlineFeeds.get();
	}

	public void setMaxNumberOfItemsForOnlineFeeds( Integer maxNumberOfItemsForOnlineFeeds )
	{
		mMaxNumberOfItemsForOnlineFeeds.set( maxNumberOfItemsForOnlineFeeds );
	}

	public Integer getOnlineFeedExpiryInterval()
	{
		return mOnlineFeedExpiryInterval.get();
	}

	public void setOnlineFeedExpiryInterval( Integer onlineFeedExpiryInterval )
	{
		mOnlineFeedExpiryInterval.set( onlineFeedExpiryInterval );
	}

	public String getOnlineContentPreferredQuality()
	{
		return mOnlineContentPreferredQuality.get();
	}

	public void setOnlineContentPreferredQuality( String onlineContentPreferredQuality )
	{
		mOnlineContentPreferredQuality.set( onlineContentPreferredQuality );
	}
}
