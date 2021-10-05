/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.nui.canvas;

import org.terasology.input.MouseInput;
import org.joml.Vector2i;
import org.terasology.nui.Canvas;

/**
 */
public interface CanvasControl extends Canvas {

    void preRender();

    void postRender();

    /**
     * Call this method when the mouse position changes. It will update the position of the primary mouse pointer.
     * This method does not support multiple pointers. Use {@link #processMousePosition(Vector2i, int)} for that case if supported.
     * @param position The position of the mouse pointer
     */
    void processMousePosition(Vector2i position);

    /**
     * Call this method when the position of a mouse pointer changes.
     *
     * The pointers must have a consistent ordering in the positions array e.g.
     * the first pointer position must be at index 0, the second at index 1, third at index 2 etc.
     * The same indices should be provided as to the {@link #processMouseClick(MouseInput, Vector2i, int)}
     * and {@link #processMouseRelease(MouseInput, Vector2i, int)} methods.
     * @param position The position of the mouse pointer
     * @param pointer The mouse pointer performing the action
     */
    void processMousePosition(Vector2i position, int pointer);

    /**
     * Call this method when a mouse button is pressed. It will update the state of the primary mouse pointer.
     * This method does not support multiple pointers. Use {@link #processMouseClick(MouseInput, Vector2i, int)} for that case.
     * @param button The button pressed
     * @param pos The mouse position when the button was pressed
     * @return Whether the input should be consumed
     */
    boolean processMouseClick(MouseInput button, Vector2i pos);

    /**
     * Call this method when a mouse button associated with a pointer is pressed.
     * @param button The button pressed
     * @param pos The mouse position when the button was pressed
     * @param pointer The mouse pointer performing the action
     * @return Whether the input should be consumed
     */
    boolean processMouseClick(MouseInput button, Vector2i pos, int pointer);

    /**
     * Call this method when a mouse button is released. It will update the state of the primary mouse pointer.
     * This method does not support multiple pointers. Use {@link #processMouseRelease(MouseInput, Vector2i, int)} for that case.
     * @param button The button released
     * @param pos The mouse position when the button was released
     * @return Whether the input should be consumed
     */
    boolean processMouseRelease(MouseInput button, Vector2i pos);

    /**
     * Call this method when a mouse button associated with a pointer is released.
     * @param button The button released
     * @param pos The mouse position when the button was released
     * @param pointer The mouse pointer performing the action
     * @return Whether the input should be consumed
     */
    boolean processMouseRelease(MouseInput button, Vector2i pos, int pointer);

    /**
     * Call this method when the mouse wheel is turned. It will update the state of the primary mouse pointer.
     * This method does not support multiple pointers. Use {@link #processMouseWheel(int, Vector2i, int)} for that case.
     * @param wheelTurns the number of turns moved by the wheel (< 0 = down, 0 = no movement, > 0 = up).
     * @param pos The mouse position when the wheel was turned
     * @return Whether the input should be consumed
     */
    boolean processMouseWheel(int wheelTurns, Vector2i pos);

    /**
     * Call this method when the mouse wheel associated with a pointer is turned
     * @param wheelTurns the number of turns moved by the wheel (< 0 = down, 0 = no movement, > 0 = up).
     * @param pos The mouse position when the wheel was turned
     * @param pointer The mouse pointer performing the action
     * @return Whether the input should be consumed
     */
    boolean processMouseWheel(int wheelTurns, Vector2i pos, int pointer);

    /**
     * Returns the scale factor to scale the UI by, relative to the screen size.
     * < 1 is smaller, > 1 is larger and = 1 is the same as the screen size.
     * @return the current UI scale
     */
    float getUiScale();

    /**
     * Sets the UI scale factor, relative to the screen size
     * < 1 is smaller, > 1 is larger and = 1 is the same as the screen size.
     * @param uiScale the scale factor to use
     */
    void setUiScale(float uiScale);
}
