package org.terasology.nui.core;

import org.joml.Vector2f;
import org.terasology.nui.core.bind.Binding;
import org.terasology.nui.core.bind.UIProperty;
import org.terasology.nui.core.bind.UISignal;
import org.terasology.nui.core.bind.UISlot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class UIObject implements AutoCloseable {
    private UIObject parent;
    private final List<UIObject> children = new ArrayList<>();
    private boolean isFree = false;
    private UIEngine uiEngine;

    public static UIEngine getEngine(UIObject widget) {
        return widget.uiEngine;
    }


    public final UISignal.UISignal0 destroy = new UISignal.UISignal0();


    public UIObject(UIObject parent) {
        // pass uiEngine to new widget
        uiEngine = parent.uiEngine;

        this.parent = parent;
        this.parent.children.add(this);

    }
//
//    public UIObject(UIObject parent) {
//        this(parent);
//
//    }
//
//    public UIObject(UIObject parent, Vector2f pos) {
//        this(parent);
//        this.pos.set(pos);
//    }
//
//    public UIObject(UIObject parent, Vector2f pos, Vector2f size) {
//        this(parent);
//        this.pos.set(pos);
//        this.size.set(size);
//    }


    public Collection<UIObject> children() {
        return Collections.unmodifiableList(children);
    }

    public boolean isDisposed() {
        return isFree;
    }

    public void addChild(UIObject widget) {
        if (widget.parent != null) {
            widget.parent.removeChild(widget);
        }
        children.add(widget);
        widget.parent = this;
    }

    public void removeChild(UIObject widget) {
        children.remove(widget);
        widget.parent = null;
    }

    public UIObject getParent() {
        return parent;
    }


    @Override
    public void close() {
        this.isFree = true;
        destroy.send();
    }
}
