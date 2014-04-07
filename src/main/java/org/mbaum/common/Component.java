package org.mbaum.common;

import org.mbaum.common.view.View;

public interface Component extends Destroyable
{
	View getView();
}
