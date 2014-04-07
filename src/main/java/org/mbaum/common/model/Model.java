package org.mbaum.common.model;

import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.Model.ModelValueId;

public interface Model<I extends ModelValueId<?>, M extends Model<I, M>> extends Destroyable, Listenable<M, Listener<M>>, Iterable<MutableModelValue<?>>
{
	public interface ModelValueId<T> extends Id<T>{}
	
    void reset();
    
    <T> MutableModelValue<T> getModelValue( ModelValueId<T> id );
    
    <T> T getValue( ModelValueId<T> id );
    
    <T> void setValue( ModelValueId<T> id, T value );
}
