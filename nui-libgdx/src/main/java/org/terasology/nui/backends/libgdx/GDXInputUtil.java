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

import com.badlogic.gdx.Input;
import org.terasology.input.Keyboard;
import org.terasology.input.MouseInput;
import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Map;

public final class GDXInputUtil {
    private static float uiScale = 1.0f;

    private GDXInputUtil() {
    }

    private static Map<Integer, Keyboard.Key> keyMap = new HashMap<>();
    private static Map<Integer, MouseInput> mouseMap = new HashMap<>();
    private NUIInputProcessor keyboardInputProcessor;

    static {
        keyMap.put(Input.Keys.ANY_KEY, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.NUM_0, Keyboard.Key.KEY_0);
        keyMap.put(Input.Keys.NUM_1, Keyboard.Key.KEY_1);
        keyMap.put(Input.Keys.NUM_2, Keyboard.Key.KEY_2);
        keyMap.put(Input.Keys.NUM_3, Keyboard.Key.KEY_3);
        keyMap.put(Input.Keys.NUM_4, Keyboard.Key.KEY_4);
        keyMap.put(Input.Keys.NUM_5, Keyboard.Key.KEY_5);
        keyMap.put(Input.Keys.NUM_6, Keyboard.Key.KEY_6);
        keyMap.put(Input.Keys.NUM_7, Keyboard.Key.KEY_7);
        keyMap.put(Input.Keys.NUM_8, Keyboard.Key.KEY_8);
        keyMap.put(Input.Keys.NUM_9, Keyboard.Key.KEY_9);
        keyMap.put(Input.Keys.A, Keyboard.Key.A);
        keyMap.put(Input.Keys.ALT_LEFT, Keyboard.Key.LEFT_ALT);
        keyMap.put(Input.Keys.ALT_RIGHT, Keyboard.Key.RIGHT_ALT);
        keyMap.put(Input.Keys.APOSTROPHE, Keyboard.Key.APOSTROPHE);
        keyMap.put(Input.Keys.AT, Keyboard.Key.AT);
        keyMap.put(Input.Keys.B, Keyboard.Key.B);
        //keyMap.put(/*BACK*/ 4, Keyboard.Key.BACKSPACE);
        keyMap.put(Input.Keys.BACKSLASH, Keyboard.Key.BACKSLASH);
        keyMap.put(Input.Keys.C, Keyboard.Key.C);
        keyMap.put(Input.Keys.CALL, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.CAMERA, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.CLEAR, Keyboard.Key.CLEAR);
        keyMap.put(Input.Keys.COMMA, Keyboard.Key.COMMA);
        keyMap.put(Input.Keys.D, Keyboard.Key.D);
        //keyMap.put(/*DEL*/ 67, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.BACKSPACE, Keyboard.Key.BACKSPACE);
        keyMap.put(Input.Keys.FORWARD_DEL, Keyboard.Key.DELETE); // NOTE: FORWARD_DEL is DELETE: see https://github.com/libgdx/libgdx/issues/5291
        //keyMap.put(/*DPAD_CENTER*/ 23, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_DOWN*/ 20, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_LEFT*/ 21, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_RIGHT*/ 22, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_UP*/ 19, Keyboard.Key.NONE);
        //keyMap.put(/*CENTER*/ 23, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.DOWN, Keyboard.Key.DOWN);
        keyMap.put(Input.Keys.LEFT, Keyboard.Key.LEFT);
        keyMap.put(Input.Keys.RIGHT, Keyboard.Key.RIGHT);
        keyMap.put(Input.Keys.UP, Keyboard.Key.UP);
        keyMap.put(Input.Keys.E, Keyboard.Key.E);
        //keyMap.put(/*ENDCALL*/ 6, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.ENTER, Keyboard.Key.ENTER);
        //keyMap.put(/*ENVELOPE*/ 65, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.EQUALS, Keyboard.Key.EQUALS);
        //keyMap.put(/*EXPLORER*/ 64, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.F, Keyboard.Key.F);
        //keyMap.put(/*FOCUS*/ 80, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.G, Keyboard.Key.G);
        keyMap.put(Input.Keys.GRAVE, Keyboard.Key.GRAVE);
        keyMap.put(Input.Keys.H, Keyboard.Key.H);
        //keyMap.put(/*HEADSETHOOK*/ 79, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.HOME, Keyboard.Key.HOME);
        keyMap.put(Input.Keys.I, Keyboard.Key.I);
        keyMap.put(Input.Keys.J, Keyboard.Key.J);
        keyMap.put(Input.Keys.K, Keyboard.Key.K);
        keyMap.put(Input.Keys.L, Keyboard.Key.L);
        keyMap.put(Input.Keys.LEFT_BRACKET, Keyboard.Key.LEFT_BRACKET);
        keyMap.put(Input.Keys.M, Keyboard.Key.M);
        //keyMap.put(/*MEDIA_FAST_FORWARD*/ 90, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_NEXT*/ 87, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_PLAY_PAUSE*/ 85, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_PREVIOUS*/ 88, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_REWIND*/ 89, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_STOP*/ 86, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.MENU, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.MINUS, Keyboard.Key.MINUS);
        keyMap.put(Input.Keys.MUTE, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.N, Keyboard.Key.N);
        keyMap.put(Input.Keys.NOTIFICATION, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.NUM, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.O, Keyboard.Key.O);
        keyMap.put(Input.Keys.P, Keyboard.Key.P);
        keyMap.put(Input.Keys.PERIOD, Keyboard.Key.PERIOD);
        keyMap.put(Input.Keys.PLUS, Keyboard.Key.NUMPAD_PLUS);
        keyMap.put(Input.Keys.POUND, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.POWER, Keyboard.Key.POWER);
        keyMap.put(Input.Keys.Q, Keyboard.Key.Q);
        keyMap.put(Input.Keys.R, Keyboard.Key.R);
        keyMap.put(Input.Keys.RIGHT_BRACKET, Keyboard.Key.RIGHT_BRACKET);
        keyMap.put(Input.Keys.S, Keyboard.Key.S);
        keyMap.put(Input.Keys.SEARCH, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.SEMICOLON, Keyboard.Key.SEMICOLON);
        keyMap.put(Input.Keys.SHIFT_LEFT, Keyboard.Key.LEFT_SHIFT);
        keyMap.put(Input.Keys.SHIFT_RIGHT, Keyboard.Key.RIGHT_SHIFT);
        keyMap.put(Input.Keys.SLASH, Keyboard.Key.SLASH);
        //keyMap.put(/*SOFT_LEFT*/ 1, Keyboard.Key.NONE);
        //keyMap.put(/*SOFT_RIGHT*/ 2, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.SPACE, Keyboard.Key.SPACE);
        keyMap.put(Input.Keys.STAR, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.SYM, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.T, Keyboard.Key.T);
        keyMap.put(Input.Keys.TAB, Keyboard.Key.TAB);
        keyMap.put(Input.Keys.U, Keyboard.Key.U);
        keyMap.put(Input.Keys.UNKNOWN, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.V, Keyboard.Key.V);
        keyMap.put(Input.Keys.VOLUME_DOWN, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.VOLUME_UP, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.W, Keyboard.Key.W);
        keyMap.put(Input.Keys.X, Keyboard.Key.X);
        keyMap.put(Input.Keys.Y, Keyboard.Key.Y);
        keyMap.put(Input.Keys.Z, Keyboard.Key.Z);
        //keyMap.put(/*META_ALT_LEFT_ON*/ 16, Keyboard.Key.NONE);
        //keyMap.put(/*META_ALT_ON*/ 2, Keyboard.Key.NONE);
        //keyMap.put(/*META_ALT_RIGHT_ON*/ 32, Keyboard.Key.NONE);
        //keyMap.put(/*META_SHIFT_LEFT_ON*/ 64, Keyboard.Key.NONE);
        //keyMap.put(/*META_SHIFT_ON*/ 1, Keyboard.Key.NONE);
        //keyMap.put(/*META_SHIFT_RIGHT_ON*/ 128, Keyboard.Key.NONE);
        //keyMap.put(/*META_SYM_ON*/ 4, Keyboard.Key.NONE);
        keyMap.put(Input.Keys.CONTROL_LEFT, Keyboard.Key.LEFT_CTRL);
        keyMap.put(Input.Keys.CONTROL_RIGHT, Keyboard.Key.RIGHT_CTRL);
        keyMap.put(Input.Keys.ESCAPE, Keyboard.Key.ESCAPE);
        keyMap.put(Input.Keys.END, Keyboard.Key.END);
        keyMap.put(Input.Keys.INSERT, Keyboard.Key.INSERT);
        keyMap.put(Input.Keys.PAGE_UP, Keyboard.Key.PAGE_UP);
        keyMap.put(Input.Keys.PAGE_DOWN, Keyboard.Key.PAGE_DOWN);
        //keyMap.put(/*PICTSYMBOLS*/ 94, Keyboard.Key.NONE);
        //keyMap.put(/*SWITCH_CHARSET*/ 95, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_CIRCLE*/ 255, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_A*/ 96, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_B*/ 97, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_C*/ 98, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_X*/ 99, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_Y*/ 100, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_Z*/ 101, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_L1*/ 102, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_R1*/ 103, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_L2*/ 104, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_R2*/ 105, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_THUMBL*/ 106, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_THUMBR*/ 107, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_START*/ 108, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_SELECT*/ 109, Keyboard.Key.NONE);
        //keyMap.put(/*BUTTON_MODE*/ 110, Keyboard.Key.NONE);

        keyMap.put(Input.Keys.NUMPAD_0, Keyboard.Key.NUMPAD_0);
        keyMap.put(Input.Keys.NUMPAD_1, Keyboard.Key.NUMPAD_1);
        keyMap.put(Input.Keys.NUMPAD_2, Keyboard.Key.NUMPAD_2);
        keyMap.put(Input.Keys.NUMPAD_3, Keyboard.Key.NUMPAD_3);
        keyMap.put(Input.Keys.NUMPAD_4, Keyboard.Key.NUMPAD_4);
        keyMap.put(Input.Keys.NUMPAD_5, Keyboard.Key.NUMPAD_5);
        keyMap.put(Input.Keys.NUMPAD_6, Keyboard.Key.NUMPAD_6);
        keyMap.put(Input.Keys.NUMPAD_7, Keyboard.Key.NUMPAD_7);
        keyMap.put(Input.Keys.NUMPAD_8, Keyboard.Key.NUMPAD_8);
        keyMap.put(Input.Keys.NUMPAD_9, Keyboard.Key.NUMPAD_9);

        keyMap.put(Input.Keys.COLON, Keyboard.Key.COLON);
        keyMap.put(Input.Keys.F1, Keyboard.Key.F1);
        keyMap.put(Input.Keys.F2, Keyboard.Key.F2);
        keyMap.put(Input.Keys.F3, Keyboard.Key.F3);
        keyMap.put(Input.Keys.F4, Keyboard.Key.F4);
        keyMap.put(Input.Keys.F5, Keyboard.Key.F5);
        keyMap.put(Input.Keys.F6, Keyboard.Key.F6);
        keyMap.put(Input.Keys.F7, Keyboard.Key.F7);
        keyMap.put(Input.Keys.F8, Keyboard.Key.F8);
        keyMap.put(Input.Keys.F9, Keyboard.Key.F9);
        keyMap.put(Input.Keys.F10, Keyboard.Key.F10);
        keyMap.put(Input.Keys.F11, Keyboard.Key.F11);
        keyMap.put(Input.Keys.F12, Keyboard.Key.F12);

        // NOTE: Mouse mappings tested using Terasology's input dialog, so they may vary between mice
        mouseMap.put(Input.Buttons.LEFT, MouseInput.MOUSE_LEFT);
        mouseMap.put(Input.Buttons.RIGHT, MouseInput.MOUSE_RIGHT);
        mouseMap.put(Input.Buttons.MIDDLE, MouseInput.MOUSE_3);
        mouseMap.put(Input.Buttons.BACK, MouseInput.MOUSE_4);
        mouseMap.put(Input.Buttons.FORWARD, MouseInput.MOUSE_5);
    }

