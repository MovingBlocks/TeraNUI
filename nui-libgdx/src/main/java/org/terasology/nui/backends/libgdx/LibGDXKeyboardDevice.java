// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.backends.libgdx;

import com.badlogic.gdx.Gdx;
import org.terasology.nui.input.device.KeyboardAction;
import org.terasology.nui.input.device.KeyboardDevice;

import java.util.Queue;

public class LibGDXKeyboardDevice implements KeyboardDevice {
    public LibGDXKeyboardDevice() {
        NUIInputProcessor.init();
    }

    @Override
    public Queue<KeyboardAction> getInputQueue() {
        return NUIInputProcessor.getInstance().getKeyboardInputQueue();
    }

    /**
     * @param key
     * @return The current state of the given key
     */
    @Override
    public boolean isKeyDown(int key) {
        return Gdx.input.isKeyPressed(GDXInputUtil.NuiToGDXKey(key));
    }
}
