package org.mbaum.common.action;

import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;

import java.util.List;

import org.mbaum.common.model.ForwardingModel;
import org.mbaum.common.veto.VetoListener;
import org.mbaum.common.veto.Vetoer;

import com.google.common.collect.Lists;

public class ActionModelImpl extends ForwardingModel<ActionModel> implements ActionModel
{
	private final List<Vetoer> mVetoers = Lists.newArrayList();

	public ActionModelImpl()
    {
	    super( createMutableModel( ActionModel.class ) );
    }
	
	@Override
	public void destroy()
	{
		super.destroy();
		destroyVetoers();
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
	    
	    setValue( ENABLED, isEnabled );
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
