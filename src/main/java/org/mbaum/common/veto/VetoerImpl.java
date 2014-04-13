package org.mbaum.common.veto;

import static org.mbaum.common.veto.VetoListener.NULL_LISTENER;

import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValidator;

import com.google.common.base.Preconditions;

class VetoerImpl<M extends Model<M>> implements Vetoer
{
	private final Model<M> mModel;
	private final ModelValidator<M> mValidator;
	private final AtomicBoolean mVetoing = new AtomicBoolean();
	private final Listener<Model<M>> mModelListener;
	private VetoListener mListener = NULL_LISTENER;
	
	private VetoerImpl( Model<M> model, ModelValidator<M> validator )
	{
		mModel = model;
		mValidator = validator;
		updateVetoing();
		
		mModelListener = createModelListener();
		model.addListener( mModelListener );
	}
	
	private Listener<Model<M>> createModelListener()
	{
		return new Listener<Model<M>>()
		{
			@Override
            public void handleChanged( Model<M> model )
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
	
	public static <M extends Model<M>> Vetoer createVetoer( Model<M> model, ModelValidator<M> validator )
	{
		return new VetoerImpl<M>( model, validator );
	}
}
