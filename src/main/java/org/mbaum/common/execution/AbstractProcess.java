package org.mbaum.common.execution;


public abstract class AbstractProcess<C extends ProcessContext> implements Process<C>
{
    private final String mDescription;
    
    protected AbstractProcess( String description )
    {
        mDescription = description;
    }

    @Override
    public String getDescription()
    {
        return mDescription;
    }
}
