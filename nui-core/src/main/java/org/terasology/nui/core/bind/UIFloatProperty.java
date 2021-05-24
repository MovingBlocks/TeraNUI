package org.terasology.nui.core.bind;

public class UIFloatProperty {
    private float value;
    public final UISignal.UISignal0 propertyChanged = new UISignal.UISignal0();

    public UIFloatProperty(float initial) {
        this.value = initial;
    }

    public void set(float value) {
        propertyChanged.send();
        this.value = value;
    }

    public float get() {
        return value;
    }
}
