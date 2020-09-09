// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.input;

/**
 * The type of an input
 */
public enum InputType {
    NONE {
        private final transient UnknownInput noneNone = new UnknownInput(this, 0);

        @Override
        public Input getInput(int id) {
            return noneNone;
        }

        @Override
        public Input getInput(String name) {
            return null;
        }
    },
    KEY {
        @Override
        public Input getInput(int id) {
            return Keyboard.Key.find(id);
        }

        @Override
        public Input getInput(String name) {
            return Keyboard.Key.find(name);
        }
    },
    MOUSE_BUTTON {
        @Override
        public Input getInput(int id) {
            return MouseInput.find(this, id);
        }

        @Override
        public Input getInput(String name) {
            return MouseInput.find(name);
        }
    },
    MOUSE_WHEEL {
        @Override
        public Input getInput(int id) {
            return MouseInput.find(this, id);
        }

        @Override
        public Input getInput(String name) {
            return MouseInput.find(name);
        }
    },
    CONTROLLER_BUTTON {
        @Override
        public Input getInput(int id) {
            return ControllerInput.find(this, id);
        }

        @Override
        public Input getInput(String name) {
            return ControllerInput.find(name);
        }
    },
    CONTROLLER_AXIS {
        @Override
        public Input getInput(int id) {
            return ControllerInput.find(this, id);
        }

        @Override
        public Input getInput(String name) {
            return ControllerInput.find(name);
        }
    };

    public abstract Input getInput(int id);
    public abstract Input getInput(String name);

    public static Input parse(String inputName) {
        for (InputType type : values()) {
            Input result = type.getInput(inputName);
            if (result != null) {
                return result;
            }
        }
        return UnknownInput.tryParse(inputName);
    }

}
