package org.terasology.nui.core.bind;

public interface Function3<T1, T2, T3> extends BindingFunction {
    void apply(T1 t1, T2 t2, T3 t3);
}
