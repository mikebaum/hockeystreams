package org.mbaum.common.action;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.veto.VetoListener;
import org.mbaum.common.veto.Vetoer;

import com.google.common.collect.Lists;

public class ActionModelImpl extends AbstractModel<ActionModel> implements ActionModel
{
	private final List<Vetoer> mVetoers = Lists.newArrayList();
	
	private final AtomicBoolean mEnabled = new AtomicBoolean();
	
	@Override
	public void destroy()
	{
		super.destroy();
		destroyVetoers();
	}
	
	@Override
	public boolean isEnabled()
	{
	    return mEnabled.get();
	}
	
	@Override
	public void addVetoer( Vetoer vetoer )
	{
	    mVetoers.add( vetoer );
		vetoer.setListener( createVetoerListener() );
		updateEnabled();
	}
	
	private void updateEnabled()
	{
	    boolean isEnabled = true;
	    for ( Vetoer vetoer : mVetoers )
	    {
	        if ( vetoer.isVetoing() )
	        {	            
	            isEnabled = false;
	            break;
	        }
	    }
	    
	    if ( mEnabled.compareAndSet( ! isEnabled, isEnabled ) )
	        notifyListeners( this );
	}

	private VetoListener createVetoerListener()
	{
		return new VetoListener()
		{
			@Override
			public void vetoChanged()
			{
				updateEnabled();
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
