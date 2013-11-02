package org.mbaum.common.execution;


public abstract class AbstractProcess<C extends ProcessContext, R> implements Process<C, R>
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
