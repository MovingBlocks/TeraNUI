// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.backends.libgdx;

import com.badlogic.gdx.Gdx;
import org.joml.Vector2i;
import org.terasology.nui.input.device.MouseAction;
import org.terasology.nui.input.device.MouseDevice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A MouseDevice implementation using LibGDX to obtain input.
 */
public class LibGDXMouseDevice implements MouseDevice {
    private final LinkedList<MouseAction> inputQueue = new LinkedList<>();

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
    public Vector2i getDelta() {
        return new Vector2i(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
    }

    /**
     * @param button
     * @return The current state of the given button
     */
    @Override
    public boolean isButtonDown(int button) {
        return Gdx.input.isButtonPressed(GDXInputUtil.NuiToGDXMouseButton(button));
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
