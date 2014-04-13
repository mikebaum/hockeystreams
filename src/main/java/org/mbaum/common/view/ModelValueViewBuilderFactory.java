package org.mbaum.common.view;

import static org.mbaum.common.view.JTextFieldBuilders.textFieldBuilder;

import java.util.Map;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.MutableModelValue;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public interface ModelValueViewBuilderFactory<T>
{
	ViewBuilder makeBuilder( MutableModelValue<?, T> value );
	
	public static class Factories
	{
		private static final Map<Class<?>, ModelValueViewBuilderFactory<?>> FACTORIES = Maps.newHashMap();
		private static final Converters CONVERTERS = new Converters();
		
		// TODO: add converters
		private static final class Converters
		{
			private static final Map<Class<?>, Function<String, ?>> CONVERTERS = Maps.newHashMap();
			
			@SuppressWarnings("unused")
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
                public ViewBuilder makeBuilder( MutableModelValue<?, String> value )
                {
	                return textFieldBuilder( value );
                }
			} );
			
			FACTORIES.put( Boolean.class, new ModelValueViewBuilderFactory<Boolean>(){
				@Override
                public ViewBuilder makeBuilder( MutableModelValue<?, Boolean> value )
                {
	                return new JCheckBoxBuilder( value );
                }
			} );
		}
		
		public static <M extends Model<M>, T> ViewBuilder getModelValueUIBuilder( final MutableModelValue<M, T> value )
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

			return textFieldBuilder( value, converter );
		}
	}
}
