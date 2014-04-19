package org.mbaum.common.model;

import org.mbaum.common.object.ObjectConversionException;


public interface ModelValueId<M extends Model<M>, T> extends Id<T>
{
    String getName();
    
    T getDefaultValue();
    
    T valueFrom( Object object ) throws ObjectConversionException;
}
