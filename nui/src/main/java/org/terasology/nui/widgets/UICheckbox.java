// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets;

import org.joml.Vector2i;
import org.terasology.nui.ActivatableWidget;
import org.terasology.nui.BaseInteractionListener;
import org.terasology.nui.Canvas;
import org.terasology.nui.InteractionListener;
import org.terasology.nui.TabbingManager;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.databinding.DefaultBinding;
import org.terasology.nui.events.NUIMouseClickEvent;
import org.terasology.nui.input.MouseInput;

/**
 * A check-box. Hovering is supported.
 */
public class UICheckbox extends ActivatableWidget {
    public static final String HOVER_ACTIVE_MODE = "hover-active";

    private Binding<Boolean> active = new DefaultBinding<>(false);

    private final InteractionListener interactionListener = new BaseInteractionListener() {

        @Override
        public boolean onMouseClick(NUIMouseClickEvent event) {
            if (event.getMouseButton() == MouseInput.MOUSE_LEFT) {
                activateWidget();
                return true;
            }
            return false;
        }

    };

    public UICheckbox() {
    }

    public UICheckbox(String id) {
        super(id);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isEnabled()) {
            canvas.addInteractionRegion(interactionListener);
        }
    }

    @Override
    public String getMode() {
        if (!isEnabled()) {
            return DISABLED_MODE;
        } else if (interactionListener.isMouseOver() || (TabbingManager.focusedWidget != null && TabbingManager.focusedWidget.equals(this))) {
            if (active.get()) {
                return HOVER_ACTIVE_MODE;
            }
            return HOVER_MODE;
        } else if (active.get()) {
            return ACTIVE_MODE;
        }
        return DEFAULT_MODE;
    }

    /**
     * @return A boolean indicating the status of the checkbox
     */
    public boolean isChecked() {
        return active.get();
    }

    /**
     * @param checked A boolean setting the ticked state of the checkbox
     */
    public void setChecked(boolean checked) {
        active.set(checked);
    }

    @Override
    public void activateWidget() {
        setChecked(!isChecked());
        UICheckbox.super.activateWidget();
    }


    public void bindChecked(Binding<Boolean> binding) {
        this.active = binding;
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        return new Vector2i();
    }

    /**
     * Subscribes a listener that is called whenever this {@code UICheckBox} is activated.
     *
     * @param listener The {@link ActivateEventListener} to be subscribed
     */
    public void subscribe(ActivateEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribes a listener from this {@code UICheckBox}.
     *
     * @param listener The {@code ActivateEventListener}to be unsubscribed
     */
    public void unsubscribe(ActivateEventListener listener) {
        listeners.remove(listener);
    }
}
