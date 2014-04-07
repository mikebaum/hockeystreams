package org.mbaum.common.veto;

import org.mbaum.common.model.Model;
import org.mbaum.common.model.Model.ModelValueId;
import org.mbaum.common.model.ModelValidator;

public final class Vetoers
{
    private Vetoers() {}
    
    public static <I extends ModelValueId<?>, M extends Model<I, M>> Vetoer createVetoer( M model, ModelValidator<M> validator )
    {
        return VetoerImpl.createVetoer( model, validator );
    }
}
