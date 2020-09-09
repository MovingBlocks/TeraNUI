// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.joml.Vector2i;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;


/**
 * Base class for mouse related input events that the NUI manager is working with.
 * See also: {@link NUIInputEvent}
 */
public abstract class NUIMouseEvent extends NUIInputEvent {
    private final Vector2i relativeMousePosition;

    public NUIMouseEvent(MouseDevice mouse, KeyboardDevice keyboard, Vector2i relativeMousePosition) {
        super(mouse, keyboard);
        this.relativeMousePosition = relativeMousePosition;
    }

    /**
     *
     * @return The mouse position relative to the mouse interaction region.
     */
    public Vector2i getRelativeMousePosition() {
        return relativeMousePosition;
    }
}
