package org.mbaum.serviio.model;

import static org.mbaum.common.model.ModelValueIdUtils.createId;
import static org.mbaum.common.model.ModelValueIdUtils.createListModelId;

import java.util.List;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValueId;


public interface RepositoryModel extends Model<RepositoryModel>
{
	static final ModelValueId<RepositoryModel, List<Model<SharedFolderModel>>> SHARED_FOLDERS = createListModelId( "sharedFolders", SharedFolderModel.class );
	static final ModelValueId<RepositoryModel, Boolean> SEARCH_HIDDEN_FILES = createId( "searchHiddenFiles", false );
	static final ModelValueId<RepositoryModel, Boolean> SEARCH_FOR_UPDATES = createId( "searchForUpdates", false );
	static final ModelValueId<RepositoryModel, Boolean> AUTOMATIC_LIBRARY_UPATES = createId( "automaticLibraryUpdate", false );
	static final ModelValueId<RepositoryModel, Integer> AUTOMATIC_LIBRARY_UPDATE_INTERVAL = createId( "automaticLibraryUpdateInterval", 0 );
	static final ModelValueId<RepositoryModel, List<Model<OnlineRepositoryModel>>> ONLINE_REPOSITORIES = createListModelId( "onlineRepositories", OnlineRepositoryModel.class );
	static final ModelValueId<RepositoryModel, Integer> MAXIMUM_NUMBER_OF_ITEMS_FOR_ONLINE_FEEDS = createId( "maxNumberOfItemsForOnlineFeeds", 0 );
	static final ModelValueId<RepositoryModel, Integer> ONLINE_FEED_EXPIRY_INTERVAL = createId( "onlineFeedExpiryInterval", 0 );
	static final ModelValueId<RepositoryModel, String> ONLINE_CONTENT_PREFERRED_QUALITY = createId( "onlineContentPreferredQuality", "" );
}