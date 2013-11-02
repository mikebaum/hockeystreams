package org.mbaum.common.action;

import static org.mbaum.common.action.ActionExecutableListener.WARNING_LISTENER;
import static org.mbaum.common.execution.ProcessUtils.createExecutbleProcessRunnable;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessListener;

import com.google.common.base.Preconditions;

final class ActionExecutableImpl implements ActionExecutable
{
    private final ExecutableProcess<?> mExecutableProcess;
    private final long mThreadId;
    private ActionExecutableListener mListener = WARNING_LISTENER;
    private AtomicBoolean mIsExecuting = new AtomicBoolean( false );
    private Executor mExecutor;

    private ActionExecutableImpl( ExecutableProcess<?> executableProcess, Executor executor )
    {
        mExecutor = executor;
        mThreadId = Thread.currentThread().getId();
        mExecutableProcess = executableProcess;
    }

    @Override
    public void setListener( ActionExecutableListener listener )
    {
        checkThread();
        mListener = listener;
    }

    @Override
    public boolean isEnabled()
    {
        checkThread();
        return mExecutableProcess.canExecute() && mIsExecuting.get() == false;
    }

    @Override
    public void execute()
    {
        checkThread();
        mExecutor.execute( createExecutbleProcessRunnable( mExecutableProcess ) );
    }

    private void setExecuting( boolean isExecuting )
    {
        if ( mIsExecuting.getAndSet( isExecuting ) != isExecuting )
            mListener.actionEnabledChanged();
    }

    private void checkThread()
    {
        Preconditions.checkState( Thread.currentThread().getId() == mThreadId,
                                  "Current thread id: " + Thread.currentThread().getId() + ", does" +
                                  " not match the thread id: " + mThreadId + " from which this action" +
                                  "executable was created on. Please make sure to only access this" +
                                  " action executable from the same thread."  );
    }
    
    private static <T> ProcessListener<T> createIsFinishedListener( final ExecutableProcess<T> executableProcess,
    																final ActionExecutableImpl actionExecutable )
    {
        return new ProcessListener.ProcessListenerAdapter<T>()
        {
        	@Override
        	public void processStarted()
        	{
        		actionExecutable.setExecuting( true );
        	}
        	
            @Override
            public void processFinished()
            {
            	actionExecutable.setExecuting( false );
            }
        };
    }
    
    public static <T> ActionExecutable createActionExecutable( ExecutableProcess<T> executableProcess, Executor executor )
    {
    	ActionExecutableImpl actionExecutable = new ActionExecutableImpl( executableProcess, executor );
    	executableProcess.addListener( createIsFinishedListener( executableProcess, actionExecutable ) );
    	return actionExecutable;
    }
}