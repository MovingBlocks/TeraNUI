// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.input.device.nulldevice;

import com.google.common.collect.Queues;
import org.joml.Vector2i;
import org.terasology.nui.input.device.MouseAction;
import org.terasology.nui.input.device.MouseDevice;

import java.util.Queue;

/**
 *
 */
public class NullMouseDevice implements MouseDevice {
    @Override
    public Vector2i getPosition() {
        return new Vector2i();
    }

    @Override
    public Vector2i getDelta() {
        return new Vector2i();
    }

    @Override
    public boolean isButtonDown(int button) {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public Queue<MouseAction> getInputQueue() {
        return Queues.newArrayDeque();
    }

    @Override
    public void setGrabbed(boolean grabbed) {
    }
}
