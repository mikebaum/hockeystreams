package org.mbaum.common.execution;


public interface Process<C extends ProcessContext>
{
    void execute( C context ) throws Exception;
    
    boolean canExecuteWith( C context );
    
    String getDescription();
}
