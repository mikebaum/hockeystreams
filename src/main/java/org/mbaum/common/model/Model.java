package org.mbaum.common.model;

import org.mbaum.common.Destroyable;

public interface Model<M extends Model<M>> extends Destroyable, Listenable<ModelListener<M>>
{
    void reset();
}
