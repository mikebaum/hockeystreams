package org.mbaum.common.view;

import javax.swing.JComponent;

import org.mbaum.common.Destroyable;

public interface View extends Destroyable
{
    JComponent getComponent();
}
