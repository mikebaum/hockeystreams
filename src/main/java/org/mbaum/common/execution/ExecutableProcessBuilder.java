package org.mbaum.common.execution;

public interface ExecutableProcessBuilder
{
	public <C extends ProcessContext, R> ExecutableProcess<R> buildExecutableProcess( Process<C, R> process,
																					  C context,
			                                                                          ProcessListener<R> listener );
	
	public <C extends ProcessContext, R> ExecutableProcess<R> buildExecutableProcess( Process<C, R> process,
		      																		  C context,
		      																		  ProcessListener<R> listener,
		      																		  ResultHandler<R> resultHandler );
}
