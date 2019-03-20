/*
 * Copyright 2018 MovingBlocks
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

package org.terasology.nui.input.events;

/**
 * Event when the mouse moved along one axis.
 * If the mouse moved diagonal, this will be reflected in two separate events.
 */
public class MouseAxisEvent extends AxisEvent {

    private static MouseAxisEvent event = new MouseAxisEvent(MouseAxis.X, 0, 0);

    public enum MouseAxis {
        X, Y;
    }

    private float value;
    private MouseAxis mouseAxis;

    protected MouseAxisEvent(MouseAxis axis, float value, float delta) {
        super(delta);
        this.mouseAxis = axis;
        this.value = value;
    }

    public MouseAxis getMouseAxis() {
        return mouseAxis;
    }

    @Override
    public float getValue() {
        return value;
    }

    public static MouseAxisEvent create(MouseAxis axis, float value, float delta) {
        event.reset(delta);
        event.mouseAxis = axis;
        event.value = value;
        return event;
    }

    public static MouseAxisEvent createCopy(MouseAxisEvent toBeCopied) {
        return new MouseAxisEvent(toBeCopied.getMouseAxis(), toBeCopied.getValue(), toBeCopied.getDelta());
    }
}