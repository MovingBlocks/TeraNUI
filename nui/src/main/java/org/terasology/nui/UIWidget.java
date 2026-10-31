/*
 * Copyright 2016 MovingBlocks
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
package org.terasology.nui;

import org.terasology.context.annotation.IndexInherited;
import org.terasology.nui.events.NUIBindButtonEvent;
import org.joml.Vector2i;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.events.NUICharEvent;
import org.terasology.nui.events.NUIKeyEvent;
import org.terasology.nui.events.NUIMouseButtonEvent;
import org.terasology.nui.events.NUIMouseClickEvent;
import org.terasology.nui.events.NUIMouseWheelEvent;
import org.terasology.nui.skin.UISkin;

import java.util.Collection;
import java.util.Optional;

/**
 */
@IndexInherited
public interface UIWidget extends Iterable<UIWidget> {

    String DEFAULT_MODE = "";
    String HOVER_MODE = "hover";
    String FOCUSED_MODE = "focused";
    String ACTIVE_MODE = "active";
    String BASE_PART = "base";
    String DISABLED_MODE = "disabled";

    String getId();

    UISkin getSkin();

    void setSkin(UISkin skin);

    String getFamily();

    void setFamily(String family);

    void bindFamily(Binding<String> binding);

    String getMode();

    /**
     * @return Whether the widget is currently visible and should be rendered
     */
    boolean isVisible();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    /**
     * Finds a widget with the given id and type, within the current widget and its contents.
     *
     * @param id
     * @param type
     * @param <T>
     * @return The widget with the given id and type, or null.
     */
    <T extends UIWidget> T find(String id, Class<T> type);

    /**
     * Try to find a widget with the given id and type, within the current widget and its contents.
     *
     * @param id of widget to search
     * @param <T> type of widget to cast
     * @return optional widget with the given id and type
     */
    <T extends UIWidget> Optional<T> tryFind(String id, Class<T> type);

    <T extends UIWidget> Collection<T> findAll(Class<T> type);

    void onDraw(Canvas canvas);

    void update(float delta);

    void onGainFocus();

    void onLoseFocus();

    void onMouseButtonEvent(NUIMouseButtonEvent event);

    void onMouseWheelEvent(NUIMouseWheelEvent event);


    /**
     * Handle raw input events. Raw keys, case-independency, keyboard layout indepencency.
     * Use this method for handle actions like "Press X to Win" or "Hold X key to Win"
     * If you needs to fill text field, or send chat, use  {@link UIWidget#onCharEvent(NUICharEvent)} instead.
     *
     * @param event raw input event
     * @return Whether the input should be consumed, and thus not propagated to other interaction regions
     */
    boolean onKeyEvent(NUIKeyEvent event);

    /**
     * Handle text input events. UTF-8 characters.
     * Use this method for handle text input.
     *
     * @param nuiEvent text input event
     * @return whether the input should be consumed, and thus not propagted to other interaction regions
     */
    boolean onCharEvent(NUICharEvent nuiEvent);

    /**
     * Executed when a registered input-bind is activated (e.g. when a key or mouse button is pressed)
     * @param event An event triggered by the bind activation (you can find which bind was activated using {@link NUIBindButtonEvent#getId()})
     */
    void onBindEvent(NUIBindButtonEvent event);

    /**
     * Returns the preferred content size of this widget.
     *
     * @param canvas A {@link Canvas} on which this widget is drawn.
     * @param sizeHint A {@link Vector2i} representing how much available space is for this widget.
     * @return A {@link Vector2i} which represents the preferred size of this widget.
     */
    Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint);

    Vector2i getMaxContentSize(Canvas canvas);

    boolean isSkinAppliedByCanvas();

    boolean canBeFocus();

    void bindTooltip(Binding<UIWidget> bind);

    void setTooltip(UIWidget value);

    UIWidget getTooltip();

    void bindTooltipString(Binding<String> bind);

    void setTooltip(String value);

    float getTooltipDelay();
}
