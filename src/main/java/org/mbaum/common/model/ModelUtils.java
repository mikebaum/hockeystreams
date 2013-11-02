package org.mbaum.common.model;


public final class ModelUtils
{
    private ModelUtils(){}

    public static <M extends Model<M>> ModelListener<M> createDoNothingListener()
    {
    	return new ModelListener<M>()
    	{
    		@Override
    		public void modelChanged( M model ) {}
    	};
    }
}
