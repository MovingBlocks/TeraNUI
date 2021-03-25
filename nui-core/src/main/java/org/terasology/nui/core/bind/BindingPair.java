package org.terasology.nui.core.bind;

import org.terasology.nui.core.UIWidget;

import java.lang.ref.WeakReference;

public class BindingPair {
    private WeakReference<UIWidget> wid1;
    private WeakReference<UIWidget> wid2;

    public BindingPair(UIWidget w1, UIWidget w2) {
        this.wid1 = new WeakReference<>(w1);
        this.wid2 = new WeakReference<>(w2);
    }
}
