package org.mbaum.common.component;

import org.mbaum.common.Destroyable;
import org.mbaum.common.view.View;

public interface Component extends Destroyable
{
	View getView();
}
