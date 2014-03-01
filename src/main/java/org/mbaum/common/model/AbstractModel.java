package org.mbaum.common.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.model.ModelValue.Builder;
import org.mbaum.common.value.ValueImpl;
import org.mbaum.common.value.VolatileValue;

import com.google.common.collect.Lists;

public abstract class AbstractModel<M extends Model<M>> implements Model<M>
{
    private final List<ModelListener<M>> mListeners = Lists.newArrayList();
    private final List<ModelValue<?>> mModelValues = Lists.newArrayList();
    private final Notifier mNotifier;

    protected AbstractModel()
    {
    	mNotifier = createNotifier( this );
    }

    @Override
    public void addListener( ModelListener<M> listener )
    {
    	mListeners.add( listener );			
    }
    
    @Override
    public void removeListener( ModelListener<M> listener )
    {
        mListeners.remove( listener );
    }
    
    @Override
    public void destroy()
    {
        mListeners.clear();
    }
    
    @Override
    public void reset()
    {
    	mNotifier.pause();
    	
    	for ( ModelValue<?> modelValue : mModelValues )
    		modelValue.reset();
    	
    	mNotifier.resume();
    }
    
	protected <T> ModelValue<T> newModelValue()
	{
		return createModelValueBuilder( (T) null ).setNotifier( mNotifier )
				                                  .build();
	}
	
	protected <T> ModelValue<T> newModelValue( T defaultValue )
	{
		return createModelValueBuilder( defaultValue ).setNotifier( mNotifier )
				      								  .setCurrentValue( new ValueImpl<T>( defaultValue ) )
				      								  .build();
	}
	
	protected <T> ModelValue<T> newVolatileModelValue( T defaultValue )
	{
		return createModelValueBuilder( defaultValue ).setNotifier( mNotifier )
												      .setCurrentValue( new VolatileValue<T>( defaultValue ) )
												      .build();
	}
    
	private <T> Builder<T> createModelValueBuilder( final T defaultValue )
    {
		return new Builder<T>()
	    {
			@Override
			public ModelValue<T> build()
			{
				setDefaultValue( defaultValue );
				ModelValue<T> modelValue = super.build();
				mModelValues.add( modelValue );
				return modelValue;
			}
		};
    }
	
    private void notifyListeners( M model )
    {
        for( ModelListener<M> listener : Lists.newArrayList( mListeners ) )
            listener.modelChanged( model );
    }
    
    private static <M extends Model<M>, A extends AbstractModel<M> & Model<M>> Notifier createNotifier( final A model )
    {
    	return new Notifier()
    	{
    		private boolean mPaused = false;
    		private final AtomicBoolean mUnsentNotifications = new AtomicBoolean( false );
    		
			@Override
            public void notifyListeners()
            {
				if ( ! mPaused )
					model.notifyListeners( (M) model );
				else
					mUnsentNotifications.set( true );
            }

			@Override
            public void pause()
            {
	            mPaused = true;
            }

			@Override
            public void resume()
            {
				mPaused = false;

				if ( mUnsentNotifications.compareAndSet( true, false ) )
	            	notifyListeners();
            }
    	};
    }
}
