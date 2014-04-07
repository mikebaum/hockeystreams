package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.serviio.model.RepositoryModel.Id;
import org.mbaum.serviio.net.transferobject.OnlineRepository;
import org.mbaum.serviio.net.transferobject.SharedFolder;
public interface RepositoryModel extends Model<Id<?>, RepositoryModel>
{
	public interface Id<T> extends ModelValueId<T>{}
	
	public static final Id<List<SharedFolder>> SHARED_FOLDERS = new Id<List<SharedFolder>>(){};
	public static final Id<Boolean> SEARCH_HIDDEN_FILES = new Id<Boolean>(){};
	public static final Id<Boolean> SEARCH_FOR_UPDATES = new Id<Boolean>(){};
	public static final Id<Boolean> AUTOMATIC_LIBRARY_UPATES = new Id<Boolean>(){};
	public static final Id<Integer> AUTOMATIC_LIBRARY_UPDATE_INTERVAL = new Id<Integer>(){};
	public static final Id<List<OnlineRepository>> ONLINE_REPOSITORIES = new Id<List<OnlineRepository>>(){};
	public static final Id<Integer> MAXIMUM_NUMBER_OF_ITEMS_FOR_ONLINE_FEEDS = new Id<Integer>(){};
	public static final Id<Integer> ONLINE_FEED_EXPIRY_INTERVAL = new Id<Integer>(){};
	public static final Id<String> ONLINE_CONTENT_PREFERRED_QUALITY = new Id<String>(){};
	
	<T> MutableModelValue<T> getModelValue( Id<T> id );
	
	<T> T getValue( Id<T> id );
	
	<T> void setValue( Id<T> id, T value );
}