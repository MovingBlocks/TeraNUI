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
import org.terasology.input.device.CharKeyboardAction;
import org.terasology.input.device.KeyboardDevice;
import org.terasology.input.device.RawKeyboardAction;

import java.util.Queue;

public class LibGDXKeyboardDevice implements KeyboardDevice {
    public LibGDXKeyboardDevice() {
        NUIInputProcessor.init();
    }

    @Override
    public Queue<RawKeyboardAction> getInputQueue() {
        return NUIInputProcessor.getInstance().getKeyboardInputQueue();
    }

    @Override
    public Queue<CharKeyboardAction> getCharInputQueue() {
        return NUIInputProcessor.getInstance().getKeyboardCharQueue();
    }

    /**
     * @param key
     * @return The current state of the given key
     */
    @Override
    public boolean isKeyDown(int key) {
        return Gdx.input.isKeyPressed(GDXInputUtil.NuiToGDXKey(key));
    }
}
