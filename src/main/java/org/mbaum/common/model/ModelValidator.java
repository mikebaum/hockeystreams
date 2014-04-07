package org.mbaum.common.model;

import org.mbaum.common.model.Model.ModelValueId;

public interface ModelValidator<M extends Model<? extends ModelValueId<?>, M>>
{
	boolean isValid( M model );
}
