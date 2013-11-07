package org.mbaum.common.model;

import org.mbaum.common.Destroyable;

public interface Model<M extends Model<M>> extends Destroyable, Listenable<ModelListener<M>>
{
    void addListener( ModelListener<M> listener );
    
    void removeListener( ModelListener<M> listener );
}
