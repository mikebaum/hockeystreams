package org.mbaum.common.model;

public interface Notifier
{
	void notifyListeners();
	
	void pause();
	
	void resume();
	
	public static Notifier DO_NOTHING_NOTIFIER = new Notifier()
	{
		@Override
		public void notifyListeners()
		{
			// intentionally does nothing
		}

		@Override
        public void pause()
        {
			// intentionally does nothing
        }

		@Override
        public void resume()
        {
			// intentionally does nothing
        }
	};
}
