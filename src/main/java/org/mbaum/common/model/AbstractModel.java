package org.mbaum.common.model;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractModel<M extends Model<M>> implements Model<M>
{
    private List<ModelListener<M>> mListeners = Lists.newArrayList();

    protected  AbstractModel() {}

    @Override
    public void addListener( ModelListener<M> listener )
    {
    	mListeners.add( listener );			
    }
    
    @Override
    public void removeListener( ModelListener<M> listener )
    {
        mListeners.remove( listener );
    }
    
    @Override
    public void destroy()
    {
        mListeners.clear();
    }

    protected void notifyListeners( M model )
    {
        for( ModelListener<M> listener : Lists.newArrayList( mListeners ) )
            listener.modelChanged( model );
    }
}
