package org.mbaum.common.model;

import static com.google.common.base.Preconditions.checkNotNull;

import org.mbaum.common.object.ObjectConversionException;
import org.mbaum.common.object.ObjectConverter;

/**
 * Marker Interface for a Model Specification. Instances need to define a set of
 * {@link ModelValueId} values
 */
public interface ModelSpec
{
    // TODO: consider making this a class, with a static method ids()
    class IdBuilder
    {
        private IdBuilder(){}

        public static <M extends ModelSpec, T> ModelValueId<M, T> createId( String name,
                                                                            T defaultValue )
        {
            return createId( name, defaultValue, ObjectConverter.DefaultObjectConverter.<T>newConverter() );
        }
        
        public static <M extends ModelSpec, T> ModelValueId<M, T> createId( String name,
                                                                            T defaultValue,
                                                                            ObjectConverter<T> converter )
        {
            return new ModelValueIdImpl<M, T>( name, defaultValue, converter );
        }
        
        private static class ModelValueIdImpl<M extends ModelSpec, T> implements ModelValueId<M, T>
        {
            private final String mName;
            private final T mDefaultValue;
            private final ObjectConverter<T> mConverter;
            
            private ModelValueIdImpl( String name, T defaultValue, ObjectConverter<T> converter )
            {
                mName = checkNotNull( name );
                mDefaultValue = checkNotNull( defaultValue );
                mConverter = checkNotNull( converter );
            }
            
            @Override
            public String getName()
            {
                return mName;
            }

            @Override
            public T getDefaultValue()
            {
                return mDefaultValue;
            }

            @Override
            public String toString()
            {
                return "ModelValueIdImpl [mDescription=" + mName + ", mDefaultValue=" +
                       mDefaultValue + "]";
            }

            @Override
            public T valueFrom( Object object ) throws ObjectConversionException
            {
                return mConverter.convert( object );
            }
        }
    }
}
