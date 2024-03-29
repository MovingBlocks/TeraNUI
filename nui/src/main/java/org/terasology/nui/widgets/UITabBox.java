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

package org.terasology.nui.widgets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.joml.geom.Rectanglei;
import org.joml.Vector2i;
import org.terasology.nui.Canvas;
import org.terasology.nui.CoreWidget;
import org.terasology.nui.LayoutConfig;
import org.terasology.nui.UIWidget;
import org.terasology.nui.layouts.RowLayout;
import org.terasology.nui.util.RectUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class UITabBox extends CoreWidget {
    private static final Logger logger = LoggerFactory.getLogger(UITabBox.class);

    @LayoutConfig
    private List<UIWidget> contents = new ArrayList<>();

    @LayoutConfig
    private List<UIButton> buttons = new ArrayList<>();

    @LayoutConfig
    private int tabBarHeight = 30;

    @LayoutConfig
    private int currentTab = 1;

    private List<ActivateEventListener> listeners = new ArrayList<>();
    private RowLayout buttonLayout = new RowLayout();
    private boolean isCreated;

    @Override
    public void onDraw(Canvas canvas) {
        if (!contents.isEmpty()) {
            Rectanglei region = canvas.getRegion();
            Rectanglei buttonRegion = RectUtility.createFromMinAndSize(region.minX, region.minY, region.lengthX(), tabBarHeight);
            Rectanglei boxRegion = new Rectanglei(region.minX, region.minY + tabBarHeight, region.lengthX(), region.lengthY());
            canvas.drawWidget(contents.get(currentTab), boxRegion);
            canvas.drawWidget(buttonLayout, buttonRegion);
        }
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        if (!contents.isEmpty()) {
            return canvas.calculateRestrictedSize(contents.get(currentTab), sizeHint);
        }
        return new Vector2i();
    }

    @Override
    public void update(float delta) {
        if (!isCreated) {
            isCreated = true;
            for (int i = 0; i < buttons.size(); i++) {
                UIButton button = buttons.get(i);
                listeners.add(widget -> currentTab = buttons.indexOf(widget));
                button.subscribe(listeners.get(listeners.size() - 1));
                buttonLayout.addWidget(button, null);
                buttons.set(i, button);
            }
        }
        buttons.forEach(b -> b.setActive(currentTab == buttons.indexOf(b)));
        super.update(delta);
    }

    @Override
    public Iterator<UIWidget> iterator() {
        if (!contents.isEmpty()) {
            return contents.iterator();
        }
        return Collections.emptyIterator();
    }

    public int addTab(UIWidget content, UIButton button) {
        listeners.add(widget -> currentTab = buttons.indexOf(widget));
        button.subscribe(listeners.get(listeners.size() - 1));
        buttonLayout.addWidget(button, null);
        contents.add(content);
        buttons.add(button);

        return contents.size() - 1;
    }

    public void removeTab(int tabNumber) {
        buttons.get(tabNumber).unsubscribe(listeners.get(tabNumber));
        buttonLayout.removeWidget(buttons.get(tabNumber));
        contents.remove(tabNumber);
        listeners.remove(tabNumber);
        buttons.remove(tabNumber);
    }

    /**
     * Selects a tab from the contents list via index.
     *
     * @param index The index of the item to select.
     */
    public void select(int index) {
        if (index >= 0 && index < contents.size()) {
            currentTab = index;
        }
    }

}
