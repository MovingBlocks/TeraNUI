package org.terasology.nui.core;

import org.joml.Vector2f;
import org.terasology.nui.core.bind.UIProperty;
import org.terasology.nui.core.bind.UISignal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UIWidget implements AutoCloseable {
    private UIWidget parent;
    private List<UIWidget> children = new ArrayList<>();
    private boolean isFree = false;

    public final UISignal.UISignal0 destroy = new UISignal.UISignal0();

    public final UIProperty<Float> z = new UIProperty<>(0.0f);
    public final UIProperty<Vector2f> pos = new UIProperty<>(new Vector2f());
    public final UIProperty<Vector2f> size = new UIProperty<>(new Vector2f());

    public UIWidget(UIWidget parent) {
        this.parent = parent;
        this.parent.children.add(this);
    }

    public UIWidget(UIWidget parent, float z) {
        this(parent);
        this.z.set(z);
    }

    public UIWidget(UIWidget parent, Vector2f pos) {
        this(parent);
        this.pos.set(pos);
    }

    public UIWidget(UIWidget parent, Vector2f pos, Vector2f size) {
        this(parent);
        this.pos.set(pos);
        this.size.set(size);
    }

    public Collection<UIWidget> children() {
        return Collections.unmodifiableList(children);
    }

    public boolean isDisposed() {
        return isFree;
    }

    @Override
    public void close() {
        this.isFree = true;
        destroy.send();
    }
}
