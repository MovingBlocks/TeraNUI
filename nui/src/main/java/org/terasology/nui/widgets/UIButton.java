/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui.widgets;

import org.terasology.input.MouseInput;
import org.joml.Vector2i;
import org.terasology.nui.asset.Sound;
import org.terasology.nui.asset.font.Font;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.ActivatableWidget;
import org.terasology.nui.BaseInteractionListener;
import org.terasology.nui.Canvas;
import org.terasology.nui.InteractionListener;
import org.terasology.nui.LayoutConfig;
import org.terasology.nui.TabbingManager;
import org.terasology.nui.TextLineBuilder;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.databinding.DefaultBinding;
import org.terasology.nui.events.NUIMouseClickEvent;
import org.terasology.nui.events.NUIMouseReleaseEvent;

import java.util.List;

/**
 * A widget displaying a clickable button, containing text and an optional image. The button can make a sound when
 * clicked, which is set by-default to {@link UIButton#DEFAULT_CLICK_SOUND}.
 */
public class UIButton extends ActivatableWidget {
    public static final String DOWN_MODE = "down";
    public static Sound DEFAULT_CLICK_SOUND = null;

    /**
     * The {@link Binding} containing the {@link UITextureRegion} corresponding to the image shown on this button
     */
    @LayoutConfig
    private Binding<UITextureRegion> image = new DefaultBinding<>();

    /**
     * The {@code Binding} containing the text to be shown on this button
     */
    @LayoutConfig
    private Binding<String> text = new DefaultBinding<>("");

    // TODO: Should clickSound be included here?

    /**
     * The {@code Binding} containing the {@link Sound} to be played when this button is clicked
     */
    @LayoutConfig
    private Binding<Sound> clickSound = new DefaultBinding<>(DEFAULT_CLICK_SOUND);

    /**
     * The {@code Binding} containing the float representing the volume of the click sound, 1.0 by default
     */
    @LayoutConfig
    private Binding<Float> clickVolume = new DefaultBinding<>(1.0f);

    /**
     * Whether the button is currently being pressed
     */
    private boolean down;

    /**
     * An {@link InteractionListener} that listens for mouse interaction with this button
     */
    private InteractionListener interactionListener = new BaseInteractionListener() {

        @Override
        public boolean onMouseClick(NUIMouseClickEvent event) {
            if (event.getMouseButton() == MouseInput.MOUSE_LEFT) {
                down = true;
                return true;
            }
            return false;
        }

        @Override
        public void onMouseRelease(NUIMouseReleaseEvent event) {
            if (event.getMouseButton() == MouseInput.MOUSE_LEFT) {
                if (isMouseOver() && isEnabled()) {
                    activateWidget();
                }
                down = false;
            }
        }
    };

    /**
     * The {@code Binding} containing the boolean representing of active status, false by default.
     */
    private Binding<Boolean> active = new DefaultBinding<>(false);

    /**
     * Creates an empty {@code UIButton}.
     */
    public UIButton() {
    }

    /**
     * Creates an empty {@code UIButton} with the given id.
     *
     * @param id The id assigned to this {@code UIButton}
     */
    public UIButton(String id) {
        super(id);
    }

    /**
     * Creates a {@code UIButton} with the given id, containing the given text.
     *
     * @param id The id assigned to this {@code UIButton}
     * @param text The text shown on this {@code UIButton}
     */
    public UIButton(String id, String text) {
        super(id);
        this.text.set(text);
    }

    /**
     * Creates a {@code UIButton} with the given id, containing the text in the given {@code Binding}.
     *
     * @param id The id assigned to this {@code UIButton}
     * @param text The {@code Binding} containing the text shown on this {@code UIButton}
     */
    public UIButton(String id, Binding<String> text) {
        super(id);
        this.text = text;
    }

    /**
     * Handles how the {@code UIButton} is drawn.
     * This is called every frame.
     *
     * @param canvas The {@link Canvas} on which this {@code UIButton} is drawn
     */
    @Override
    public void onDraw(Canvas canvas) {
        if (image.get() != null) {
            canvas.drawTexture(image.get());
        }
        canvas.drawText(text.get());
        if (isEnabled() || down) {
            canvas.addInteractionRegion(interactionListener);
        }
    }

