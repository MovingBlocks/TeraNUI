// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.events;

import org.terasology.nui.input.ButtonState;
import org.terasology.nui.input.Input;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;

/**
 * See {@link NUIInputEvent}
 */
public class NUIKeyEvent extends NUIInputEvent {
    private final Input key;
    private final char keyCharacter;
    private final ButtonState state;

    public NUIKeyEvent(MouseDevice mouse, KeyboardDevice keyboard, Input key, char keyChar, ButtonState state) {
        super(mouse, keyboard);
        this.key = key;
        this.keyCharacter = keyChar;
        this.state = state;
    }

    public Input getKey() {
        return key;
    }

    public char getKeyCharacter() {
        return keyCharacter;
    }

    public ButtonState getState() {
        return state;
    }

    public boolean isDown() {
        return state != ButtonState.UP;
    }
}
