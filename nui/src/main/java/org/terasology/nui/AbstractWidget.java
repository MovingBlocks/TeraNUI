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

import com.google.common.collect.Lists;
import org.joml.Vector2i;
import org.terasology.nui.events.NUIBindButtonEvent;
import org.terasology.input.ButtonState;
import org.terasology.input.Input;
import org.terasology.input.InputType;
import org.terasology.input.MouseInput;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.databinding.DefaultBinding;
import org.terasology.nui.databinding.ReadOnlyBinding;
import org.terasology.nui.events.NUIKeyEvent;
import org.terasology.nui.events.NUIMouseButtonEvent;
import org.terasology.nui.events.NUIMouseWheelEvent;
import org.terasology.nui.skin.UISkin;
import org.terasology.nui.widgets.UIDropdown;
import org.terasology.nui.widgets.UILabel;
import org.terasology.nui.widgets.UIRadialSection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractWidget implements UIWidget {

    private static final int DEFAULT_DEPTH = -99999;

    @LayoutConfig
    private String id;

    @LayoutConfig
    private UISkin skin;

    @LayoutConfig
    private boolean overrideChildEnabledProp = true;

    @LayoutConfig
    private Binding<String> family = new DefaultBinding<>();

    @LayoutConfig
    private Binding<Boolean> visible = new DefaultBinding<>(true);

    @LayoutConfig
    private Binding<UIWidget> tooltip = new DefaultBinding<>();

    @LayoutConfig
    private float tooltipDelay = 0.5f;

    protected int depth = new DefaultBinding<Integer>(DEFAULT_DEPTH).get();

    private boolean focused;

    private static boolean shiftPressed;

    @LayoutConfig
    private Binding<Boolean> enabled = new DefaultBinding<>(true);

    public AbstractWidget() {
        id = "";
    }

    public AbstractWidget(String id) {
        this.id = id;
    }

    public boolean isOverrideChildEnabledProp() {
        return overrideChildEnabledProp;
    }

    public void setOverrideChildEnabledProp(boolean overrideChildEnabledProp) {
        this.overrideChildEnabledProp = overrideChildEnabledProp;
    }

    @Override
    public String getMode() {
        if (this.isEnabled()) {
            return DEFAULT_MODE;
        }
        return DISABLED_MODE;
    }

    @Override
    public final String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    @Override
    public final UISkin getSkin() {
        return skin;
    }

    @Override
    public final void setSkin(UISkin skin) {
        this.skin = skin;
    }

    @Override
    public final String getFamily() {
        return family.get();
    }

    @Override
    public final void setFamily(String family) {
        this.family.set(family);
    }

    @Override
    public void bindFamily(Binding<String> binding) {
        this.family = binding;
    }

    @Override
    public final <T extends UIWidget> T find(String targetId, Class<T> type) {
        if (this.id.equals(targetId)) {
            if (type.isInstance(this)) {
                return type.cast(this);
            }
            return null;
        }
        for (UIWidget contents : this) {
            if (contents != null) {
                T result = contents.find(targetId, type);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public <T extends UIWidget> Optional<T> tryFind(String id, Class<T> type) {
        return Optional.ofNullable(find(id, type));
    }

    @Override
    public final <T extends UIWidget> Collection<T> findAll(Class<T> type) {
        List<T> results = Lists.newArrayList();
        findAll(type, this, results);
        return results;
    }

    private <T extends UIWidget> void findAll(Class<T> type, UIWidget widget, List<T> results) {
        if (type.isInstance(widget)) {
            results.add(type.cast(widget));
        }
        for (UIWidget content : widget) {
            findAll(type, content, results);
        }
    }

    @Override
    public boolean isVisible() {
        return visible.get();
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);

        for (UIWidget child : this) {
            if (overrideChildEnabledProp) {
                if (child instanceof AbstractWidget) {
                    AbstractWidget widget = (AbstractWidget) child;
                    widget.setEnabled(this.isEnabled());
                }
            }
        }
    }

    public void bindEnabled(Binding<Boolean> binding) {
        enabled = binding;

        for (UIWidget child : this) {
            if (child instanceof AbstractWidget) {
                AbstractWidget widget = (AbstractWidget) child;
                widget.bindEnabled(binding);
            }
        }
    }

    public void bindVisible(Binding<Boolean> bind) {
        this.visible = bind;
    }

    public void clearVisibleBinding() {
        this.visible = new DefaultBinding<>(true);
    }

    @Override
    public void onGainFocus() {
        focused = true;
        this.onMouseButtonEvent(new NUIMouseButtonEvent(MouseInput.MOUSE_LEFT, ButtonState.UP, new Vector2i()));
    }

    @Override
    public void onLoseFocus() {
        focused = false;

        if (TabbingManager.focusedWidget != null && TabbingManager.focusedWidget.equals(this)) {
            TabbingManager.unfocusWidget();
        }
    }

    public final boolean isFocused() {
        return focused;
    }

    @Override
    public boolean isSkinAppliedByCanvas() {
        return true;
    }

    @Override
    public void update(float delta) {
        for (UIWidget item : this) {
            item.update(delta);
        }
    }

    @Override
    public boolean canBeFocus() {
        return true;
    }

    @Override
    public void bindTooltip(Binding<UIWidget> binding) {
        tooltip = binding;
    }

    @Override
    public UIWidget getTooltip() {
        return tooltip.get();
    }

    @Override
    public void setTooltip(String value) {
        if (value != null && !value.isEmpty()) {
            setTooltip(new UILabel(value));
        } else {
            tooltip = new DefaultBinding<>(null);
        }
    }

    @Override
    public void setTooltip(UIWidget val) {
        tooltip.set(val);
    }

    @Override
    public void bindTooltipString(Binding<String> bind) {
        bindTooltip(new TooltipLabelBinding(bind));
    }

    @Override
    public float getTooltipDelay() {
        return tooltipDelay;
    }

    public final void setTooltipDelay(float value) {
        this.tooltipDelay = value;
    }

    private static class TooltipLabelBinding extends ReadOnlyBinding<UIWidget> {

        private UILabel tooltipLabel = new UILabel();

        TooltipLabelBinding(Binding<String> stringBind) {
            tooltipLabel.bindText(stringBind);
        }

        @Override
        public UIWidget get() {
            if (tooltipLabel.getText().isEmpty()) {
                return null;
            }
            return tooltipLabel;
        }
    }

    @Override
    public void onMouseButtonEvent(NUIMouseButtonEvent event) {
        onTabbingInput(event.getButton(), event.getState());
    }

    @Override
    public void onMouseWheelEvent(NUIMouseWheelEvent event) {
        // TODO: Implement this
        onTabbingInput(InputType.MOUSE_WHEEL.getInput(event.getWheelTurns()), ButtonState.DOWN);
    }

    @Override
    public boolean onKeyEvent(NUIKeyEvent event) {
        return onTabbingInput(event.getKey(), event.getState());
    }

    @Override
    public void onBindEvent(NUIBindButtonEvent event) {
        // Placeholder for compatibility...
    }

    private boolean onTabbingInput(Input input, ButtonState state) {
        if (state.equals(ButtonState.DOWN)/* && !SortOrderSystem.containsConsole()*/) {
            if (input.equals(TabbingManager.tabBackInputModifier)) {
                shiftPressed = true;
            }

            if (input.equals(TabbingManager.tabForwardInput)) {
                if (!TabbingManager.isInitialized()) {
                    TabbingManager.init();
                }
                if (TabbingManager.getFocusManager().getFocus() == null) {
                    if (TabbingManager.getWidgetsList().size() > 0) {
                        TabbingManager.resetCurrentNum();
                        TabbingManager.focusedWidget = TabbingManager.getWidgetsList().get(0);
                    }
                }
                TabbingManager.focusSetThrough = true;
                TabbingManager.changeCurrentNum(!shiftPressed);

                for (WidgetWithOrder widget : TabbingManager.getWidgetsList()) {
                    if (widget.getOrder() == TabbingManager.getCurrentNum()) {
                        if (!widget.isEnabled()) {
                            TabbingManager.changeCurrentNum(true);
                        } else {
                            widget.onGainFocus();
                            TabbingManager.focusedWidget = widget;
                            TabbingManager.getFocusManager().setFocus(widget);
                        }
                    } else {
                        widget.onLoseFocus();

                        if (widget instanceof UIRadialSection) {
                            ((UIRadialSection) widget).setSelected(false);
                        }
                    }
                }

                return true;
            } else if (input.equals(TabbingManager.activateInput)) {
                if (TabbingManager.focusedWidget instanceof UIDropdown) {
                    UIDropdown dropdown = ((UIDropdown) TabbingManager.focusedWidget);
                    if (dropdown.isOpened()) {
                        dropdown.setOpenedReverse(true);
                    }
                } else if  (TabbingManager.focusedWidget instanceof ActivatableWidget) {
                    ((ActivatableWidget) TabbingManager.focusedWidget).activateWidget();
                }

                return true;
            }
        }

        if (state.equals(ButtonState.UP)/* && !SortOrderSystem.containsConsole()*/) {
            if (input.equals(TabbingManager.tabBackInputModifier)) {
                shiftPressed = false;
                return true;
            }
        }

        return false;
    }

    public static boolean getShiftPressed() {
        return shiftPressed;
    }
}
