package org.mbaum.common.model;

public interface ProgressPanelModel extends Model<ProgressPanelModel>
{
	String getMessage();

	void setMessage( String message );

	boolean isIndeterminant();

	void setIndeterminant( boolean indeterminant );
}