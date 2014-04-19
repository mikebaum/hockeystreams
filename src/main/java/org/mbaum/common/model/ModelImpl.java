package org.mbaum.common.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.ValueImpl;
import org.mbaum.common.value.VolatileValue;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class ModelImpl<M extends Model<M>> implements Model<M>
{
    private final Map<ModelValueId<M, ?>, MutableModelValue<M, ?>> mModelValues = Maps.newIdentityHashMap();
	private final ListenableSupport<Model<M>, Listener<Model<M>>> mListenableSupport;
	
    protected ModelImpl()
    {
    	mListenableSupport =  createModelListenerSupport( this );
    }
    
    public ModelImpl( MutableModelValue<M, ?>... modelValues )
    {
        this();
        addModelValues( modelValues );
    }
    
    public ModelImpl( ModelValueId<M, ?>... modelValueIds )
    {
        this();
        addModelValues( createModelValues( modelValueIds ) );
    }
    
    public ModelImpl( List<ModelValueId<M, ?>> modelValueIds )
    {
        this();
        @SuppressWarnings("unchecked")
        ModelValueId<M, ?>[] _modelValueIds = new  ModelValueId[modelValueIds.size()];
        addModelValues( createModelValues( modelValueIds.toArray( _modelValueIds ) ) );
    }
    
    private MutableModelValue<M, ?>[] createModelValues( ModelValueId<M, ?>[] modelValueIds )
    {
        checkNotNull( modelValueIds );
        checkArgument( modelValueIds.length > 0, "Cannot construct a model with an empty list of ids" );
        
        @SuppressWarnings("unchecked")
        MutableModelValue<M, ?>[] modelValues = new MutableModelValue[ modelValueIds.length ];
        
        int i = 0;
        for ( ModelValueId<M, ?> id : modelValueIds )
            modelValues[i++] = MutableModelValue.Builder.newModelValue( id );
        
        return modelValues;
    }

    private void addModelValues( MutableModelValue<M, ?>[] modelValues )
    {
        for ( MutableModelValue<M, ?> value : modelValues )
            addModelValue( value );
    }

    @Override
    public void addListener( Listener<Model<M>> listener )
    {
    	mListenableSupport.addListener( listener );			
    }
    
    @Override
    public void removeListener( Listener<Model<M>> listener )
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
        
        for ( MutableModelValue<M, ?> modelValue : mModelValues.values() )
        	modelValue.clearListeners();
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
        MutableModelValue<M, T> modelValue = (MutableModelValue<M, T>) mModelValues.get( id );
    	Preconditions.checkArgument( modelValue != null, "There is no model value for the id: " + id );
	    return modelValue;
    }
    
    @Override
    public ImmutableList<ModelValueId<M, ?>> getIds()
    {
        return ImmutableList.copyOf( mModelValues.keySet() );
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
	
    private <T> void addModelValue( MutableModelValue<M, T> modelValue )
    {
        Listener<T> listener = createModelValueListener( mListenableSupport );
        modelValue.addListener( listener );
        mModelValues.put( modelValue.getId(), modelValue );
    }
    
	private <T> MutableModelValue.Builder<M, T> createModelValueBuilder( T defaultValue, final ModelValueId<M, T> id )
    {
		return new MutableModelValue.Builder<M, T>( id, defaultValue )
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
	
	private static <T, M extends Model<M>> Listener<T> 
		createModelValueListener( final ListenableSupport<Model<M>, Listener<Model<M>>> listenerSupport )
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
	
	protected static <M extends Model<M>> ListenableSupport<Model<M>, Listener<Model<M>>> 
		createModelListenerSupport( final Model<M> model )
	{
		return ListenableSupport.createListenableSupport( new Supplier<Model<M>>()
		{
			@Override
            public Model<M> get()
            {
				return model;
            }
		} );
	}
}
