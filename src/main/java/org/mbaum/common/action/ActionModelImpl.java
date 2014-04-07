package org.mbaum.common.action;

import java.util.List;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.AbstractModel;
import org.mbaum.common.model.MutableModelValue;
import org.mbaum.common.veto.VetoListener;
import org.mbaum.common.veto.Vetoer;

import com.google.common.collect.Lists;

public class ActionModelImpl extends AbstractModel<ActionModel.Id<?>, ActionModel> implements ActionModel
{
	private final List<Vetoer> mVetoers = Lists.newArrayList();
	private final MutableModelValue<Boolean> mEnabled;
	private final String mDescription;
	
	public ActionModelImpl( String description )
    {
		mDescription = description;
		mEnabled = newVolatileModelValue( ENABLED, false, "Enabled", this );
    }
	
	@Override
	public void destroy()
	{
		super.destroy();
		destroyVetoers();
	}
	
	@Override
	public <T> MutableModelValue<T> getModelValue( Id<T> id )
	{
	    return super.getModelValue( id );
	}
	
	@Override
	public <T> T getValue( Id<T> id )
	{
	    return getModelValue( id ).get();
	}
	
	@Override
	public <T> void setValue( Id<T> id, T value )
	{
		getModelValue( id ).set( value );
	}
	
	@Override
	public void addVetoer( Vetoer vetoer )
	{
	    mVetoers.add( vetoer );
		vetoer.setListener( createVetoerListener() );
		updateEnabled();
	}
	
	@Override
	protected ListenableSupport<ActionModel, Listener<ActionModel>> createListenableSupport()
	{
	    return createModelListenerSupport( (ActionModel) this );
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
	    
	    mEnabled.set( isEnabled );
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

	@Override
    public String getDescription()
    {
	    return mDescription;
    }
}
