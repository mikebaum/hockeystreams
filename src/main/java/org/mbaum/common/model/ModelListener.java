package org.mbaum.common.model;

public interface ModelListener<M extends Model<M>>
{
	void modelChanged( M model );
}