    /**
     * Retrieves the preferred content size of the {@code UIButton}.
     * This is the minimum size this layout will take, given no space restrictions.
     *
     * @param canvas The {@code Canvas} on which the {@code UIButton} is drawn
     * @param areaHint A {@link Vector2i} representing the available space for this {@code UIButton}
     * @return A {@link Vector2i} representing the preferred content size of the {@code UIButton}
     */
    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i areaHint) {
        Font font = canvas.getCurrentStyle().getFont();
        List<String> lines = TextLineBuilder.getLines(font, text.get(), areaHint.x);
        return font.getSize(lines);
    }

    /**
     * Retrieves the current mode of this {@code UIButton}.
     * <p><ul>
     * <li> DISABLED_MODE - The {@code UIButton} is disabled
     * <li> DOWN_MODE - The {@code UIButton} is being pressed or active
     * <li> HOVER_MODE - The mouse is hovering over the {@code UIButton}
     * <li> DEFAULT_MODE - The default mode if no other modes are applicable
     * </ul></p>
     *
     * @return A {@code String} representing the current mode of this {@code UIButton}
     */
    @Override
    public String getMode() {
        if (!isEnabled()) {
            return DISABLED_MODE;
        } else if (down || isActive() || (TabbingManager.focusedWidget != null && TabbingManager.focusedWidget.equals(this))) {
            return DOWN_MODE;
        } else if (interactionListener.isMouseOver()) {
            return HOVER_MODE;
        }
        return DEFAULT_MODE;
    }

    @Override
    protected void activateWidget() {
        if (getClickSound() != null) {
            getClickSound().play(getClickVolume());
        }
        super.activateWidget();
    }

    /**
     * Binds the text to be shown on this {@code UIButton}.
     *
     * @param binding The {@code Binding} containing the text
     */
    public void bindText(Binding<String> binding) {
        this.text = binding;
    }

    /**
     * Retrieves the text shown on this {@code UIButton}.
     *
     * @return The text shown on this {@code UIButton}
     */
    public String getText() {
        return text.get();
    }

    /**
     * Sets the text shown on this {@code UIButton}.
     *
     * @param text The text to be shown on this {@code UIButton}
     */
    public void setText(String text) {
        this.text.set(text);
    }

    /**
     * Binds the image shown on this {@code UIButton}.
     *
     * @param binding The {@code Binding} containing the {@code UITextureRegion} corresponding to the image
     */
    public void bindImage(Binding<UITextureRegion> binding) {
        this.image = binding;
    }

    /**
     * Sets the image shown on this {@code UIButton}.
     *
     * @param image The {@code UITextureRegion} corresponding to the image
     */
    public void setImage(UITextureRegion image) {
        this.image.set(image);
    }

    /**
     * Retrieves the the image shown on this {@code UIButton}.
     *
     * @return A {@code UITextureRegion} corresponding to the image
     */
    public UITextureRegion getImage() {
        return image.get();
    }

    /**
     * Binds the click sound played when this {@code UIButton} is clicked.
     *
     * @param binding The {@code Binding} containing the {@code Sound} corresponding to the click sound
     */
    public void bindClickSound(Binding<Sound> binding) {
        clickSound = binding;
    }

    /**
     * Retrieves the click sound played when this {@code UIButton} is clicked.
     *
     * @return A {@code StaticSound} corresponding to the click sound
     */
    public Sound getClickSound() {
        return clickSound.get();
    }

    /**
     * Sets the click sound played when this {@code UIButton} is clicked.
     *
     * @param val A {@code StaticSound} corresponding to the click sound
     */
    public void setClickSound(Sound val) {
        clickSound.set(val);
    }

    /**
     * Binds the volume of the click sound.
     *
     * @param binding The {@code Binding} containing the float representing the volume the click sound
     */
    public void bindClickVolume(Binding<Float> binding) {
        clickVolume = binding;
    }

    /**
     * Retrieves the volume of the click sound.
     *
     * @return A float representing the volume of the click sound
     */
    public float getClickVolume() {
        return clickVolume.get();
    }

    /**
     * Sets the volume of the click sound.
     *
     * @param val The float representing the volume of the click sound
     */
    public void setClickVolume(float val) {
        clickVolume.set(val);
    }

    /**
     * Subscribes a listener that is called whenever this {@code UIButton} is activated.
     *
     * @param listener The {@link ActivateEventListener} to be subscribed
     */
    public void subscribe(ActivateEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribes a listener from this {@code UIButton}.
     *
     * @param listener The {@code ActivateEventListener}to be unsubscribed
     */
    public void unsubscribe(ActivateEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the active status of this {@code UIButton}.
     */
    public void setActive(boolean val) {
        this.active.set(val);
    }

    /**
     * Returns if the button is active or not.
     */
    public boolean isActive() {
        return active.get();
    }
}
