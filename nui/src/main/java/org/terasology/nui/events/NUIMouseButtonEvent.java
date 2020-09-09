// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.joml.Vector2i;
import org.terasology.nui.input.ButtonState;
import org.terasology.nui.input.MouseInput;

public class NUIMouseButtonEvent extends NUIMouseEvent {
    private MouseInput button;
    private final ButtonState state;
    private final Vector2i mousePosition = new Vector2i();

    public NUIMouseButtonEvent(MouseInput button, ButtonState state, Vector2i mousePosition) {
        super(null, null, mousePosition);
        this.state = state;
        this.button = button;
    }

    public ButtonState getState() {
        return state;
    }

    public MouseInput getButton() {
        return button;
    }

    public String getMouseButtonName() {
        return button.getName();
    }

    public String getButtonName() {
        return "mouse:" + getMouseButtonName();
    }

    public Vector2i getMousePosition() {
        return new Vector2i(mousePosition);
    }

    protected void setButton(MouseInput button) {
        this.button = button;
    }

    public void setMousePosition(Vector2i mousePosition) {
        this.mousePosition.set(mousePosition);
    }
}
