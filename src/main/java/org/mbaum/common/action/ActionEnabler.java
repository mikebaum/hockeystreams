package org.mbaum.common.action;

import org.mbaum.common.model.Model;

public interface ActionEnabler<M extends Model<M>>
{
	boolean isEnabled( M model );	
}
