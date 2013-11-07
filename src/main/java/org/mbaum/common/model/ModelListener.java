package org.mbaum.common.model;

public interface ModelListener<M extends Model<M>> extends Listener
{
	void modelChanged( M model );
}