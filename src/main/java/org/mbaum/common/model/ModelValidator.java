package org.mbaum.common.model;

public interface ModelValidator<M extends ModelSpec>
{
    boolean isValid( Model<M> model );
}
