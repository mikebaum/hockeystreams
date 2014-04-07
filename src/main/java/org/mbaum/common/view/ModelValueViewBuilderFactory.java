package org.mbaum.common.view;

import java.util.Map;

import org.mbaum.common.model.MutableModelValue;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;

public interface ModelValueViewBuilderFactory<T>
{
	ViewBuilder makeBuilder( MutableModelValue<T> value );
	
	public static class Factories
	{
		private static final Map<Class<?>, ModelValueViewBuilderFactory<?>> FACTORIES = Maps.newHashMap();
		private static final Converters CONVERTERS = new Converters();
		
		// TODO: add converters
		private static final class Converters
		{
			private static final Map<Class<?>, Function<String, ?>> CONVERTERS = Maps.newHashMap();
			
			private <T> void put( Class<T> clazz, Function<String, T> converter )
			{
				CONVERTERS.put( clazz, converter );
			}
			
			@SuppressWarnings("unchecked")
            private <T> Function<String, T> getConverter( Class<T> clazz )
			{
				return  (Function<String, T>) CONVERTERS.get( clazz );
			}
		}
		
		
		static
		{
			FACTORIES.put( String.class, new ModelValueViewBuilderFactory<String>(){
				@Override
                public ViewBuilder makeBuilder( MutableModelValue<String> value )
                {
					Function<String, String> extractor = Functions.identity();
	                return new JTextFieldBuilder<String>( value, extractor );
                }
			} );
			
			FACTORIES.put( Boolean.class, new ModelValueViewBuilderFactory<Boolean>(){
				@Override
                public ViewBuilder makeBuilder( MutableModelValue<Boolean> value )
                {
	                return new JCheckBoxBuilder( value );
                }
			} );
		}
		
		public static <T> ViewBuilder getModelValueUIBuilder( final MutableModelValue<T> value )
		{
			for( Map.Entry<Class<?>, ModelValueViewBuilderFactory<?>> entry : FACTORIES.entrySet() )
			{				
				if( entry.getKey().isAssignableFrom( value.get().getClass() ) )
				{					
					@SuppressWarnings("unchecked")
                    ModelValueViewBuilderFactory<T> factory = (ModelValueViewBuilderFactory<T>) entry.getValue();
					return (ViewBuilder) factory.makeBuilder( value );
				}
			}
			
			@SuppressWarnings("unchecked")
            Function<String, T> converter = (Function<String, T>) CONVERTERS.getConverter( (Class<T>) value.get().getClass() );

			return new JTextFieldBuilder<T>( value, converter );
		}
	}
}
