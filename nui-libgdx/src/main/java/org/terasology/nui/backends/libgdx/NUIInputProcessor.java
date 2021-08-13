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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import org.joml.Vector2i;
import org.terasology.input.ButtonState;
import org.terasology.input.InputType;
import org.terasology.input.Keyboard;
import org.terasology.input.device.CharKeyboardAction;
import org.terasology.input.device.MouseAction;
import org.terasology.input.device.RawKeyboardAction;

import java.util.LinkedList;
import java.util.Queue;

public class NUIInputProcessor implements InputProcessor {
    private Keyboard.Key lastKey;
    private Queue<RawKeyboardAction> keyboardActionQueue = new LinkedList<>();
    private Queue<CharKeyboardAction> keyboardCharQueue = new LinkedList<>();
    private Queue<MouseAction> mouseActionQueue = new LinkedList<>();
    private static NUIInputProcessor instance = new NUIInputProcessor();

    public static boolean CONSUME_INPUT = false;

    public NUIInputProcessor() {
    }

    public static void init() {
        InputMultiplexer inputMultiplexer;

        if (Gdx.input.getInputProcessor() instanceof InputMultiplexer) {
            inputMultiplexer = (InputMultiplexer)Gdx.input.getInputProcessor();
        } else {
            InputProcessor currentProcessor = Gdx.input.getInputProcessor();
            if (currentProcessor == null) {
                inputMultiplexer = new InputMultiplexer();
            } else {
                inputMultiplexer = new InputMultiplexer(currentProcessor);
            }
            Gdx.input.setInputProcessor(inputMultiplexer);
        }

        if (!inputMultiplexer.getProcessors().contains(instance, true)) {
            inputMultiplexer.addProcessor(0, instance);
        }
    }

    public static NUIInputProcessor getInstance() {
        return instance;
    }

    public Queue<RawKeyboardAction> getKeyboardInputQueue() {
        Queue<RawKeyboardAction> copy = new LinkedList<>(keyboardActionQueue);
        keyboardActionQueue.clear();
        return copy;
    }

    public Queue<CharKeyboardAction> getKeyboardCharQueue() {
        Queue<CharKeyboardAction> copy = new LinkedList<>(keyboardCharQueue);
        keyboardCharQueue.clear();
        return copy;
    }

    public Queue<MouseAction> getMouseInputQueue() {
        Queue<MouseAction> copy = new LinkedList<>(mouseActionQueue);
        mouseActionQueue.clear();
        return copy;
    }

    @Override
    public boolean keyDown(int keycode) {
        String keyName = Input.Keys.toString(keycode);
        if (keyName == null || keyName.equalsIgnoreCase("UNKNOWN")) {
            return false;
        }

        Keyboard.Key key = GDXInputUtil.GDXToNuiKey(keycode);
        if (key == null) {
            return false;
        }

        keyboardActionQueue.add(new RawKeyboardAction(key, ButtonState.DOWN));
        return CONSUME_INPUT;
    }

    @Override
    public boolean keyUp(int keycode) {
        String keyName = Input.Keys.toString(keycode);
        if (keyName == null || keyName.equalsIgnoreCase("UNKNOWN")) {
            return false;
        }

        Keyboard.Key key = GDXInputUtil.GDXToNuiKey(keycode);
        if (key == null) {
            return false;
        }

        keyboardActionQueue.add(new RawKeyboardAction(key, ButtonState.UP));
        return CONSUME_INPUT;
    }

    @Override
    public boolean keyTyped(char character) {
        keyboardCharQueue.add(new CharKeyboardAction(character));
        return CONSUME_INPUT;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2i pointerPosition = GDXInputUtil.GDXToNuiMousePosition(screenX, screenY);
        mouseActionQueue.add(new MouseAction(GDXInputUtil.GDXToNuiMouseButton(button), ButtonState.DOWN, pointerPosition, pointer));
        return CONSUME_INPUT;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2i pointerPosition = GDXInputUtil.GDXToNuiMousePosition(screenX, screenY);
        mouseActionQueue.add(new MouseAction(GDXInputUtil.GDXToNuiMouseButton(button), ButtonState.UP, pointerPosition, pointer));
        return CONSUME_INPUT;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        int amount = (amountY < 0 ? 1 : -1);
        mouseActionQueue.add(new MouseAction(InputType.MOUSE_WHEEL.getInput(amount), amount, GDXInputUtil.GDXToNuiMousePosition(Gdx.input.getX(), Gdx.input.getY())));
        return CONSUME_INPUT;
    }
}
