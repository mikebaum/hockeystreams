package org.mbaum.common.model;

import com.google.common.base.Supplier;

public interface ModelValue<I extends ModelSpec, T> extends Supplier<T>
{
	ModelValueId<I, T> getId();
}