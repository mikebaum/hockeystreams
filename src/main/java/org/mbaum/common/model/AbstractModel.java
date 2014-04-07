package org.mbaum.common.model;

import java.util.Iterator;
import java.util.Map;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.MutableModelValue.Builder;
import org.mbaum.common.value.ValueImpl;
import org.mbaum.common.value.VolatileValue;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

public abstract class AbstractModel<I extends ModelValueId<?>, M extends Model<I, M>> implements Model<I, M>
{
    private final Map<ModelValueId<?>, MutableModelValue<?>> mModelValues = Maps.newIdentityHashMap();
	private final ListenableSupport<M, Listener<M>> mListenableSupport;
	
    protected AbstractModel()
    {
    	mListenableSupport = createListenableSupport();
    }
    
    @Override
    public void addListener( Listener<M> listener )
    {
    	mListenableSupport.addListener( listener );			
    }
    
    @Override
    public void removeListener( Listener<M> listener )
    {
    	mListenableSupport.removeListener( listener );
    }
    
    @Override
    public void clearListeners()
    {
    	mListenableSupport.clearListeners();
    }
    
    @Override
    public void destroy()
    {
    	mListenableSupport.destroy();
        
        for ( MutableModelValue<?> modelValue : mModelValues.values() )
        	modelValue.clearListeners();
    }
    
    @Override
    public void reset()
    {
    	mListenableSupport.pause();
    	
    	for ( MutableModelValue<?> modelValue : mModelValues.values() )
    		modelValue.reset();
    	
    	mListenableSupport.resume();
    }
    
    @Override
    public Iterator<MutableModelValue<?>> iterator()
    {
        return mModelValues.values().iterator();
    }
    
    @Override
    public <T> T getValue( ModelValueId<T> id )
    {
        return getModelValue( id ).get();
    }
    
    @Override
    public <T> void setValue( ModelValueId<T> id, T value )
    {
    	getModelValue( id ).set( value );   	
    }

    @Override
	public <T> MutableModelValue<T> getModelValue( ModelValueId<T> id )
    {
	    @SuppressWarnings("unchecked")
        MutableModelValue<T> modelValue = (MutableModelValue<T>) mModelValues.get( id );
    	Preconditions.checkArgument( modelValue != null, "There is no model value for the id: " + id );
	    return modelValue;
    }
    
    protected abstract ListenableSupport<M, Listener<M>> createListenableSupport();
    
	protected <T> MutableModelValue<T> newModelValue( I id, T defaultValue, String description, M model )
	{
		@SuppressWarnings("unchecked")
        ModelValueId<T> modelValueId = (ModelValueId<T>) id;
		return createModelValueBuilder( defaultValue, description, model, modelValueId ).setCurrentValue( new ValueImpl<T>( defaultValue ) )
																						.build();
	}
	
	protected <T> MutableModelValue<T> newVolatileModelValue( I id,
	                                                          T defaultValue, 
	                                                          String description, 
	                                                          M model )
	{
		@SuppressWarnings("unchecked")
        ModelValueId<T> modelValueId = (ModelValueId<T>) id;
		return createModelValueBuilder( defaultValue, description, model, modelValueId ).setCurrentValue( new VolatileValue<T>( defaultValue ) )
																			  			.build();
	}
    
	private <T> Builder<T> createModelValueBuilder( T defaultValue, 
	                                                String description, 
	                                                final M model, 
	                                                final ModelValueId<T> id )
    {
		return new Builder<T>( description, defaultValue )
	    {
			@Override
			public MutableModelValue<T> build()
			{
				MutableModelValue<T> modelValue = super.build();
				Listener<T> listener = createModelValueListener( mListenableSupport, model );
				modelValue.addListener( listener );
				mModelValues.put( id, modelValue );
				return modelValue;
			}
		};
    }
	
	private static <I extends ModelValueId<?>, T, M extends Model<I, M>> Listener<T> 
		createModelValueListener( final ListenableSupport<M, Listener<M>> listenerSupport, final M model )
    {
        return new Listener<T>()
		{
			@Override
            public void handleChanged( T newValue )
            {
				listenerSupport.notifyListeners();
            }
		};
    }
	
	protected static <I extends ModelValueId<?>, M extends Model<I, M>> ListenableSupport<M, Listener<M>> 
		createModelListenerSupport( final M model )
	{
		return ListenableSupport.createListenableSupport( new Supplier<M>()
		{
			@Override
            public M get()
            {
				return model;
            }
		} );
	}
}
