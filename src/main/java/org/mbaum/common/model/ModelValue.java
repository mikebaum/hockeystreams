package org.mbaum.common.model;

import com.google.common.base.Supplier;

public interface ModelValue<T> extends Supplier<T>
{
	String getDescription();
}