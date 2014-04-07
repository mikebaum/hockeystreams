package org.mbaum.common.listener;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

public class ListenableSupport<T, L extends Listener<T>> implements Listenable<T, L>
{
	private final List<L> mListeners = newArrayList();
	private final AtomicBoolean mUnsentNotifications = new AtomicBoolean( false );
	private final Supplier<T> mValueSupplier;
	
	private boolean mPaused = false;
	
	public ListenableSupport( Supplier<T> valueSupplier )
    {
		mValueSupplier = valueSupplier;
    }

	@Override
	public void addListener( L listener )
	{
		mListeners.add( listener );
	}

	@Override
	public void removeListener( L listener )
	{
		mListeners.remove( listener );
	}
	
	@Override
	public void clearListeners()
	{
		mListeners.clear();
	}
	
	/**
	 * Will notify the listeners with the current value.
	 * 
	 * - If this {@link Listenable} is currently paused the notification will be delayed until resumed.
	 * - Only one notification will be sent, regardless how many request to notify listeners occurs while paused. 
	 * - All value changes that occur while paused will be transparent to the {@link Listener} as the delayed
	 *   notification will notify the Listeners with the current value.
	 */
	public void notifyListeners()
	{
		if ( mPaused )
		{
			mUnsentNotifications.set( true );
			return;
		}
		
		for ( L listener : ImmutableList.copyOf( mListeners ) )
			listener.handleChanged( mValueSupplier.get() );
	}
	
	public void destroy()
	{
		mListeners.clear();
	}
	
	/**
	 * Pause notification messages. While paused calling {@link #notifyListeners()} will have no immediate
	 * effect. The listeners will be notified when resumed, see {@link #resume()}.
	 */
	public void pause()
	{
		mPaused = true;
	}
	
	/**
	 * Resumes sending notifications. If while paused one or more calls to {@link #notifyListeners()} 
	 * were made they will be coalesced into one notification and sent to the listeners with the current 
	 * value after resuming. 
	 */
	public void resume()
	{		
		mPaused = false;
		
		if ( mUnsentNotifications.compareAndSet( true, false ) )
			notifyListeners();
	}
	
	public static <T, L extends Listener<T>> ListenableSupport<T, L> createListenableSupport( Supplier<T> valueSupplier )
	{
		return new ListenableSupport<T, L>( valueSupplier );
	}
}
