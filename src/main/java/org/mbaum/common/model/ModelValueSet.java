package org.mbaum.common.model;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

class ModelValueSet<M extends ModelSpec, V extends ModelValue<M, ?>>
{
    private final Map<ModelValueId<M, ?>, ModelValueHolder<M, V>> mModelValues = 
        Maps.newIdentityHashMap();
    
    public void putModelValue( V modelValue )
    {
        ModelValueHolder<M, V> modelValueHolder = new ModelValueHolder<M, V>( modelValue );
        mModelValues.put( modelValue.getId(), modelValueHolder );
    }
    
    public V getModelValue( ModelValueId<M, ?> id )
    {
        ModelValueHolder<M, V> modelValueHolder = (ModelValueHolder<M, V>) mModelValues.get( id );
        Preconditions.checkArgument( modelValueHolder != null, "There is no model value for the id: " + id );
        
        return modelValueHolder.getModelValue();
    }

    public ImmutableSet<ModelValueId<M, ?>> getIds()
    {
        return ImmutableSet.copyOf( mModelValues.keySet() );
    }

    public ImmutableSet<V> values()
    {
        ImmutableSet.Builder<V> values = ImmutableSet.builder();
        
        for ( ModelValueHolder<M, V> holder : mModelValues.values() )
            values.add( holder.getModelValue() );
        
        return values.build();
    }

    private static class ModelValueHolder<M extends ModelSpec, V extends ModelValue<M, ?>>
    {
        private final V mModelValue;
        
        public ModelValueHolder( V modelValue )
        {
            mModelValue = modelValue;
        }
        
        public V getModelValue()
        {
            return mModelValue;
        }
    }
}