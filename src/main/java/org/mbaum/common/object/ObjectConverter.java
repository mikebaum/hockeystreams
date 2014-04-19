package org.mbaum.common.object;


public interface ObjectConverter<T>
{
    T convert( Object object ) throws ObjectConversionException;
    
    
    public static class DefaultObjectConverter<T> implements ObjectConverter<T>
    {
        @Override
        public T convert( Object object ) throws ObjectConversionException
        {
            try
            {
                @SuppressWarnings("unchecked")
                T convertedObject = (T) object;
                return convertedObject;
            }
            catch ( ClassCastException exception )
            {
                throw new ObjectConversionException( "Exception while converting attempting to convert: " + object, 
                                                     exception );
            }
        }
        
        public static <T> ObjectConverter<T> newConverter()
        {
            return new DefaultObjectConverter<T>();
        }
    }
}
