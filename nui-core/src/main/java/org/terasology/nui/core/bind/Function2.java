package org.terasology.nui.core.bind;

public interface Function2<T1, T2> extends BindingFunction {
    void apply(T1 t1, T2 t2);
}
