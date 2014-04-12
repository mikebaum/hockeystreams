package org.mbaum.common.execution;

import java.util.concurrent.ExecutorService;

import org.mbaum.common.Destroyable;

public interface ProcessExecutorService extends ExecutorService, ExecutableProcessBuilder, Destroyable
{

}
