package org.mbaum.common.model;

import com.google.common.base.Supplier;

public interface ModelValue<M extends Model<M>, T> extends Supplier<T>
{
	ModelValueId<M, T> getId();
}