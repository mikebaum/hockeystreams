package org.mbaum.hockeystreams.model;

import org.mbaum.hockeystreams.Destroyable;

public interface Model<M extends Model<M>> extends Destroyable
{
    void addListener( ModelListener<M> listener );
    
    void removeListener( ModelListener<M> listener );
}
