// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.backends.libgdx;

import com.badlogic.gdx.Input;
import org.joml.Vector2i;
import org.terasology.nui.input.Keyboard;
import org.terasology.nui.input.MouseInput;

import java.util.HashMap;
import java.util.Map;

public final class GDXInputUtil {
    private static float uiScale = 1.0f;

    private GDXInputUtil() {
    }

    private static final Map<Integer, Keyboard.Key> keyMap = new HashMap<>();
    private static final Map<Integer, MouseInput> mouseMap = new HashMap<>();
    private NUIInputProcessor keyboardInputProcessor;

    static {
        keyMap.put(/*ANY_KEY*/ -1, Keyboard.Key.NONE);
        keyMap.put(/*NUM_0*/ 7, Keyboard.Key.KEY_0);
        keyMap.put(/*NUM_1*/ 8, Keyboard.Key.KEY_1);
        keyMap.put(/*NUM_2*/ 9, Keyboard.Key.KEY_2);
        keyMap.put(/*NUM_3*/ 10, Keyboard.Key.KEY_3);
        keyMap.put(/*NUM_4*/ 11, Keyboard.Key.KEY_4);
        keyMap.put(/*NUM_5*/ 12, Keyboard.Key.KEY_5);
        keyMap.put(/*NUM_6*/ 13, Keyboard.Key.KEY_6);
        keyMap.put(/*NUM_7*/ 14, Keyboard.Key.KEY_7);
        keyMap.put(/*NUM_8*/ 15, Keyboard.Key.KEY_8);
        keyMap.put(/*NUM_9*/ 16, Keyboard.Key.KEY_9);
        keyMap.put(/*A*/ 29, Keyboard.Key.A);
        keyMap.put(/*ALT_LEFT*/ 57, Keyboard.Key.LEFT_ALT);
        keyMap.put(/*ALT_RIGHT*/ 58, Keyboard.Key.RIGHT_ALT);
        keyMap.put(/*APOSTROPHE*/ 75, Keyboard.Key.APOSTROPHE);
        keyMap.put(/*AT*/ 77, Keyboard.Key.AT);
        keyMap.put(/*B*/ 30, Keyboard.Key.B);
        //keyMap.put(/*BACK*/ 4, Keyboard.Key.BACKSPACE);
        keyMap.put(/*BACKSLASH*/ 73, Keyboard.Key.BACKSLASH);
        keyMap.put(/*C*/ 31, Keyboard.Key.C);
        keyMap.put(/*CALL*/ 5, Keyboard.Key.NONE);
        keyMap.put(/*CAMERA*/ 27, Keyboard.Key.NONE);
        keyMap.put(/*CLEAR*/ 28, Keyboard.Key.CLEAR);
        keyMap.put(/*COMMA*/ 55, Keyboard.Key.COMMA);
        keyMap.put(/*D*/ 32, Keyboard.Key.D);
        //keyMap.put(/*DEL*/ 67, Keyboard.Key.NONE);
        keyMap.put(/*BACKSPACE*/ 67, Keyboard.Key.BACKSPACE);
        keyMap.put(/*FORWARD_DEL*/ 112, Keyboard.Key.DELETE); // NOTE: FORWARD_DEL is DELETE: see https://github.com/libgdx/libgdx/issues/5291
        //keyMap.put(/*DPAD_CENTER*/ 23, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_DOWN*/ 20, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_LEFT*/ 21, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_RIGHT*/ 22, Keyboard.Key.NONE);
        //keyMap.put(/*DPAD_UP*/ 19, Keyboard.Key.NONE);
        //keyMap.put(/*CENTER*/ 23, Keyboard.Key.NONE);
        keyMap.put(/*DOWN*/ 20, Keyboard.Key.DOWN);
        keyMap.put(/*LEFT*/ 21, Keyboard.Key.LEFT);
        keyMap.put(/*RIGHT*/ 22, Keyboard.Key.RIGHT);
        keyMap.put(/*UP*/ 19, Keyboard.Key.UP);
        keyMap.put(/*E*/ 33, Keyboard.Key.E);
        //keyMap.put(/*ENDCALL*/ 6, Keyboard.Key.NONE);
        keyMap.put(/*ENTER*/ 66, Keyboard.Key.ENTER);
        //keyMap.put(/*ENVELOPE*/ 65, Keyboard.Key.NONE);
        keyMap.put(/*EQUALS*/ 70, Keyboard.Key.EQUALS);
        //keyMap.put(/*EXPLORER*/ 64, Keyboard.Key.NONE);
        keyMap.put(/*F*/ 34, Keyboard.Key.F);
        //keyMap.put(/*FOCUS*/ 80, Keyboard.Key.NONE);
        keyMap.put(/*G*/ 35, Keyboard.Key.G);
        keyMap.put(/*GRAVE*/ 68, Keyboard.Key.GRAVE);
        keyMap.put(/*H*/ 36, Keyboard.Key.H);
        //keyMap.put(/*HEADSETHOOK*/ 79, Keyboard.Key.NONE);
        keyMap.put(/*HOME*/ 3, Keyboard.Key.HOME);
        keyMap.put(/*I*/ 37, Keyboard.Key.I);
        keyMap.put(/*J*/ 38, Keyboard.Key.J);
        keyMap.put(/*K*/ 39, Keyboard.Key.K);
        keyMap.put(/*L*/ 40, Keyboard.Key.L);
        keyMap.put(/*LEFT_BRACKET*/ 71, Keyboard.Key.LEFT_BRACKET);
        keyMap.put(/*M*/ 41, Keyboard.Key.M);
        //keyMap.put(/*MEDIA_FAST_FORWARD*/ 90, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_NEXT*/ 87, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_PLAY_PAUSE*/ 85, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_PREVIOUS*/ 88, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_REWIND*/ 89, Keyboard.Key.NONE);
        //keyMap.put(/*MEDIA_STOP*/ 86, Keyboard.Key.NONE);
        keyMap.put(/*MENU*/ 82, Keyboard.Key.NONE);
        keyMap.put(/*MINUS*/ 69, Keyboard.Key.MINUS);
        keyMap.put(/*MUTE*/ 91, Keyboard.Key.NONE);
        keyMap.put(/*N*/ 42, Keyboard.Key.N);
        keyMap.put(/*NOTIFICATION*/ 83, Keyboard.Key.NONE);
        keyMap.put(/*NUM*/ 78, Keyboard.Key.NONE);
        keyMap.put(/*O*/ 43, Keyboard.Key.O);
        keyMap.put(/*P*/ 44, Keyboard.Key.P);
        keyMap.put(/*PERIOD*/ 56, Keyboard.Key.PERIOD);
        keyMap.put(/*PLUS*/ 81, Keyboard.Key.NUMPAD_PLUS);
        keyMap.put(/*POUND*/ 18, Keyboard.Key.NONE);
        keyMap.put(/*POWER*/ 26, Keyboard.Key.POWER);
        keyMap.put(/*Q*/ 45, Keyboard.Key.Q);
        keyMap.put(/*R*/ 46, Keyboard.Key.R);
        keyMap.put(/*RIGHT_BRACKET*/ 72, Keyboard.Key.RIGHT_BRACKET);
        keyMap.put(/*S*/ 47, Keyboard.Key.S);
        keyMap.put(/*SEARCH*/ 84, Keyboard.Key.NONE);
        keyMap.put(/*SEMICOLON*/ 74, Keyboard.Key.SEMICOLON);
        keyMap.put(/*SHIFT_LEFT*/ 59, Keyboard.Key.LEFT_SHIFT);
        keyMap.put(/*SHIFT_RIGHT*/ 60, Keyboard.Key.RIGHT_SHIFT);
        keyMap.put(/*SLASH*/ 76, Keyboard.Key.SLASH);
        //keyMap.put(/*SOFT_LEFT*/ 1, Keyboard.Key.NONE);
        //keyMap.put(/*SOFT_RIGHT*/ 2, Keyboard.Key.NONE);
        keyMap.put(/*SPACE*/ 62, Keyboard.Key.SPACE);
        keyMap.put(/*STAR*/ 17, Keyboard.Key.NONE);
        keyMap.put(/*SYM*/ 63, Keyboard.Key.NONE);
        keyMap.put(/*T*/ 48, Keyboard.Key.T);
        keyMap.put(/*TAB*/ 61, Keyboard.Key.TAB);
        keyMap.put(/*U*/ 49, Keyboard.Key.U);
        keyMap.put(/*UNKNOWN*/ 0, Keyboard.Key.NONE);
        keyMap.put(/*V*/ 50, Keyboard.Key.V);
        keyMap.put(/*VOLUME_DOWN*/ 25, Keyboard.Key.NONE);
        keyMap.put(/*VOLUME_UP*/ 24, Keyboard.Key.NONE);
        keyMap.put(/*W*/ 51, Keyboard.Key.W);
        keyMap.put(/*X*/ 52, Keyboard.Key.X);
        keyMap.put(/*Y*/ 53, Keyboard.Key.Y);
        keyMap.put(/*Z*/ 54, Keyboard.Key.Z);
        //keyMap.put(/*META_ALT_LEFT_ON*/ 16, Keyboard.Key.NONE);
        //keyMap.put(/*META_ALT_ON*/ 2, Keyboard.Key.NONE);
        //keyMap.put(/*META_ALT_RIGHT_ON*/ 32, Keyboard.Key.NONE);
        //keyMap.put(/*META_SHIFT_LEFT_ON*/ 64, Keyboard.Key.NONE);
        //keyMap.put(/*META_SHIFT_ON*/ 1, Keyboard.Key.NONE);
        //keyMap.put(/*META_SHIFT_RIGHT_ON*/ 128, Keyboard.Key.NONE);
        //keyMap.put(/*META_SYM_ON*/ 4, Keyboard.Key.NONE);
        keyMap.put(/*CONTROL_LEFT*/ 129, Keyboard.Key.LEFT_CTRL);
        keyMap.put(/*CONTROL_RIGHT*/ 130, Keyboard.Key.RIGHT_CTRL);
        keyMap.put(/*ESCAPE*/ 131, Keyboard.Key.ESCAPE);
        keyMap.put(/*END*/ 132, Keyboard.Key.END);
        keyMap.put(/*INSERT*/ 133, Keyboard.Key.INSERT);
        keyMap.put(/*PAGE_UP*/ 92, Keyboard.Key.PAGE_UP);
        keyMap.put(/*PAGE_DOWN*/ 93, Keyboard.Key.PAGE_DOWN);
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

        keyMap.put(/*NUMPAD_0*/ 144, Keyboard.Key.NUMPAD_0);
        keyMap.put(/*NUMPAD_1*/ 145, Keyboard.Key.NUMPAD_1);
        keyMap.put(/*NUMPAD_2*/ 146, Keyboard.Key.NUMPAD_2);
        keyMap.put(/*NUMPAD_3*/ 147, Keyboard.Key.NUMPAD_3);
        keyMap.put(/*NUMPAD_4*/ 148, Keyboard.Key.NUMPAD_4);
        keyMap.put(/*NUMPAD_5*/ 149, Keyboard.Key.NUMPAD_5);
        keyMap.put(/*NUMPAD_6*/ 150, Keyboard.Key.NUMPAD_6);
        keyMap.put(/*NUMPAD_7*/ 151, Keyboard.Key.NUMPAD_7);
        keyMap.put(/*NUMPAD_8*/ 152, Keyboard.Key.NUMPAD_8);
        keyMap.put(/*NUMPAD_9*/ 153, Keyboard.Key.NUMPAD_9);

        keyMap.put(/*COLON*/ 243, Keyboard.Key.COLON);
        keyMap.put(/*F1*/ 244, Keyboard.Key.F1);
        keyMap.put(/*F2*/ 245, Keyboard.Key.F2);
        keyMap.put(/*F3*/ 246, Keyboard.Key.F3);
        keyMap.put(/*F4*/ 247, Keyboard.Key.F4);
        keyMap.put(/*F5*/ 248, Keyboard.Key.F5);
        keyMap.put(/*F6*/ 249, Keyboard.Key.F6);
        keyMap.put(/*F7*/ 250, Keyboard.Key.F7);
        keyMap.put(/*F8*/ 251, Keyboard.Key.F8);
        keyMap.put(/*F9*/ 252, Keyboard.Key.F9);
        keyMap.put(/*F10*/ 253, Keyboard.Key.F10);
        keyMap.put(/*F11*/ 254, Keyboard.Key.F11);
        keyMap.put(/*F12*/ 255, Keyboard.Key.F12);

        // NOTE: Mouse mappings tested using Terasology's input dialog, so they may vary between mice
        mouseMap.put(Input.Buttons.LEFT, MouseInput.MOUSE_LEFT);
        mouseMap.put(Input.Buttons.RIGHT, MouseInput.MOUSE_RIGHT);
        mouseMap.put(Input.Buttons.MIDDLE, MouseInput.MOUSE_3);
        mouseMap.put(Input.Buttons.BACK, MouseInput.MOUSE_4);
        mouseMap.put(Input.Buttons.FORWARD, MouseInput.MOUSE_5);
    }

    public static Keyboard.Key GDXToNuiKey(int key) {
        return keyMap.get(key);
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
