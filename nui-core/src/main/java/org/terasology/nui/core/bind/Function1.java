package org.terasology.nui.core.bind;

@FunctionalInterface
public interface Function1<T1> extends BindingFunction {
    void apply(T1 t1);
}
