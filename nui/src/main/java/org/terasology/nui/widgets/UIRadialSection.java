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
package org.terasology.nui.widgets;

import org.terasology.joml.geom.Rectanglei;
import org.joml.Vector2i;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.Canvas;
import org.terasology.nui.LayoutConfig;
import org.terasology.nui.TabbingManager;
import org.terasology.nui.UIWidget;
import org.terasology.nui.WidgetWithOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * One radial section of the Radial Ring
 */
public class UIRadialSection extends WidgetWithOrder {

    private Rectanglei infoRegion;
    private Rectanglei innerRegion;
    private Rectanglei sectionRegion;
    private UITextureRegion sectionTexture;
    private UITextureRegion selectedTexture;
    private Boolean isSelected = false;
    private List<ActivateEventListener> listeners;

    //TODO: Consider bringing back binding to icon,text and widget in UIRadialSection.java
    //TODO: Use bindings in future. Previously used bindings were throwing some exceptions not even allowing to open the screen with UIRadialRing, so this is a quick fix - conversion from binded properties to standard ones.
    @LayoutConfig
    private UITextureRegion icon;
    @LayoutConfig
    private String text;
    @LayoutConfig
    private UIWidget widget;

    public UIRadialSection(UITextureRegion sectionTexture, UITextureRegion selectedTexture) {
        this.sectionTexture = sectionTexture;
        this.selectedTexture = selectedTexture;

        setId("");
        if (TabbingManager.isInitialized()) {
            TabbingManager.addToUsedNums(this.getOrder());
            TabbingManager.addToWidgetsList(this);
            initialized = true;
        }
    }

    public UIRadialSection(String id) {
        this.setId(id);
        if (TabbingManager.isInitialized()) {
            TabbingManager.addToUsedNums(this.getOrder());
            TabbingManager.addToWidgetsList(this);
            initialized = true;
        }
    }

    @Override
    public String getMode() {
        if  (isSelected) {
            return ACTIVE_MODE;
        } else if (TabbingManager.focusedWidget != null && TabbingManager.focusedWidget.equals(this)) {
            isSelected = true;
            return ACTIVE_MODE;
        }
        return DEFAULT_MODE;
    }

    /**
     * Draws the widget
     *
     * @param canvas The canvas to draw on.
     */
    public void onDraw(Canvas canvas) {
        canvas.getRegion();
        canvas.drawTexture(sectionTexture, sectionRegion);

        if (icon != null) {
            canvas.drawTexture(icon, innerRegion);
        }

        if (text != null) {
            canvas.drawText(text, innerRegion);
        }
        if (isSelected) {
            canvas.drawTexture(selectedTexture, sectionRegion);
            if (widget != null) {
                canvas.drawWidget(widget, infoRegion);
            }
        }
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        return sectionRegion == null ? new Vector2i() : new Vector2i(sectionRegion.lengthX(), sectionRegion.lengthY());
    }

    /**
     * Add a listener to this section. It will be fired when the section is activated
     *
     * @param listener The listener to add
     */
    public void addListener(ActivateEventListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    /**
     * Removes a listener from the section.
     *
     * @param listener
     */
    public void removeListener(ActivateEventListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Activates the section, triggering all listeners.
     */
    public void activateSection() {
        if (listeners != null) {
            for (ActivateEventListener listener : listeners) {
                listener.onActivated(this);
            }
        }
    }

    /**
     * Sets the selected state of the section
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Sets info widget
     */
    public void setInfoWidget(UIWidget infoWidget) {
        widget = infoWidget;
    }

    /**
     * Set icon texture
     */
    public void setIcon(UITextureRegion newIcon) {
        icon = newIcon;
    }

    /**
     * Set section text
     */
    public void setText(String newText) {
        text = newText;
    }

    /**
     * Sets the region in which to draw the info widget
     */
    public void setInfoRegion(Rectanglei newRegion) {
        infoRegion = newRegion;
    }

    /**
     * Sets the draw region of the widget itself
     */
    public void setDrawRegion(Rectanglei region) {
        sectionRegion = region;
    }

    /**
     * Sets the draw region of the items inside the widget.
     */
    public void setInnerRegion(Rectanglei region) {
        innerRegion = region;
    }
}
