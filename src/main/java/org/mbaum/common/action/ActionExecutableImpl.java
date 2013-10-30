package org.mbaum.common.action;

import static org.mbaum.common.action.ActionExecutableListener.WARNING_LISTENER;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessUtils;

import com.google.common.base.Preconditions;

final class ActionExecutableImpl implements ActionExecutable
{
    private final ExecutableProcess mExecutableProcess;
    private final long mThreadId;
    private ActionExecutableListener mListener = WARNING_LISTENER;
    private AtomicBoolean mIsExecuting = new AtomicBoolean( false );
    private Executor mExecutor;

    ActionExecutableImpl( ExecutableProcess executableProcess, Executor executor )
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
        setExecuting( true );
        mExecutableProcess.addListener( createIsFinishedListener() );
        mExecutor.execute( ProcessUtils.createExecutbleProcessRunnable( mExecutableProcess ) );
    }
    
    private ProcessListener createIsFinishedListener()
    {
        return new ProcessListener.ProcessListenerAdapter()
        {
            @Override
            public void processFinished()
            {
                setExecuting( false );
                mExecutableProcess.removeListener( this );
            }
        };
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
}