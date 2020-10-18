/*
 * Copyright 2020 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui.backends.libgdx;

import com.badlogic.gdx.Gdx;
import org.joml.Vector2d;
import org.terasology.input.device.MouseAction;
import org.terasology.input.device.MouseDevice;
import org.joml.Vector2i;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A MouseDevice implementation using LibGDX to obtain input.
 */
public class LibGDXMouseDevice implements MouseDevice {
    private LinkedList<MouseAction> inputQueue = new LinkedList<>();

    public LibGDXMouseDevice() {
        NUIInputProcessor.init();
    }

    @Override
    public Queue<MouseAction> getInputQueue() {
        return NUIInputProcessor.getInstance().getMouseInputQueue();
    }

    /**
     * @return The current position of the mouse in screen space
     */
    @Override
    public Vector2i getPosition() {
        return GDXInputUtil.GDXToNuiMousePosition(Gdx.input.getX(), Gdx.input.getY());
    }

    /**
     * @return The change in mouse position over the last update
     */
    @Override
    public Vector2d getDelta() {
        return new Vector2d(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
    }

    /**
     * @param button
     * @return The current state of the given button
     */
    @Override
    public boolean isButtonDown(int button) {
        return Gdx.input.isButtonPressed(GDXInputUtil.NuiToGDXMouseButton(button));
    }

    @Override
    public void update() {

    }

    /**
     * @return Whether the mouse cursor is visible
     */
    @Override
    public boolean isVisible() {
        return Gdx.input.isCursorCatched();
    }

    /**
     * Specifies if the mouse is grabbed and there is thus no mouse cursor that can get to a border.
     *
     * @param grabbed
     */
    @Override
    public void setGrabbed(boolean grabbed) {
        Gdx.input.setCursorCatched(grabbed);
    }
}
