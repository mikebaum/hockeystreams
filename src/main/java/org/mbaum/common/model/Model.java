package org.mbaum.common.model;

import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;

public interface Model<M extends Model<M>> extends Destroyable, Listenable<M, Listener<M>>, Iterable<MutableModelValue<?>>
{
	public interface ModelValueId<M, T> extends Id<T>{}
	
    void reset();
    
    <T> MutableModelValue<T> getModelValue( ModelValueId<M, T> id );
    
    <T> T getValue( ModelValueId<M, T> id );
    
    <T> void setValue( ModelValueId<M, T> id, T value );
    
    
    public static class IdBuilder
    {
        public static <M extends Model<M>, T> ModelValueId<M, T> createId()
        {
            return new ModelValueId<M, T>() {};
        }
    }
}
