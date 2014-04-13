package org.mbaum.common.model;

import java.util.Iterator;

import org.mbaum.common.listener.Listener;

public class ForwardingModel<M extends Model<M>> implements Model<M>
{
    private final Model<M> mModel;
    
    public ForwardingModel( Model<M> model )
    {
        mModel = model;
    }

    @Override
    public void destroy()
    {
        mModel.destroy();
    }

    @Override
    public void addListener( Listener<Model<M>> listener )
    {
        mModel.addListener( listener );
    }

    @Override
    public void removeListener( Listener<Model<M>> listener )
    {
        mModel.removeListener( listener );
    }

    @Override
    public void clearListeners()
    {
        mModel.clearListeners();
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
}
