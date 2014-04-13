package org.mbaum.common.model;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.mbaum.common.Destroyable;
import org.mbaum.common.listener.Listenable;
import org.mbaum.common.listener.Listener;

import com.google.common.collect.Lists;

public interface Model<M extends Model<M>> extends Destroyable, Listenable<Model<M>, Listener<Model<M>>>, Iterable<MutableModelValue<M, ?>>
{
	public interface ModelValueId<M, T> extends Id<T>
	{
	    String getDescription();
	    
	    T getDefaultValue();
	}
	
    void reset();
    
    <T> MutableModelValue<M, T> getModelValue( ModelValueId<M, T> id );
    
    <T> T getValue( ModelValueId<M, T> id );
    
    <T> void setValue( ModelValueId<M, T> id, T value );
    
    
    public static class IdBuilder
    {
        public static <M extends Model<M>, T> ModelValueId<M, T> createId( final String description, final T defaultValue )
        {
            return new ModelValueId<M, T>() 
            {
                @Override
                public String getDescription()
                {
                    return description;
                }

                @Override
                public T getDefaultValue()
                {
                    return defaultValue;
                }
            };
        }
        
        public static <M extends Model<M>, T> ModelValueId<M, T> createId( String description )
        {
            return createId( description, (T) null );
        }
    }
    
    static class Builder
    {
        private static final Logger LOGGER = Logger.getLogger( Builder.class );
        
        @SuppressWarnings("unchecked")
        public static <M extends Model<M>> Model<M> createModel( Class<M> modelClass )
        {
            try
            {
                // Comando Mode
                Field[] fields = modelClass.getDeclaredFields();
                
                List<ModelValueId<M, ?>> modelValueIds = Lists.newArrayList();
                
                for ( Field field : fields )
                {
                    if ( ModelValueId.class.isAssignableFrom( field.getType() ) )
                        modelValueIds.add( (ModelValueId<M, ?>) field.get( null ) );
                }
                
                return new ModelImpl<M>( modelValueIds );
            }
            catch ( Exception exception )
            {
                LOGGER.error( "Failed to create model.", exception );
                return null;
            }
        }
    }
    
    public static class ModelBuilder
    {
        public static <M extends Model<M>> Model<M> build( MutableModelValue<M, ?>... modelValues )
        {
            return new ModelImpl<M>( modelValues );
        }
    }
}
