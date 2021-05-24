package org.terasology.nui.core.bind;

import org.terasology.nui.core.UIObject;

public class Binding {
    private Binding() {
    }

    public static void bind(UIObject wid1, UISignal.UISignal0 signal, UIObject wid2, UISlot.UISlot0 slot) {
        bind(wid1, signal, wid2, slot, BindingType.Auto);
    }

    public static void bind(UIObject wid1, UISignal.UISignal0 signal, UIObject wid2, UISlot.UISlot0 slot, BindingType type) {
        BindingPair pair = new BindingPair(wid1, wid2, type);
        signal.register(pair, slot);
    }

    public static <T1> void bind(UIObject wid1, UISignal.UISignal1<T1> signal, UIObject wid2, UISlot.UISlot1<T1> slot) {
        bind(wid1, signal, wid2, slot, BindingType.Auto);
    }

    public static <T1> void bind(UIObject wid1, UISignal.UISignal1<T1> signal, UIObject wid2, UISlot.UISlot1<T1> slot, BindingType type) {
        BindingPair pair = new BindingPair(wid1, wid2, type);
        signal.register(pair, slot);
    }
}
