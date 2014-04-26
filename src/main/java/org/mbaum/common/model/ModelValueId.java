package org.mbaum.common.model;

import org.mbaum.common.object.ObjectConversionException;

public interface ModelValueId<M extends ModelSpec, T> extends Id<T>
{
    String getName();

    T getDefaultValue();

    // TODO: consider moving this to another place, seems odd here. Perhaps we should register
    // all converts in a central place.
    T valueFrom( Object object ) throws ObjectConversionException;
}
