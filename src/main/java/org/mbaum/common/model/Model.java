package org.mbaum.common.model;

import org.apache.log4j.Logger;
import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;

import com.google.common.collect.ImmutableList;

public interface Model<M extends Model<M>> extends Destroyable, Listenable<Model<M>, Listener<Model<M>>>, Iterable<MutableModelValue<M, ?>>
{
    void reset();
    
    <T> MutableModelValue<M, T> getModelValue( ModelValueId<M, T> id );
    
    <T> T getValue( ModelValueId<M, T> id );
    
    <T> void setValue( ModelValueId<M, T> id, T value );
    
    ImmutableList<ModelValueId<M, ?>> getIds();
    

    static class Builder
    {
        private static final Logger LOGGER = Logger.getLogger( Builder.class );
        
        public static <M extends Model<M>> Model<M> createModel( Class<M> modelClass )
        {
            try
            {
                return new ModelImpl<M>( ModelValueIdUtils.getIds( modelClass ) );
            }
            catch ( IllegalAccessException exception )
            {
                LOGGER.error( "Failed to create model.", exception );
                return null;
            }
        }
    }
}
