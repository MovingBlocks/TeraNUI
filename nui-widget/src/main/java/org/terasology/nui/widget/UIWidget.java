package org.terasology.nui.widget;

import org.joml.Vector2f;
import org.terasology.nui.core.UIObject;
import org.terasology.nui.core.bind.Binding;
import org.terasology.nui.core.bind.UIProperty;
import org.terasology.nui.core.bind.UISlot;

public class UIWidget extends UIObject {
    public final UIProperty<Float> z = new UIProperty<>(0.0f);
    public final UIProperty<Vector2f> pos = new UIProperty<>(new Vector2f());
    public final UIProperty<Vector2f> size = new UIProperty<>(new Vector2f());

    private final UISlot.UISlot0 layoutChange = new UISlot.UISlot0(this::updateLayout);

    public UIWidget(UIObject parent) {
        super(parent);

        Binding.bind(this, z.propertyChanged, this, layoutChange);
        Binding.bind(this, pos.propertyChanged, this, layoutChange);
        Binding.bind(this, size.propertyChanged, this, layoutChange);
    }

    public void updateLayout() {
    }
}
