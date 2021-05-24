package org.terasology.nui.core.bind;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Iterator;

public class UISignal {

    protected boolean handle(BindingPair pair, Runnable runnable) {
        runnable.run();
        return true;
    }

    protected boolean validate(BindingPair pair, Iterator<BindingPair> pairs) {
        if (pair.isDisposed()) {
            pairs.remove();
            return false;
        }
        return true;
    }

    public static class UISignal0 extends UISignal {
        private Multimap<BindingPair, UISlot.UISlot0> bindings;

        public UISignal0() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot0 slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send() {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (pair.isDisposed()) {
                    pairs.remove();
                    continue;
                }
                for (UISlot.UISlot0 slot : bindings.get(pair)) {
                    handle(pair, slot::invoke);
                }
            }
        }
    }

    public static class UISignal1<T1> extends UISignal {
        private Multimap<BindingPair, UISlot.UISlot1<T1>> bindings = ArrayListMultimap.create();

        public UISignal1() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot1<T1> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot1<T1> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1));
                }
            }
        }
    }

    public static class UISignal2<T1, T2> extends UISignal {
        private final Multimap<BindingPair, UISlot.UISlot2<T1, T2>> bindings = ArrayListMultimap.create();

        public UISignal2() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot2<T1, T2> slot) {
            bindings.put(pair, slot);
            return true;
        }


        public void send(T1 t1, T2 t2) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot2<T1, T2> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2));
                }
            }
        }
    }

    public static class UISignal3<T1, T2, T3> extends UISignal {
        private Multimap<BindingPair, UISlot.UISlot3<T1, T2, T3>> bindings = ArrayListMultimap.create();

        public UISignal3() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot3<T1, T2, T3> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1, T2 t2, T3 t3) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot3<T1, T2, T3> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2, t3));
                }
            }
        }
    }

    public static class UISignal4<T1, T2, T3, T4> extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot4<T1, T2, T3, T4>> bindings = ArrayListMultimap.create();

        public UISignal4() {
        }

        protected boolean register(BindingPair pair, UISlot.UISlot4<T1, T2, T3, T4> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1, T2 t2, T3 t3, T4 t4) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot4<T1, T2, T3, T4> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2, t3, t4));
                }
            }
        }
    }

    public static class UISignal5<T1, T2, T3, T4, T5> extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot5<T1, T2, T3, T4, T5>> bindings = ArrayListMultimap.create();

        public UISignal5() {

        }

        protected boolean register(BindingPair pair, UISlot.UISlot5<T1, T2, T3, T4, T5> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot5<T1, T2, T3, T4, T5> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2, t3, t4, t5));
                }
            }
        }
    }

    public static class UISignal6<T1, T2, T3, T4, T5, T6> extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot6<T1, T2, T3, T4, T5, T6>> bindings = ArrayListMultimap.create();

        public UISignal6() {

        }

        protected boolean register(BindingPair pair, UISlot.UISlot6<T1, T2, T3, T4, T5, T6> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot6<T1, T2, T3, T4, T5, T6> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2, t3, t4, t5, t6));
                }
            }
        }
    }

    public static class UISignal7<T1, T2, T3, T4, T5, T6, T7> extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot7<T1, T2, T3, T4, T5, T6, T7>> bindings = ArrayListMultimap.create();

        public UISignal7() {

        }

        protected boolean register(BindingPair pair, UISlot.UISlot7<T1, T2, T3, T4, T5, T6, T7> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot7<T1, T2, T3, T4, T5, T6, T7> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2, t3, t4, t5, t6, t7));
                }
            }
        }
    }

    public static class UISignal8<T1, T2, T3, T4, T5, T6, T7, T8> extends UISignal {
        public Multimap<BindingPair, UISlot.UISlot8<T1, T2, T3, T4, T5, T6, T7, T8>> bindings = ArrayListMultimap.create();

        public UISignal8() {

        }

        protected boolean register(BindingPair pair, UISlot.UISlot8<T1, T2, T3, T4, T5, T6, T7, T8> slot) {
            bindings.put(pair, slot);
            return true;
        }

        public void send(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
            Iterator<BindingPair> pairs = bindings.keySet().iterator();
            while (pairs.hasNext()) {
                BindingPair pair = pairs.next();
                if (!validate(pair, pairs)) continue;
                for (UISlot.UISlot8<T1, T2, T3, T4, T5, T6, T7, T8> slot : bindings.get(pair)) {
                    handle(pair, () -> slot.invoke(t1, t2, t3, t4, t5, t6, t7, t8));
                }
            }
        }
    }
}


