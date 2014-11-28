package org.mbaum.common.serialization.json;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValueId;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.object.ObjectConversionException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class JsonDeserializers
{
    private JsonDeserializers()
    {
    }

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
                catch ( IOException e )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "", e );
                }
            }
        };
    }

    public static <M extends ModelSpec> JsonDeserializer<MutableModel<M>> createMutableModelDeserializer( final Class<M> modelClass )
    {
        return new JsonDeserializer<MutableModel<M>>()
        {
            @Override
            public MutableModel<M> deserialize( String jsonString ) throws JsonException
            {
                try
                {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> result =
                        new ObjectMapper().readValue( jsonString, HashMap.class );
                    return createMutableModelFromMap( modelClass, result );
                }
                catch ( IOException | ObjectConversionException exception )
                {
                    throw new JsonException( "Exception while parsing: " + jsonString + "",
                                             exception );
                }
            }
        };
    }

    public static <M extends ModelSpec> MutableModel<M> createMutableModelFromMap( Class<M> modelClass,
                                                                                   Map<String, Object> result ) 
        throws ObjectConversionException
    {
        MutableModel<M> model = createMutableModel( modelClass );
        Map<String, ModelValueId<M, ?>> ids = getModelIdsMap( model );

        for ( Map.Entry<String, Object> entry : result.entrySet() )
        {
            ModelValueId<M, ?> id = ids.get( entry.getKey() );
            setModelValue( model, id, entry.getValue() );
        }

        return model;
    }

    private static <M extends ModelSpec, T> void setModelValue( MutableModel<M> model,
                                                                ModelValueId<M, T> id,
                                                                Object value ) throws ObjectConversionException
    {
        model.setValue( checkNotNull( id ), id.valueFrom( value ) );
    }

    private static <M extends ModelSpec> ImmutableMap<String, ModelValueId<M, ?>> getModelIdsMap( MutableModel<M> model )
    {
        ImmutableSet<ModelValueId<M, ?>> ids = model.getIds();

        ImmutableMap.Builder<String, ModelValueId<M, ?>> builder = ImmutableMap.builder();

        for ( ModelValueId<M, ?> modelValueId : ids )
            builder.put( modelValueId.getName(), modelValueId );

        return builder.build();
    }
}
