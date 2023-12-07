package org.terasology.nui.core;

import org.terasology.nui.core.bind.BindingFunction;

import java.lang.ref.WeakReference;

public class UISlot<T extends BindingFunction> {
    private WeakReference<UIObject> reference;
    private T handler;

    public UISlot(T handler) {
        this.handler = handler;
    }

    public Object invoke(Object[] entry) {
        return null;
    }
}
