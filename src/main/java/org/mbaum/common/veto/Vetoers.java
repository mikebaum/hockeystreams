package org.mbaum.common.veto;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.ModelValidator;

public final class Vetoers
{
    private Vetoers() {}
    
    public static <M extends Model<M>> Vetoer createVetoer( M model, ModelValidator<M> validator )
    {
        return VetoerImpl.createVetoer( model, validator );
    }
}
