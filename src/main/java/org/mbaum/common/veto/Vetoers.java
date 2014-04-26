package org.mbaum.common.veto;

import org.mbaum.common.model.ModelSpec;
import org.mbaum.common.model.ModelValidator;
import org.mbaum.common.model.MutableModel;

public final class Vetoers
{
    private Vetoers() {}
    
    public static <M extends ModelSpec> Vetoer createVetoer( MutableModel<M> model, ModelValidator<M> validator )
    {
        return VetoerImpl.createVetoer( model, validator );
    }
}
