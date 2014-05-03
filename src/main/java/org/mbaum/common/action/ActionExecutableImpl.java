package org.mbaum.common.action;

import static org.mbaum.common.action.ActionExecutableListener.WARNING_LISTENER;
import static org.mbaum.common.action.ActionModel.DESCRIPTION;
import static org.mbaum.common.action.ActionModel.ENABLED;
import static org.mbaum.common.execution.ProcessUtils.createExecutableProcessRunnable;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessListenerAdapter;
import org.mbaum.common.listener.Listener;
import org.mbaum.common.model.MutableModel;

import com.google.common.base.Preconditions;

final class ActionExecutableImpl<T> implements ActionExecutable
{
    private final long mThreadId;
    private final AtomicBoolean mIsExecuting = new AtomicBoolean( false );
    private final Executor mExecutor;
    private final ExecutableProcess<T> mExecutableProcess;
    private final ProcessListener<T> mProcessListener;
    private final MutableModel<ActionModel> mActionModel;
    private final Listener<MutableModel<ActionModel>> mActionModelListener;
    
    private ActionExecutableListener mListener = WARNING_LISTENER;
    
    private ActionExecutableImpl( ExecutableProcess<T> executableProcess, 
                                  MutableModel<ActionModel> actionModel, 
                                  Executor executor )
    {
        mActionModel = actionModel;
        mExecutor = executor;
        mThreadId = Thread.currentThread().getId();
        mExecutableProcess = executableProcess;
        
        mActionModelListener = createActionModelListener( this );
        actionModel.addListener( mActionModelListener );
        
        mProcessListener = createIsFinishedListener( executableProcess, actionModel.getValue( DESCRIPTION ), this );
        executableProcess.addListener( mProcessListener );
    }
    
    @Override
    public void destroy()
    {
        mListener = null;
        mActionModel.removeListener( mActionModelListener );
        mExecutableProcess.removeListener( mProcessListener );
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
        return mActionModel.getValue( ENABLED ) && ! mIsExecuting.get();
    }

    @Override
    public void execute()
    {
        checkThread();
        mExecutor.execute( createExecutableProcessRunnable( mExecutableProcess ) );
    }

    private void setExecuting( boolean isExecuting )
    {
        if ( mIsExecuting.getAndSet( isExecuting ) != isExecuting )
            fireActionEnabledChanged();
    }
    
    private void fireActionEnabledChanged()
    {
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
                                                                    final String description,
    																final ActionExecutableImpl<T> actionExecutable )
    {
        return new ProcessListenerAdapter<T>()
        {
        	@Override
        	public void handleStarted()
        	{
        		actionExecutable.setExecuting( true );
        	}
        	
            @Override
            public void handleFinished()
            {
            	actionExecutable.setExecuting( false );
            }
        };
    }

    private static <T> Listener<MutableModel<ActionModel>> createActionModelListener( final ActionExecutableImpl<T> actionExecutable )
    {
        return new Listener<MutableModel<ActionModel>>()
        {
            @Override
            public void handleChanged( MutableModel<ActionModel> model )
            {
                actionExecutable.fireActionEnabledChanged();
            }
        };
    }
    
    public static <T> ActionExecutable createActionExecutable( ExecutableProcess<T> executableProcess, 
                                                               MutableModel<ActionModel> actionModel, 
                                                               Executor executor )
    {
    	return new ActionExecutableImpl<T>( executableProcess, actionModel, executor );
    }
}