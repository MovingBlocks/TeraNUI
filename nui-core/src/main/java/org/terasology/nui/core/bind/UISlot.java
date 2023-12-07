package org.terasology.nui.core.bind;

public abstract class UISlot {

    public static class UISlot0 extends UISlot {
        private final Function0 binding;
        public UISlot0(Function0 binding) {
            this.binding = binding;
        }

        public void invoke() {
            binding.apply();
        }
    }

    public static class UISlot1<T1> extends UISlot {
        private final Function1<T1> binding;
        public UISlot1(Function1<T1> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1) {
            binding.apply(t1);
        }
    }

    public static class UISlot2<T1, T2> extends UISlot {
        private final Function2<T1, T2> binding;
        public UISlot2(Function2<T1, T2> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2) {
            binding.apply(t1, t2);
        }
    }

    public static class UISlot3<T1,T2,T3> extends UISlot {
        private final Function3<T1,T2,T3> binding;
        public UISlot3(Function3<T1,T2,T3> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2, T3 t3) {
            binding.apply(t1, t2, t3);
        }
    }

    public static class UISlot4<T1,T2,T3,T4> extends UISlot {
        private final Function4<T1, T2, T3, T4> binding;
        public UISlot4(Function4<T1, T2, T3, T4> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2, T3 t3, T4 t4) {
            binding.apply(t1, t2, t3, t4);
        }
    }

    public static class UISlot5<T1,T2,T3,T4,T5> extends UISlot {
        private final Function5<T1,T2,T3,T4,T5> binding;
        public UISlot5(Function5<T1,T2,T3,T4,T5> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
            binding.apply(t1, t2, t3, t4, t5);
        }
    }

    public static class UISlot6<T1,T2,T3,T4,T5,T6> extends UISlot {
        private final Function6<T1, T2, T3, T4, T5, T6> binding;

        public UISlot6(Function6<T1, T2, T3, T4, T5, T6> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
            binding.apply(t1, t2, t3, t4, t5, t6);
        }
    }

    public static class UISlot7<T1,T2,T3,T4,T5,T6,T7> extends UISlot {
        private final Function7<T1, T2, T3, T4, T5, T6, T7> binding;

        public UISlot7(Function7<T1, T2, T3, T4, T5, T6, T7> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
            binding.apply(t1, t2, t3, t4, t5, t6, t7);
        }
    }

    public static class UISlot8<T1,T2,T3,T4,T5,T6,T7,T8> extends UISlot {
        private final Function8<T1, T2, T3, T4, T5, T6, T7, T8> binding;

        public UISlot8(Function8<T1, T2, T3, T4, T5, T6, T7, T8> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
            binding.apply(t1, t2, t3, t4, t5, t6, t7, t8);
        }
    }
}
