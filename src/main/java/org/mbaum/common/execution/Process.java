package org.mbaum.common.execution;


public interface Process<C extends ProcessContext, R>
{
    R execute( C context ) throws Exception;
    
    String getDescription();
}
