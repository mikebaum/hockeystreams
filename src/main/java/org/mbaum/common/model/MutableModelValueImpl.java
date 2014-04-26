package org.mbaum.common.model;

import static org.mbaum.common.listener.ListenableSupport.createListenableSupport;

import org.mbaum.common.listener.ListenableSupport;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.value.Value;

class MutableModelValueImpl<M extends ModelSpec, T> implements MutableModelValue<M, T>
{
	private final Value<T> mValue;
	private final ListenableSupport<T, Listener<T>> mListenableSupport;
    private final ModelValueId<M, T> mId;

    public MutableModelValueImpl( ModelValueId<M, T> id, Value<T> currentValue )
	{
		mValue = currentValue;
		mId = id;
		mListenableSupport = createListenableSupport( this );
	}
    
    @Override
    public void destroy()
    {
        mListenableSupport.destroy();
    }

	@Override
	public T get()
	{
		return mValue.get();
	}

	@Override
	public void set( T value )
	{
		if ( mValue.set( value ) )
			mListenableSupport.notifyListeners();
	}

	@Override
    public void reset()
    {
		set( mId.getDefaultValue() );
    }
	
	@Override
	public ModelValueId<M, T> getId()
	{
	    return mId;
	}

	@Override
    public void addListener( Listener<T> listener )
    {
		mListenableSupport.addListener( listener );
    }

	@Override
    public void removeListener( Listener<T> listener )
    {
		mListenableSupport.addListener( listener );
    }

    @Override
    public String toString()
    {
        return "MutableModelValueImpl [mId=" + mId + ", mValue=" + mValue + "]";
    }
}
