package org.mbaum.common.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.ValueImpl;
import org.mbaum.common.value.VolatileValue;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class MutableModelImpl<M extends ModelSpec> implements MutableModel<M>
{
    private final ModelValueSet<M, MutableModelValue<M, ?>> mModelValues = new ModelValueSet<M, MutableModelValue<M, ?>>();
    private final ListenableSupport<MutableModel<M>, Listener<MutableModel<M>>> mListenableSupport;
    
    public static <M extends ModelSpec> MutableModel<M> of( List<ModelValueId<M, ?>> modelValueIds )
    {
        return new MutableModelImpl<M>( createModelValues( modelValueIds ) );
    }
	
    private MutableModelImpl( List<MutableModelValue<M, ?>> modelValues )
    {
    	mListenableSupport =  createModelListenerSupport( this );
    	addModelValues( modelValues );
    }
    
    @Override
    public void destroy()
    {
        mListenableSupport.destroy();
        
        for ( MutableModelValue<M, ?> modelValue : mModelValues.values() )
            modelValue.destroy();
    }
    
    @Override
    public void addListener( Listener<MutableModel<M>> listener )
    {
    	mListenableSupport.addListener( listener );			
    }
    
    @Override
    public void removeListener( Listener<MutableModel<M>> listener )
    {
    	mListenableSupport.removeListener( listener );
    }
    
    @Override
    public void reset()
    {
    	mListenableSupport.pause();
    	
    	for ( MutableModelValue<M, ?> modelValue : mModelValues.values() )
    		modelValue.reset();
    	
    	mListenableSupport.resume();
    }
    
    @Override
    public Iterator<MutableModelValue<M, ?>> iterator()
    {
        return mModelValues.values().iterator();
    }
    
    @Override
    public <T> T getValue( ModelValueId<M, T> id )
    {
        return getModelValue( id ).get();
    }
    
    @Override
    public <T> void setValue( ModelValueId<M, T> id, T value )
    {
    	getModelValue( id ).set( value );   	
    }

    @Override
	public <T> MutableModelValue<M, T> getModelValue( ModelValueId<M, T> id )
    {
        @SuppressWarnings("unchecked")
        MutableModelValue<M, T> modelValue = (MutableModelValue<M, T>) mModelValues.getModelValue( id );
        return modelValue;
    }
    
    @Override
    public ImmutableSet<ModelValueId<M,?>> getIds()
    {
        return mModelValues.getIds();
    }
    
	@Override
    public String toString()
    {
        return "ModelImpl [mModelValues=" + mModelValues.values() + "]";
    }

    protected <T> MutableModelValue<M, T> newModelValue( ModelValueId<M, T> id, T defaultValue )
	{
		return createModelValueBuilder( defaultValue, id ).setCurrentValue( new ValueImpl<T>( defaultValue ) )
														  .build();
	}
	
	protected <T> MutableModelValue<M, T> newVolatileModelValue( ModelValueId<M, T> id, T defaultValue )
	{
		return createModelValueBuilder( defaultValue, id ).setCurrentValue( new VolatileValue<T>( defaultValue ) )
														  .build();
	}
	
    private void addModelValues( List<MutableModelValue<M, ?>> modelValues )
    {
        for ( MutableModelValue<M, ?> value : modelValues )
            addModelValue( value );
    }
	
    private <T> void addModelValue( MutableModelValue<M, T> modelValue )
    {
        Listener<T> listener = createModelValueListener( mListenableSupport );
        modelValue.addListener( listener );
        mModelValues.putModelValue( modelValue );
    }
    
	private <T> MutableModelValue.Builder<M, T> createModelValueBuilder( T defaultValue, final ModelValueId<M, T> id )
    {
		return new MutableModelValue.Builder<M, T>( id )
	    {
			@Override
			public MutableModelValue<M, T> build()
			{
				MutableModelValue<M, T> modelValue = super.build();
				addModelValue( modelValue );
				return modelValue;
			}
		};
    }
	
	private static <T, M extends ModelSpec> Listener<T> 
		createModelValueListener( final ListenableSupport<MutableModel<M>, Listener<MutableModel<M>>> listenerSupport )
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
	
	private static <M extends ModelSpec> ListenableSupport<MutableModel<M>, Listener<MutableModel<M>>> 
		createModelListenerSupport( final MutableModel<M> model )
	{
		return ListenableSupport.createListenableSupport( new Supplier<MutableModel<M>>()
		{
			@Override
            public MutableModel<M> get()
            {
				return model;
            }
		} );
	}
	
    private static <M extends ModelSpec> List<MutableModelValue<M, ?>> createModelValues( List<ModelValueId<M, ?>> modelValueIds )
    {
        checkNotNull( modelValueIds );
        checkArgument( modelValueIds.size() > 0, "Cannot construct a model with an empty list of ids" );
        
        ImmutableList.Builder<MutableModelValue<M, ?>> modelValues = ImmutableList.builder();
        
        for ( ModelValueId<M, ?> id : modelValueIds )
            modelValues.add( MutableModelValue.Builder.newModelValue( id ) );
        
        return modelValues.build();
    }
}
