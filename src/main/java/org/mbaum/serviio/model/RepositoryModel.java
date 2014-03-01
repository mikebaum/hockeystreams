package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.model.Model;
import org.mbaum.serviio.net.transferobject.OnlineRepository;
import org.mbaum.serviio.net.transferobject.SharedFolder;

public interface RepositoryModel extends Model<RepositoryModel>
{
	List<SharedFolder> getSharedFolders();

	Boolean getSearchHiddenFiles();

	Boolean getSearchForUpdates();

	Boolean getAutomaticLibraryUpdate();

	Integer getAutomaticLibraryUpdateInterval();

	List<OnlineRepository> getOnlineRepositories();

	Integer getMaxNumberOfItemsForOnlineFeeds();

	Integer getOnlineFeedExpiryInterval();

	String getOnlineContentPreferredQuality();
}