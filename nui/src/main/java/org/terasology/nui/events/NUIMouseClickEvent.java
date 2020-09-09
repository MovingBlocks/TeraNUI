// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.joml.Vector2i;
import org.terasology.nui.input.MouseInput;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;

/**
 * See {@link NUIInputEvent}
 */
public class NUIMouseClickEvent extends NUIMouseEvent {
    private final MouseInput mouseButton;

    public NUIMouseClickEvent(MouseDevice mouse, KeyboardDevice keyboard, Vector2i relativeMousePosition,
                              MouseInput mouseButton) {
        super(mouse, keyboard, relativeMousePosition);
        this.mouseButton = mouseButton;
    }

    /**
     * @return the mouse button that was clicked
     */
    public MouseInput getMouseButton() {
        return mouseButton;
    }
}
