package org.mbaum.common.serialization.json;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.mbaum.common.model.Model.Builder.createModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValueId;
import org.mbaum.common.object.ObjectConversionException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class JsonDeserializers
{
    private JsonDeserializers(){}
    
    public static <R> JsonDeserializer<R> createDefault( final Class<R> resultClass )
    {
        return new JsonDeserializer<R>()
        {
            @Override
            public R deserialize( String jsonString ) throws JsonException
            {
                try
                {
                    return (R) new ObjectMapper().readValue( jsonString, resultClass );
                }
                catch ( JsonParseException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
                catch ( JsonMappingException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
                catch ( IOException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
            }
        };
    }
    
    public static <R extends Model<R>> JsonDeserializer<Model<R>> createModelDeserializer( final Class<R> modelClass )
    {
        return new JsonDeserializer<Model<R>>()
        {
            @Override
            public Model<R> deserialize( String jsonString ) throws JsonException
            {
                try
                {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> result = new ObjectMapper().readValue( jsonString, HashMap.class );
                    return createModelFromMap( modelClass, result );
                }
                catch ( JsonParseException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
                catch ( JsonMappingException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
                catch ( IOException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
                catch ( ObjectConversionException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
            }
        };
    }
    
    public static <R extends Model<R>> Model<R> createModelFromMap( Class<R> modelClass,
                                                                    Map<String, Object> result )
        throws ObjectConversionException
    {
        Model<R> model = createModel( modelClass );
        Map<String, ModelValueId<R, ?>> ids = getModelIdsMap( model );
        
        for ( Map.Entry<String, Object> entry : result.entrySet() )
        {
            ModelValueId<R, ?> id = ids.get( entry.getKey() );
            setModelValue( model, id, entry.getValue() );
        }
        
        return model;
    }

    private static <M extends Model<M>, T> void setModelValue( Model<M> model,
                                                               ModelValueId<M, T> id,
                                                               Object value ) throws ObjectConversionException
    {
        // TODO: handle collections, arrays and values that are of type model (recursion!!!)
        model.setValue( checkNotNull( id ), id.valueFrom( value ) );
    }

    private static <R extends Model<R>> ImmutableMap<String, ModelValueId<R, ?>> getModelIdsMap( Model<R> model )
    {
        ImmutableList<ModelValueId<R, ?>> ids = model.getIds();
        
        ImmutableMap.Builder<String, ModelValueId<R, ?>> builder = ImmutableMap.builder();
        
        for ( ModelValueId<R, ?> modelValueId : ids )
            builder.put( modelValueId.getName(), modelValueId );
        
        return builder.build();
    }
}
