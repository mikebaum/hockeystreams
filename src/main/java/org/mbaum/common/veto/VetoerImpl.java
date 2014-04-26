package org.mbaum.common.veto;

import static org.mbaum.common.veto.VetoListener.NULL_LISTENER;

import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValidator;
import org.mbaum.common.model.MutableModel;

import com.google.common.base.Preconditions;

class VetoerImpl<M extends ModelSpec> implements Vetoer
{
	private final MutableModel<M> mModel;
	private final ModelValidator<M> mValidator;
	private final AtomicBoolean mVetoing = new AtomicBoolean();
	private final Listener<MutableModel<M>> mModelListener;
	private VetoListener mListener = NULL_LISTENER;
	
	private VetoerImpl( MutableModel<M> model, ModelValidator<M> validator )
	{
		mModel = model;
		mValidator = validator;
		updateVetoing();
		
		mModelListener = createModelListener();
		model.addListener( mModelListener );
	}
	
	private Listener<MutableModel<M>> createModelListener()
	{
		return new Listener<MutableModel<M>>()
		{
			@Override
            public void handleChanged( MutableModel<M> model )
            {
				updateVetoing();
            }
		};
	}

	@Override
	public void destroy()
	{
		mModel.removeListener( mModelListener );
		mListener = NULL_LISTENER;
	}
	
	private void updateVetoing()
	{
		boolean isVetoing = ! mValidator.isValid( mModel );
		
		if ( mVetoing.compareAndSet( ! isVetoing, isVetoing ) )
			mListener.vetoChanged();
	}
	
	@Override
	public boolean isVetoing()
	{
		return mVetoing.get();
	}

	@Override
	public void setListener( VetoListener listener )
	{
		Preconditions.checkNotNull( listener );
		Preconditions.checkState( NULL_LISTENER.equals( mListener ) );
		mListener = listener;
	}
	
	public static <M extends ModelSpec> Vetoer createVetoer( MutableModel<M> model, ModelValidator<M> validator )
	{
		return new VetoerImpl<M>( model, validator );
	}
}
