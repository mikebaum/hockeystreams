package org.mbaum.common.object;

import static org.mbaum.common.serialization.json.JsonDeserializers.createMutableModelFromMap;

import java.util.List;
import java.util.Map;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.MutableModel;

import com.google.common.collect.Lists;

public class ListModelObjectConverter<M extends ModelSpec> implements ObjectConverter<List<MutableModel<M>>>
{
    private Class<M> mModelClass;

    public ListModelObjectConverter( Class<M> modelClass )
    {
        mModelClass = modelClass;
    }

    @Override
    public List<MutableModel<M>> convert( Object object ) throws ObjectConversionException
    {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> modelList = (List<Map<String, Object>>) object;
        List<MutableModel<M>> models = Lists.newArrayList();
        
        for ( Map<String, Object> model : modelList )
            models.add( createMutableModelFromMap( mModelClass, model ) );
        
        return models;
    }
}
