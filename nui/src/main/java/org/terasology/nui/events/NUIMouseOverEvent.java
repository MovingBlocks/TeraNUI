// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.joml.Vector2i;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;

/**
 * See {@link NUIMouseEvent}
 */
public final class NUIMouseOverEvent extends NUIMouseEvent {
    private final boolean topMostElement;

    public NUIMouseOverEvent(MouseDevice mouse, KeyboardDevice keyboard, Vector2i relativeMousePosition,
                             boolean topMostElement) {
        super(mouse, keyboard, relativeMousePosition);
        this.topMostElement = topMostElement;
    }

    public boolean isTopMostElement() {
        return topMostElement;
    }
}
