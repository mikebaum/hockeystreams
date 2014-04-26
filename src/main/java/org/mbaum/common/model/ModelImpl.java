package org.mbaum.common.model;

import static org.mbaum.hockeystreams.model.LoginResponseModelIds.STATUS;

import org.mbaum.hockeystreams.model.LoginResponseModelIds;

import com.google.common.collect.ImmutableSet;

public class ModelImpl<M extends ModelSpec> implements Model<M>
{
    private final ModelValueSet<M, ModelValue<M, ?>> mModelValues;
    
    private ModelImpl( ModelValueSet<M, ModelValue<M, ?>> modelValues )
    {
        mModelValues = modelValues;
    }

    @Override
    public <T> T getValue( ModelValueId<M, T> id )
    {
        @SuppressWarnings("unchecked")
        T value = (T) mModelValues.getModelValue( (ModelValueId<M, ?>) id ).get();
        return value;
    }
    
    @Override
    public ImmutableSet<ModelValueId<M, ?>> getIds()
    {
        return mModelValues.getIds();
    }
    
    public static class Builder<M extends ModelSpec>
    {
        ModelValueSet<M, ModelValue<M, ?>> mModelValues = new ModelValueSet<M, ModelValue<M, ?>>();
        
        public Builder<M> addModelValue( ModelValue<M, ?> modelValue )
        {
            mModelValues.putModelValue( modelValue );
            return this;
        }
        
        public Model<M> build()
        {
            return new ModelImpl<M>( mModelValues );
        }
    }

    public static void main( String[] args )
    {
        ModelValue<LoginResponseModelIds, String> status = 
            new SimpleModelValue<LoginResponseModelIds, String>( STATUS, "hello" );
        
        Model<LoginResponseModelIds> model =  
            new Builder<LoginResponseModelIds>().addModelValue( status )
                                                .build();
        
        String value = model.getValue( STATUS );
        
        System.out.println( "value: " + value );
    }
}
