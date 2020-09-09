// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;

/**
 * The NUIManager has it's own event classes, so that it is independent from the entity event system. The event class
 * contains all the information needed to process the event. It is abstract as each event type should have it's own
 * class so that it is later on possible to add fields to a certain event without breaking signatures.
 */
public abstract class NUIInputEvent {
    private final MouseDevice mouse;
    private final KeyboardDevice keyboard;
    private boolean consumed;

    public NUIInputEvent(MouseDevice mouse, KeyboardDevice keyboard) {
        this.mouse = mouse;
        this.keyboard = keyboard;
    }

    public MouseDevice getMouse() {
        return mouse;
    }

    public KeyboardDevice getKeyboard() {
        return keyboard;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void consume() {
        consumed = true;
    }
}
