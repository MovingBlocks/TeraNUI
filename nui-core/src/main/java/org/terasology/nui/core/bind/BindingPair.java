package org.terasology.nui.core.bind;

import org.terasology.nui.core.EventDispatcher;
import org.terasology.nui.core.UIEngine;
import org.terasology.nui.core.UIObject;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class BindingPair {
    private WeakReference<UIObject> signalWidget;
    private WeakReference<UIObject> slotWidget;
    private BindingType type;

    public BindingPair(UIObject signalWidget, UIObject slotWidget, BindingType type) {
        this.signalWidget = new WeakReference<>(signalWidget);
        this.slotWidget = new WeakReference<>(slotWidget);
        this.type = type;
    }

    public Optional<UIObject> getSignalWidget() {
        return Optional.ofNullable(signalWidget.get());
    }


    public Optional<UIObject> getSlotWidget() {
        return Optional.ofNullable(slotWidget.get());
    }

    public BindingType getBindingType() {
        return this.type;
    }

    public Optional<EventDispatcher> getSlotDispatcher() {
        UIObject target = this.slotWidget.get();
        if (target != null) {
            UIEngine engine = UIObject.getEngine(target);
            return Optional.ofNullable(engine.dispatcher());
        }
        return Optional.empty();
    }

    public boolean  isDisposed() {
        UIObject slotWid = signalWidget.get();
        UIObject targetWid = slotWidget.get();
        if (slotWid == null || slotWid.isDisposed()) {
            return true;
        }
        if (targetWid == null || targetWid.isDisposed()) {
            return true;
        }
        return false;

    }
}
