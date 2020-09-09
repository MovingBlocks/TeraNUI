// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.input.device;

import org.joml.Vector2i;
import org.terasology.gestalt.module.sandbox.API;

import java.util.Queue;

/**
 *
 */
@API
public interface MouseDevice extends InputDevice {

    /**
     * @return A queue of all input actions that have occurred over the last update for this device
     */
    @Override
    Queue<MouseAction> getInputQueue();

    /**
     * @return The current position of the mouse in screen space
     */
    Vector2i getPosition();

    /**
     * @return The change in mouse position over the last update
     */
    Vector2i getDelta();

    /**
     * @param button
     * @return The current state of the given button
     */
    boolean isButtonDown(int button);

    /**
     * @return Whether the mouse cursor is visible
     */
    boolean isVisible();

    /**
     * Specifies if the mouse is grabbed and there is thus no mouse cursor that can get to a border.
     */
    void setGrabbed(boolean grabbed);
}
