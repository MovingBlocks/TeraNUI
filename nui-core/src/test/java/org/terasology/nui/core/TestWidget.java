package org.terasology.nui.core;


import org.terasology.nui.core.bind.Binding;
import org.terasology.nui.core.bind.UISignal;
import org.terasology.nui.core.bind.UISlot;
import org.terasology.nui.core.bind.Function1;

public class TestWidget extends UIWidget{
    private UISignal.UISignal1<Integer> a1 = new UISignal.UISignal1<>();
    private TestWidget2 widget2 = new TestWidget2(this);

    public class TestWidget2 extends UIWidget {
        private int counter;
        public final UISlot.UISlot1<Integer> consumer = new UISlot.UISlot1<>(new Function1<Integer>() {
            @Override
            public void apply(Integer integer) {
                counter += integer;
                handle1();
            }
        });

        public void  handle1() {

        }

        public TestWidget2(UIWidget parent) {
            super(parent);
        }
    }

    public TestWidget(UIWidget parent) {
        super(parent);
        Binding.bind(this, a1, widget2, widget2.consumer);
        a1.send(10);
    }
}
