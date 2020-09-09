// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.input.device;

import org.terasology.gestalt.module.sandbox.API;

import java.util.Queue;

/**
 *
 */
@API
public interface KeyboardDevice extends InputDevice {

    @Override
    Queue<KeyboardAction> getInputQueue();

    /**
     * @param key
     * @return The current state of the given key
     */
    boolean isKeyDown(int key);
}
