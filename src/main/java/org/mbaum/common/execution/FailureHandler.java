package org.mbaum.common.execution;


public interface FailureHandler
{
    void handleFailure( Exception exception );
}
