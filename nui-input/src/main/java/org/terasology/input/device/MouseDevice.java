/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.input.device;

import java.util.Queue;

import org.joml.Vector2d;
import org.joml.Vector2i;

/**
 */
public interface MouseDevice extends InputDevice {

    /**
     * @return A queue of all input actions that have occurred over the last update for this device
     */
    @Override
    Queue<MouseAction> getInputQueue();

    /**
     * @return The current position of the first mouse pointer in screen space
     */
    Vector2i getPosition();

    /**
     * @return The current position of a mouse pointer in screen space
     */
    default Vector2i getPosition(int pointer) {
        if (pointer == 0) {
            return getPosition();
        } else {
            // Multiple pointers not supported, so return a zero value.
            return new Vector2i();
        }
    }

    /**
     * @return The change in mouse position over the last update
     */
    Vector2d getDelta();

    /**
     * @param button
     * @return The current state of the given button
     */
    boolean isButtonDown(int button);

    void update();

    /**
     * @return Whether the mouse cursor is visible
     */
    boolean isVisible();

    /**
     * Specifies if the mouse is grabbed and there is thus no mouse cursor that can get to a border.
     */
    void setGrabbed(boolean grabbed);

    /**
     * @return The maximum number of supported pointers for this device.
     */
    default int getMaxPointers() {
        // Normal desktop mice only support a single pointer, so 1 is a sensible default.
        // Touch screens can support many pointers though, so this can change.
        return 1;
    }
}
