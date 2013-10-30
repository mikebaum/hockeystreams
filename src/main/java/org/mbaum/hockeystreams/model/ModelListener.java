package org.mbaum.hockeystreams.model;

public interface ModelListener<M extends Model<M>>
{
	void modelChanged( M model );
}