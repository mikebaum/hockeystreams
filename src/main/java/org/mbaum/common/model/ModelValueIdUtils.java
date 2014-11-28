package org.mbaum.common.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.mbaum.common.object.ListModelObjectConverter;
import org.mbaum.common.object.ObjectConverter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ModelValueIdUtils
{
    public static <M extends ModelSpec> List<ModelValueId<M, ?>> getIds( Class<M> modelClass ) 
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
    
    public static <M extends ModelSpec, T> ModelValueId<M, T> createId( String name, T defaultValue )
    {
        return createId( name, defaultValue, ObjectConverter.DefaultObjectConverter.<T>newConverter() );
    }

    public static <M extends ModelSpec, T> ModelValueId<M, T> createId( String name, 
                                                                        T defaultValue, 
                                                                        ObjectConverter<T> converter )
    {
        return ModelSpec.IdBuilder.createId( name, defaultValue, converter );
    }
    
    public static <M extends ModelSpec, MV extends ModelSpec> ModelValueId<M, List<MutableModel<MV>>> 
        createListModelId( String name, Class<MV> modelClass )
    {
        return createListId( name, new ListModelObjectConverter<MV>( modelClass ) );
    }
    
    public static <M extends ModelSpec, T> ModelValueId<M, List<T>> 
        createListId( String name, ObjectConverter<List<T>> converter )
    {
        return ModelSpec.IdBuilder.createId(  name, (List<T>) Lists.<T>newArrayList(), converter );
    }
}
