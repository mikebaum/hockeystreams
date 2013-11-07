package org.mbaum.common.veto;

public interface VetoListener
{
	void vetoChanged();
	
	public static final VetoListener NULL_LISTENER = new VetoListener()
	{
		@Override
		public void vetoChanged() {}
	};
}
