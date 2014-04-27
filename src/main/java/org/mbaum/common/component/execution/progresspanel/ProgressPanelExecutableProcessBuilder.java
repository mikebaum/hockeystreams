package org.mbaum.common.component.execution.progresspanel;

import static org.mbaum.common.model.ProgressPanelModel.INDETERMINATE;
import static org.mbaum.common.model.ProgressPanelModel.MESSAGE;

import org.mbaum.common.execution.ExecutableProcess;
import org.mbaum.common.execution.ExecutableProcessBuilder;
import org.mbaum.common.execution.Process;
import org.mbaum.common.execution.ProcessContext;
import org.mbaum.common.execution.ProcessListener;
import org.mbaum.common.execution.ProcessUtils;
import org.mbaum.common.execution.ResultHandler;
import org.mbaum.common.model.MutableModel;
import org.mbaum.common.model.ProgressPanelModel;

public class ProgressPanelExecutableProcessBuilder implements ExecutableProcessBuilder
{
    private final MutableModel<ProgressPanelModel> mProgressPanelModel;

    public ProgressPanelExecutableProcessBuilder( MutableModel<ProgressPanelModel> progressPanelModel )
    {
        mProgressPanelModel = progressPanelModel;
    }

    @Override
    public <C extends ProcessContext, R> ExecutableProcess<R> buildExecutableProcess( Process<C, R> process,
                                                                                      C context,
                                                                                      ProcessListener<R> listener,
                                                                                      ResultHandler<R> resultHandler )
    {
        ExecutableProcess<R> executableProcess =
            ProcessUtils.createExecutableProcess( process, context, listener );

        ProcessListener<R> createProcessListener =
            createProcessListener( mProgressPanelModel, process.getDescription(), resultHandler );
        executableProcess.addListener( createProcessListener );

        return executableProcess;
    }

    @Override
    public <C extends ProcessContext, R> ExecutableProcess<R> buildExecutableProcess( Process<C, R> process,
                                                                                      C context,
                                                                                      ProcessListener<R> listener )
    {
        return buildExecutableProcess( process,
                                       context,
                                       listener,
                                       createDefaultResultHandler( mProgressPanelModel, process ) );
    }

    private static <R, C extends ProcessContext> ResultHandler<R> createDefaultResultHandler( final MutableModel<ProgressPanelModel> progressPanelModel,
                                                                                              final Process<C, R> process )
    {
        return new ResultHandler<R>()
        {
            @Override
            public void handleResult( R result )
            {
                progressPanelModel.setValue( MESSAGE, process.getDescription() + " complete" );
            }
        };
    }

    private static <R> ProcessListener<R> createProcessListener( final MutableModel<ProgressPanelModel> progressPanelModel,
                                                                 final String processDescription,
                                                                 final ResultHandler<R> resultHander )
    {
        return new ProcessListener<R>()
        {
            @Override
            public void handleStarted()
            {
                progressPanelModel.setValue( MESSAGE, "Performing: " + processDescription );
                progressPanelModel.setValue( INDETERMINATE, true );
            }

            @Override
            public void handleResult( R result )
            {
                resultHander.handleResult( result );
            }

            @Override
            public void handleFailed( Exception exception )
            {
                progressPanelModel.setValue( MESSAGE, processDescription + " failed" );
            }

            @Override
            public void handleFinished()
            {
                progressPanelModel.reset();
            }
        };
    }
}
