// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.input.device.nulldevice;

import com.google.common.collect.Queues;
import org.terasology.nui.input.device.KeyboardAction;
import org.terasology.nui.input.device.KeyboardDevice;

import java.util.Queue;

/**
 *
 */
public class NullKeyboardDevice implements KeyboardDevice {

    @Override
    public boolean isKeyDown(int button) {
        return false;
    }

    @Override
    public Queue<KeyboardAction> getInputQueue() {
        return Queues.newArrayDeque();
    }
}
