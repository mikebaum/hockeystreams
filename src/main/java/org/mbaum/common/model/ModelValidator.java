package org.mbaum.common.model;


public interface ModelValidator<M extends Model<M>>
{
	boolean isValid( Model<M> model );
}
