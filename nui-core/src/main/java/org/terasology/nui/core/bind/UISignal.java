package org.terasology.nui.core.bind;

import com.google.common.collect.Multimap;
import org.terasology.nui.core.UIWidget;

import java.lang.ref.WeakReference;

public class UISignal {

    public static class UISignal0 extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot0> bindings;

        public UISignal0() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot0 slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send() {
            for (BindingPair pair : bindings.keys()) {
                for (UISlot.UISlot0 slot : bindings.get(pair)) {
                    slot.invoke();
                }
            }
        }
    }

    public static class UISignal1<T1> extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot1<T1>> bindings;

        public UISignal1() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot1<T1> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 handler) {
            for (BindingPair pair : bindings.keys()) {
                for (UISlot.UISlot1<T1> slot : bindings.get(pair)) {
                    slot.invoke(handler);
                }
            }
        }
    }

    public static class UISignal2<T1, T2> extends UISignal {
        private WeakReference<UIWidget> reference;

        public UISignal2() {
        }


        public void send(T1 t1, T2 t2) {

        }
    }


}


