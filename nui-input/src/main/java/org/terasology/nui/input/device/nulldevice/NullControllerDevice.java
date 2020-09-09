// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.input.device.nulldevice;

import org.terasology.nui.input.ControllerDevice;
import org.terasology.nui.input.device.ControllerAction;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * A dummy implementation of {@link ControllerDevice}.
 */
public class NullControllerDevice implements ControllerDevice {

    @Override
    public Queue<ControllerAction> getInputQueue() {
        return new ArrayDeque<>();
    }

    @Override
    public List<String> getControllers() {
        return Collections.emptyList();
    }
}
