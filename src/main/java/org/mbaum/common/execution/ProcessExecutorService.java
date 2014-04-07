package org.mbaum.common.execution;

import java.util.concurrent.ExecutorService;

// TODO: should be Destroyable to stop any running tasks
public interface ProcessExecutorService extends ExecutorService, ExecutableProcessBuilder
{

}
