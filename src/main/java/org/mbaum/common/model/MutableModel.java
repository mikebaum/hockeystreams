package org.mbaum.common.model;

import org.apache.log4j.Logger;
import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;

public interface MutableModel<M extends ModelSpec> extends Model<M>, Destroyable, Listenable<MutableModel<M>, Listener<MutableModel<M>>>, Iterable<MutableModelValue<M, ?>>
{
    void reset();
    
    <T> MutableModelValue<M, T> getModelValue( ModelValueId<M, T> id );
    
    <T> void setValue( ModelValueId<M, T> id, T value );
    

    static class Builder
    {
        private static final Logger LOGGER = Logger.getLogger( Builder.class );
        
        public static <M extends ModelSpec> MutableModel<M> createMutableModel( Class<M> modelClass )
        {
            try
            {
                return MutableModelImpl.of( ModelValueIdUtils.getIds( modelClass ) );
            }
            catch ( IllegalAccessException exception )
            {
                LOGGER.error( "Failed to create model.", exception );
                return null;
            }
        }
    }
}
