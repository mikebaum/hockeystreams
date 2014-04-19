package org.mbaum.common.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.mbaum.common.object.ListModelObjectConverter;
import org.mbaum.common.object.ObjectConversionException;
import org.mbaum.common.object.ObjectConverter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ModelValueIdUtils
{
    public static <M extends Model<M>> List<ModelValueId<M, ?>> getIds( Class<M> modelClass ) 
        throws IllegalAccessException
    {
        // Comando Mode
        Field[] fields = modelClass.getDeclaredFields();

        Map<String, ModelValueId<M, ?>> modelValueIds = Maps.newHashMap();

        for ( Field field : fields )
        {
            if ( ModelValueId.class.isAssignableFrom( field.getType() ) )
            {                
                @SuppressWarnings("unchecked")
                ModelValueId<M, ?> modelValueId = (ModelValueId<M, ?>) field.get( null );
                modelValueIds.put( modelValueId.getName(), modelValueId );
            }
        }

        return ImmutableList.copyOf( modelValueIds.values() );
    }
    
    public static <M extends Model<M>, T> ModelValueId<M, T> createId( String name, T defaultValue )
    {
        return createId( name, defaultValue, ObjectConverter.DefaultObjectConverter.<T>newConverter() );
    }

    public static <M extends Model<M>, T> ModelValueId<M, T> createId( String name, 
                                                                       T defaultValue, 
                                                                       ObjectConverter<T> converter )
    {
        return new ModelValueIdImpl<M, T>( name, defaultValue, converter );
    }
    
    public static <M extends Model<M>, MV extends Model<MV>> ModelValueId<M, List<Model<MV>>> 
        createListModelId( String name, Class<MV> modelClass )
    {
        return new ModelValueIdImpl<M, List<Model<MV>>>( name, 
                                                         (List<Model<MV>>) Lists.<Model<MV>>newArrayList(),
                                                         new ListModelObjectConverter<MV>( modelClass ) );
    }
    
    private static class ModelValueIdImpl<M extends Model<M>, T> implements ModelValueId<M, T>
    {
        private final String mDescription;
        private final T mDefaultValue;
        private final ObjectConverter<T> mConverter;
        
        private ModelValueIdImpl( String description, T defaultValue, ObjectConverter<T> converter )
        {
            mDescription = checkNotNull( description );
            mDefaultValue = checkNotNull( defaultValue );
            mConverter = checkNotNull( converter );
        }
        
        @Override
        public String getName()
        {
            return mDescription;
        }

        @Override
        public T getDefaultValue()
        {
            return mDefaultValue;
        }

        @Override
        public String toString()
        {
            return "ModelValueIdImpl [mDescription=" + mDescription + ", mDefaultValue=" +
                   mDefaultValue + "]";
        }

        @Override
        public T valueFrom( Object object ) throws ObjectConversionException
        {
            return mConverter.convert( object );
        }
    }
}
