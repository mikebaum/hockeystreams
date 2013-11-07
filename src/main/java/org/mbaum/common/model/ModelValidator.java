package org.mbaum.common.model;

public interface ModelValidator<M extends Model<M>>
{
	boolean isValid( M model );
}