    public static Keyboard.Key GDXToNuiKey(int key) {
        return keyMap.getOrDefault(key, Keyboard.Key.NONE);
    }

    public static int NuiToGDXKey(int key) {
        for (Map.Entry<Integer, Keyboard.Key> entry : keyMap.entrySet()) {
            if (entry.getValue().getId() == key) {
                return entry.getKey();
            }
        }

        return -1;
    }

    public static MouseInput GDXToNuiMouseButton(int button) {
        return mouseMap.get(button);
    }

    public static int NuiToGDXMouseButton(int button) {
        for (Map.Entry<Integer, MouseInput> entry : mouseMap.entrySet()) {
            if (entry.getValue().getId() == button) {
                return entry.getKey();
            }
        }

        return -1;
    }

    public static char getGDXKeyChar(int key) {
        String name = Input.Keys.toString(key);
        if (name == null) {
            return 0;
        }
        name = name.replace("Space", " ")
                .replace("Tab", "\t")
                .replace("Numpad ", "");
        return name.length() > 1 ? 0 : name.charAt(0);
    }

    public static Vector2i GDXToNuiMousePosition(int x, int y) {
        return new Vector2i((int) (x * uiScale), (int) (y * uiScale));
    }

    public static void setUiScale(float scale) {
        GDXInputUtil.uiScale = scale;
    }
}
