package org.mbaum.common.component.execution;

import java.util.concurrent.Executor;

import org.mbaum.common.component.Component;
import org.mbaum.common.execution.ExecutableProcessBuilder;

public interface ExecutorComponent extends Component, ExecutableProcessBuilder, Executor
{

}
