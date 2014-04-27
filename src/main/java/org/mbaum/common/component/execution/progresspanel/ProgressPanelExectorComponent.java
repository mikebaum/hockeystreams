package org.mbaum.common.component.execution.progresspanel;

import static org.mbaum.common.execution.ProcessExecutorServiceImpl.createExecutorService;
import static org.mbaum.common.model.MutableModel.Builder.createMutableModel;

import org.mbaum.common.component.AbstractComponent;
import org.mbaum.common.component.execution.ExecutorComponent;
import org.mbaum.common.component.execution.progresspanel.view.ProgressPanel;
import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.execution.ProcessExecutorService;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ResultHandler;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.model.ProgressPanelModel;
import org.mbaum.common.view.View;

public class ProgressPanelExectorComponent extends AbstractComponent implements ExecutorComponent
{
    private final MutableModel<ProgressPanelModel> mProgressPanelModel;
    private final ProgressPanel mProgressPanel;
    private final ProcessExecutorService mExecutorService;

    public ProgressPanelExectorComponent( String executorName )
    {
        mProgressPanelModel = d( createMutableModel( ProgressPanelModel.class ) );
        mProgressPanel = d( new ProgressPanel( mProgressPanelModel ) );
        mExecutorService = d( createExecutorService( new ProgressPanelExecutableProcessBuilder( mProgressPanelModel ), 
                                                     executorName ) );
    }

    @Override
    public View getView()
    {
        return mProgressPanel;
    }

    @Override
    public <C extends ProcessContext, R> ExecutableProcess<R> buildExecutableProcess( Process<C, R> process,
                                                                                      C context,
                                                                                      ProcessListener<R> listener )
    {
        return mExecutorService.buildExecutableProcess( process, context, listener );
    }

    @Override
    public <C extends ProcessContext, R> ExecutableProcess<R> buildExecutableProcess( Process<C, R> process,
                                                                                      C context,
                                                                                      ProcessListener<R> listener,
                                                                                      ResultHandler<R> resultHandler )
    {
        return mExecutorService.buildExecutableProcess( process, context, listener, resultHandler );
    }

    @Override
    public void execute( Runnable executableProcess )
    {
        mExecutorService.execute( executableProcess );
    }
}
