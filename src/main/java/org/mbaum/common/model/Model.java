package org.mbaum.common.model;

import com.google.common.collect.ImmutableSet;

public interface Model<M extends ModelSpec>
{
    <T> T getValue( ModelValueId<M, T> id );
    
    ImmutableSet<ModelValueId<M, ?>> getIds();
}
