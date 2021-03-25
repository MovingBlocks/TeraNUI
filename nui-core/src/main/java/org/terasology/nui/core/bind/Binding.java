package org.terasology.nui.core.bind;

import org.terasology.nui.core.UIWidget;

public class Binding {
    public static <T1> void bind(UIWidget wid1, UISignal.UISignal1<T1> signal, UIWidget wid2, UISlot.UISlot1<T1> slot) {
        BindingPair pair = new BindingPair(wid1, wid2);
        signal.register(pair, slot);
    }
}
