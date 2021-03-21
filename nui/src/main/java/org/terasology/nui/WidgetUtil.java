// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui;

import org.joml.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.layouts.ColumnLayout;
import org.terasology.nui.layouts.RowLayout;
import org.terasology.nui.layouts.RowLayoutHint;
import org.terasology.nui.widgets.ActivateEventListener;
import org.terasology.nui.widgets.UIButton;
import org.terasology.nui.widgets.UICheckbox;
import org.terasology.nui.widgets.UILabel;
import org.terasology.nui.widgets.UISpace;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 */
public final class WidgetUtil {
    public static final int INDENT_SIZE = 50;

    private static final Logger logger = LoggerFactory.getLogger(WidgetUtil.class);

    private WidgetUtil() {
    }

    public static void trySubscribe(UIWidget widget, String id, ActivateEventListener listener) {
        UIButton button = widget.find(id, UIButton.class);
        if (button != null) {
            button.subscribe(listener);
        } else {
            logger.warn("Contents of {} missing button with id '{}'", widget, id);
        }
    }

    public static void tryBindCheckbox(UIWidget widget, String id, Binding<Boolean> binding) {
        UICheckbox checkbox = widget.find(id, UICheckbox.class);
        if (checkbox != null) {
            checkbox.bindChecked(binding);
        }
    }

    /**
     * Bind a check box and boolean, and a listener will be subscribed to the checkbox.
     *
     * @param widget the widget.
     * @param id the id of the checkbox.
     * @param binding the boolean bound with the checkbox.
     * @param listener the listener which will activated when the check box is pressed.
     */
    public static void tryBindCheckBoxWithListener(UIWidget widget, String id, Binding<Boolean> binding,
                                                   ActivateEventListener listener) {
        UICheckbox checkbox = widget.find(id, UICheckbox.class);
        if (checkbox != null) {
            checkbox.bindChecked(binding);
            checkbox.subscribe(listener);
        }
    }

    /**
     * Wraps widget with indentation.
     * <p>
     * Uses {@link WidgetUtil#INDENT_SIZE} as identitaion size.
     *     
     * @param widget widget to wraping
     * @return wrapped widget.
     */
    public static UIWidget indent(UIWidget widget) {
        return indent(widget, INDENT_SIZE);
    }
    
    /**
     * Wraps widget with indentation.
     * 
     * @param widget widget to wraping
     * @param indentSize indent size
     * @return wrapped widget.
     */
    public static UIWidget indent(UIWidget widget, int indentSize) {
        RowLayout layout = new RowLayout();

        layout.addWidget(new UISpace(new Vector2i(indentSize, 0)), new RowLayoutHint().setUseContentWidth(true));
        layout.addWidget(widget, new RowLayoutHint());

        return layout;
    }

    public static <L extends UILayout<?>> ColumnLayout createExpandableLayout(
            String label,
            Supplier<L> layoutSupplier,
            Consumer<L> layoutExpander
    ) {
        return createExpandableLayout(new UILabel(label), layoutSupplier, layoutExpander, ColumnLayout::new);
    }

    public static <L extends UILayout<?>> ColumnLayout createExpandableLayout(
            String label, Supplier<L> layoutSupplier,
            Consumer<L> layoutExpander,
            Supplier<ColumnLayout> columnLayoutSupplier
    ) {
        return createExpandableLayout(new UILabel(label), layoutSupplier, layoutExpander, columnLayoutSupplier);
    }

    public static <L extends UILayout<?>> ColumnLayout createExpandableLayout(
            UILabel labelWidget,
            Supplier<L> layoutSupplier,
            Consumer<L> layoutExpander,
            Supplier<ColumnLayout> columnLayoutSupplier
    ) {
        L layoutToExpand = layoutSupplier.get();

        RowLayout expanderLayout = createExpanderWidget(labelWidget, layoutToExpand, layoutExpander);

        ColumnLayout columnLayout = columnLayoutSupplier.get();

        columnLayout.addWidget(expanderLayout);
        columnLayout.addWidget(indent(layoutToExpand));

        return columnLayout;
    }

    public static <L extends UILayout<?>> RowLayout createExpanderWidget(
            String label,
            L layoutToExpand,
            Consumer<L> layoutExpander
    ) {
        return createExpanderWidget(new UILabel(label), layoutToExpand, layoutExpander);
    }

    public static <L extends UILayout<?>> RowLayout createExpanderWidget(
            UILabel labelWidget,
            L layoutToExpand,
            Consumer<L> layoutExpander
    ) {
        RowLayout rowLayout = new RowLayout();

        UIButton expandButton = createExpanderButton(layoutToExpand, layoutExpander);

        rowLayout.addWidget(expandButton, new RowLayoutHint().setUseContentWidth(false));
        rowLayout.addWidget(labelWidget, new RowLayoutHint());
        return rowLayout;
    }

    public static <L extends UILayout<?>> UIButton createExpanderButton(L layoutToExpand,
                                                                        Consumer<L> layoutExpander) {
        UIButton expandButton = new UIButton();

        expandButton.setText("+");
        expandButton.subscribe(widget -> {
            UIButton button = (UIButton) widget;
            if ("-".equals(button.getText())) {
                layoutToExpand.removeAllWidgets();

                button.setText("+");
                // TODO: Translate
                button.setTooltip("Expand");
            } else {
                layoutExpander.accept(layoutToExpand);

                button.setText("-");
                // TODO: Translate
                button.setTooltip("Collapse");
            }
        });

        return expandButton;
    }

    public static UIWidget labelize(UIWidget widget, String labelText, String labelWidgetId) {
        Optional<UILabel> labelWidget = widget.tryFind(labelWidgetId, UILabel.class);

        if (labelWidget.isPresent()) {
            labelWidget.get().setText(labelText);

            return widget;
        }

        RowLayout fieldLayout = new RowLayout();
        fieldLayout.setHorizontalSpacing(5);

        fieldLayout.addWidget(new UILabel(labelWidgetId, labelText), new RowLayoutHint().setUseContentWidth(true));
        fieldLayout.addWidget(widget, new RowLayoutHint());

        return fieldLayout;
    }
}
