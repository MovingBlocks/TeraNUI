// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.events;

import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.nui.input.ButtonState;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;

/**
 *
 */
public class NUIBindButtonEvent extends NUIInputEvent {

    private final ResourceUrn id;
    private final ButtonState state;

    public NUIBindButtonEvent(MouseDevice mouseDevice, KeyboardDevice keyboardDevice, ResourceUrn buttonId,
                              ButtonState newState) {
        super(mouseDevice, keyboardDevice);

        this.id = buttonId;
        this.state = newState;
    }

    public ResourceUrn getId() {
        return id;
    }

    public ButtonState getState() {
        return state;
    }
}
