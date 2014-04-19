package org.mbaum.common.object;

import static org.mbaum.common.serialization.json.JsonDeserializers.createModelFromMap;

import java.util.List;
import java.util.Map;

import org.mbaum.common.model.Model;

import com.google.common.collect.Lists;

public class ListModelObjectConverter<M extends Model<M>> implements ObjectConverter<List<Model<M>>>
{
    private Class<M> mModelClass;

    public ListModelObjectConverter( Class<M> modelClass )
    {
        mModelClass = modelClass;
    }

    @Override
    public List<Model<M>> convert( Object object ) throws ObjectConversionException
    {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> modelList = (List<Map<String, Object>>) object;
        List<Model<M>> models = Lists.newArrayList();
        
        for ( Map<String, Object> model : modelList )
            models.add( createModelFromMap( mModelClass, model ) );
        
        return models;
    }
}
