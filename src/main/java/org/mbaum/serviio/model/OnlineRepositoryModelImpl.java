package org.mbaum.serviio.model;

import java.util.List;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.MutableModelValue;

import com.google.common.collect.Lists;

public class OnlineRepositoryModelImpl extends AbstractModel<OnlineRepositoryModel.Id<?>, OnlineRepositoryModel> implements OnlineRepositoryModel
{
	public OnlineRepositoryModelImpl()
    {
		super();
		newModelValue( ID, 0, "Id", this );
		newModelValue( REPOSITORY_TYPE, "", "Repository Type", this );
		newModelValue( CONTENT_URL, "", "Url", this );
		newModelValue( FILE_TYPE, "", "File Type", this );
		newModelValue( REPOSITORY_NAME, "", "Name", this );
		newModelValue( ENABLED, false, "Enabled", this );
		newModelValue( ACCESS_GROUP_IDS, (List<Integer>) Lists.<Integer>newArrayList(), "Access Group Ids", this );
    }
	
	@Override
    public <T> MutableModelValue<T> getModelValue( Id<T> id )
    {
	    return super.getModelValue( id );
    }

	@Override
    public <T> T getValue( Id<T> id )
    {
	    return getModelValue( id ).get();
    }

	@Override
    public <T> void setValue( Id<T> id, T value )
    {
		getModelValue( id ).set( value );
    }

	@Override
    protected ListenableSupport<OnlineRepositoryModel, Listener<OnlineRepositoryModel>> createListenableSupport()
    {
	    return createModelListenerSupport( (OnlineRepositoryModel) this );
    }
}
