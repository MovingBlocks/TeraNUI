// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.canvas;

import org.joml.Vector2i;
import org.terasology.nui.Canvas;
import org.terasology.nui.input.MouseInput;

/**
 */
public interface CanvasControl extends Canvas {

    void preRender();

    void postRender();

    void processMousePosition(Vector2i position);

    boolean processMouseClick(MouseInput button, Vector2i pos);

    boolean processMouseRelease(MouseInput button, Vector2i pos);

    boolean processMouseWheel(int wheelTurns, Vector2i pos);

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
