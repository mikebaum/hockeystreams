package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.serviio.net.transferobject.OnlineRepository;
import org.mbaum.serviio.net.transferobject.SharedFolder;

import com.google.common.collect.Lists;

public class RepositoryModelImpl extends AbstractModel<RepositoryModel> implements RepositoryModel
{
	public RepositoryModelImpl()
    {
		super();
		newModelValue( SHARED_FOLDERS, (List<SharedFolder>) Lists.<SharedFolder>newArrayList(), "Shared Folders", this );
		newModelValue( SEARCH_HIDDEN_FILES, false, "Search Hidden Files", this );
		newModelValue( SEARCH_FOR_UPDATES, false, "Search For Updates", this );
		newModelValue( AUTOMATIC_LIBRARY_UPATES, false, "Automatic Library Update", this );
		newModelValue( AUTOMATIC_LIBRARY_UPDATE_INTERVAL, 0, "Automatic Library Update Interval", this );
		newModelValue( ONLINE_REPOSITORIES, (List<OnlineRepository>) Lists.<OnlineRepository>newArrayList(), "Online Repositories", this );
		newModelValue( MAXIMUM_NUMBER_OF_ITEMS_FOR_ONLINE_FEEDS, 0, "Max Number of Items for Online Feeds", this );
		newModelValue( ONLINE_FEED_EXPIRY_INTERVAL, 0, "Online Feed Expiry Interval", this );
		newModelValue( ONLINE_CONTENT_PREFERRED_QUALITY, "", "Online Content Preferred Quality", this );
    }

	@Override
    protected ListenableSupport<RepositoryModel, Listener<RepositoryModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (RepositoryModel) this );
    }
}
