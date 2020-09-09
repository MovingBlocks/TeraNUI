// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.joml.Vector2i;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;

/**
 * See {@link NUIInputEvent}
 */
public final class NUIMouseWheelEvent extends NUIMouseEvent {
    private final int wheelTurns;

    public NUIMouseWheelEvent(MouseDevice mouse, KeyboardDevice keyboard, Vector2i relativeMousePosition,
                              int wheelTurns) {
        super(mouse, keyboard, relativeMousePosition);
        this.wheelTurns = wheelTurns;
    }

    public int getWheelTurns() {
        return wheelTurns;
    }
}
