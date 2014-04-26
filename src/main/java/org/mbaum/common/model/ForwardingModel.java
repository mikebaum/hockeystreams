package org.mbaum.common.model;

import java.util.Iterator;

import org.mbaum.common.listener.Listener;

import com.google.common.collect.ImmutableSet;

public class ForwardingModel<M extends ModelSpec> implements MutableModel<M>
{
    private final MutableModel<M> mModel;
    
    public ForwardingModel( MutableModel<M> model )
    {
        mModel = model;
    }

    @Override
    public void destroy()
    {
        mModel.destroy();
    }

    @Override
    public void addListener( Listener<MutableModel<M>> listener )
    {
        mModel.addListener( listener );
    }

    @Override
    public void removeListener( Listener<MutableModel<M>> listener )
    {
        mModel.removeListener( listener );
    }

    @Override
    public Iterator<MutableModelValue<M, ?>> iterator()
    {
        return mModel.iterator();
    }

    @Override
    public void reset()
    {
        mModel.reset();
    }

    @Override
    public <T> MutableModelValue<M, T> getModelValue( ModelValueId<M, T> id )
    {
        return mModel.getModelValue( id );
    }

    @Override
    public <T> T getValue( ModelValueId<M, T> id )
    {
        return mModel.getValue( id );
    }

    @Override
    public <T> void setValue( ModelValueId<M, T> id, T value )
    {
        mModel.setValue( id, value );
    }

    @Override
    public ImmutableSet<ModelValueId<M, ?>> getIds()
    {
        return mModel.getIds();
    }
}
