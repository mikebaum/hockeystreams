package org.mbaum.common.action;

import java.util.List;

import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.veto.VetoListener;
import org.mbaum.common.veto.Vetoer;

import com.google.common.collect.Lists;

public class ActionModelImpl extends AbstractModel<ActionModel> implements ActionModel
{
	private final List<Vetoer> mVetoers = Lists.newArrayList();
	
	@Override
	public void destroy()
	{
		super.destroy();
		destroyVetoers();
	}
	
	@Override
	public boolean isEnabled()
	{
		for ( Vetoer vetoer : mVetoers )
			if ( vetoer.isVetoing() )
				return false;
		
		return true;
	}
	
	@Override
	public void addVetoer( Vetoer vetoer )
	{
		vetoer.setListener( createVetoerListener() );
	}

	private VetoListener createVetoerListener()
	{
		return new VetoListener()
		{
			@Override
			public void vetoChanged()
			{
				notifyListeners( ActionModelImpl.this );
			}
		};
	}

	private void destroyVetoers()
	{
		for ( Vetoer vetoer : mVetoers )
			vetoer.destroy();
		
		mVetoers.clear();
	}
}
