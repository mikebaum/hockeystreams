package org.mbaum.common.execution;

public interface ResultHandler<R>
{
	void handleResult( R result );
	
	public static class DoNothingResultHandler<R> implements ResultHandler<R>
	{
		private DoNothingResultHandler(){}

		@Override
		public void handleResult( R result ) {}	
		
		public static <R> ResultHandler<R> create()
		{
			return new DoNothingResultHandler<R>();
		}
	}
}
