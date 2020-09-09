// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui;

import org.terasology.nui.events.NUIKeyEvent;
import org.terasology.nui.input.Keyboard;
import org.terasology.nui.layouts.ScrollableArea;

/**
 * Parent for widgets that can be tabbed to.
 */
public abstract class WidgetWithOrder extends CoreWidget {

    @LayoutConfig
    protected int order = TabbingManager.UNINITIALIZED_DEPTH;
    protected boolean initialized = false;

    private boolean added = false;

    protected ScrollableArea parent;

    public WidgetWithOrder() {
        this.setId("");
    }

    public WidgetWithOrder(String id) {
        this.setId(id);
    }

    public ScrollableArea getParent() {
        return parent;
    }

    public void setParent(ScrollableArea area) {
        parent = area;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
    @Override
    public String getMode() {
        if (isFocused()) {
            return ACTIVE_MODE;
        }
        return DEFAULT_MODE;
    }
    public int getOrder() {
        if (order == TabbingManager.UNINITIALIZED_DEPTH) {
            order = TabbingManager.getNewNextNum();
            TabbingManager.addToWidgetsList(this);
            TabbingManager.addToUsedNums(order);
            added = true;
        } else if (!added) {
            TabbingManager.addToWidgetsList(this);
            TabbingManager.addToUsedNums(order);
            added = true;
        }
        return order;
    }

    @Override
    public boolean onKeyEvent(NUIKeyEvent event) {
        if (event.isDown()) {
            int keyId = event.getKey().getId();
            if (keyId == Keyboard.KeyId.UP) {
                if (parent != null && !TabbingManager.isWidgetOpen()) {
                    parent.scroll(true);
                }
                return true;
            } else if (keyId == Keyboard.KeyId.DOWN) {
                if (parent != null && !TabbingManager.isWidgetOpen()) {
                    parent.scroll(false);
                }
                return true;
            }
        }
        return super.onKeyEvent(event);
    }
}
