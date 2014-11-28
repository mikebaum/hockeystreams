package org.mbaum.common.model;

public interface CRUDModel<M extends ModelSpec> extends MutableModel<M>
{
    void create();
    
    void read();
    
    void update();
    
    void delete();
}
