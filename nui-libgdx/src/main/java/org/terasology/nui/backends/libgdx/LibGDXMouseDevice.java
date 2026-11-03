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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import org.joml.Vector2d;
import org.terasology.input.device.MouseAction;
import org.terasology.input.device.MouseDevice;
import org.joml.Vector2i;

import java.util.Queue;

/**
 * A MouseDevice implementation using LibGDX to obtain input.
 */
public class LibGDXMouseDevice implements MouseDevice {
    // The maximum number of touches that LibGDX supports on Android is 20.
    // See https://github.com/libgdx/libgdx/blob/5eac848925d6e1f24070f887cbfaf99bb8bc4a63/backends/gdx-backend-android/src/com/badlogic/gdx/backends/android/AndroidInput.java#L99
    private static final int MAX_POINTERS = 20;
    // The default value has been calibrated by trial and error, mostly. You can set your own stickiness in the constructor.
    private static final int DEFAULT_POINTER_STICKINESS = 4;
    /**
     * Defines how "sticky" a pointer is, meaning how many updates it should be retained for after the touch is released.
     * This value can be fine-tuned based on the desired general responsiveness of the UI.
     */
    private final int pointerStickiness;
    /**
     * Flags a pointer for "removal" when it is no longer present on the screen.
     *
     * When a finger is removed from the touch screen, that pointer will still remain in the last known
     * position by-default. This makes sense on a desktop where pointing devices will always be present.
     *
     * It does not make sense for a mobile device though, where touches are assumed to be transient.
     * In order to overcome this limitation, the pointer is placed into an almost certainly off-screen location
     * (NUI doesn't seem to do any checks on this) when the corresponding touch has been removed.
     * The removePointer variable is used to delay this removal by a single update, so that UI widgets have time
     * to register the removal first (e.g. for button de-presses).
     */
    private final int[] pointerCooldowns;

    public LibGDXMouseDevice() {
        this(DEFAULT_POINTER_STICKINESS);
    }

    /**
     * @param pointerStickiness Defines how "sticky" a pointer is,
     *                          meaning how many updates it should be retained for after the touch is released.
     */
    public LibGDXMouseDevice(int pointerStickiness) {
        NUIInputProcessor.init();
        this.pointerStickiness = pointerStickiness;
        pointerCooldowns = new int[MAX_POINTERS];
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

    @Override
    public Vector2i getPosition(int pointer) {
        if (pointer < 0 || pointer >= getMaxPointers()) {
            throw new IndexOutOfBoundsException("Attempted to access pointer " + pointer + "when there are only " +
                    getMaxPointers() + " pointers available.");
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (Gdx.input.isTouched(pointer)) {
                pointerCooldowns[pointer] = pointerStickiness;
            } else if (pointerCooldowns[pointer] <= 0) {
                pointerCooldowns[pointer] = 0;
                // Since touches are mapped to pointers on Android, reset the pointer when not currently touching.
                // Set the pointer to an off-screen location, so it acts as if it were not present.
                return new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
            }
        }

        return GDXInputUtil.GDXToNuiMousePosition(Gdx.input.getX(pointer), Gdx.input.getY(pointer));
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
        for (int pointer = 0; pointer < MAX_POINTERS; pointer++) {
            if (pointerCooldowns[pointer] > 0) {
                pointerCooldowns[pointer]--;
            }
        }
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

    @Override
    public int getMaxPointers() {
        return MAX_POINTERS;
    }
}
