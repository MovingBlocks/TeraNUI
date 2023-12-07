package org.terasology.nui.core.bind;

public class UIProperty<T1> {
    private T1 value;
    public final UISignal.UISignal0 propertyChanged = new UISignal.UISignal0();

    public UIProperty(T1 initial) {
        this.value = initial;
    }

    public void set(T1 value) {
        propertyChanged.send();
        this.value = value;
    }

    public T1 get() {
        return value;
    }
}
