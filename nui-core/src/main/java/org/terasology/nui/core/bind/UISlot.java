package org.terasology.nui.core.bind;

public class UISlot {

    public static class UISlot0 extends UISlot {
        private Function0 binding;
        public UISlot0(Function0 binding) {
            this.binding = binding;
        }

        public void invoke() {
            binding.apply();
        }
    }

    public static class UISlot1<T1> extends UISlot {
        private Function1<T1> binding;
        public UISlot1(Function1<T1> binding) {
            this.binding = binding;
        }

        public void invoke(T1 t1) {
            binding.apply(t1);
        }
    }
}
