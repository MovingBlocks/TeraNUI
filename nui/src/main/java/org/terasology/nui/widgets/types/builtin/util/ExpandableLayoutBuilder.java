// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin.util;

import org.terasology.nui.UIWidget;
import org.terasology.nui.WidgetUtil;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.databinding.NotifyingBinding;
import org.terasology.nui.layouts.ColumnLayout;
import org.terasology.nui.widgets.UIBox;
import org.terasology.nui.widgets.UILabel;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;

import static org.terasology.nui.widgets.types.TypeWidgetFactory.LABEL_WIDGET_ID;

public abstract class ExpandableLayoutBuilder<T> implements TypeWidgetBuilder<T> {
    @Override
    public UIWidget build(Binding<T> binding) {
        ColumnLayout innerExpandableLayout = createDefaultLayout();
        ColumnLayout mainLayout = createDefaultLayout();

        Binding<T> wrappedBinding = new NotifyingBinding<T>(binding) {
            @Override
            protected void onSet() {
                if (!innerExpandableLayout.iterator().hasNext()) {
                    // If it is empty (collapsed), we don't need to rebuild the inner layout
                    return;
                }

                clearAndPopulate(this, innerExpandableLayout, mainLayout);
            }
        };

        WidgetUtil.createExpandableLayout(
            new UILabel(LABEL_WIDGET_ID, ""),
            () -> innerExpandableLayout,
            layout -> clearAndPopulate(wrappedBinding, layout, mainLayout),
            () -> mainLayout
        );

        postInitialize(binding, mainLayout);

        return mainLayout;
    }

    protected void postInitialize(Binding<T> binding, ColumnLayout mainLayout) {}

    protected ColumnLayout createDefaultLayout() {
        ColumnLayout layout = new ColumnLayout();

        layout.setFillVerticalSpace(false);
        layout.setAutoSizeColumns(false);
        layout.setVerticalSpacing(5);

        return layout;
    }

    protected UIBox buildErrorWidget(String errorMessage) {
        UIBox box = new UIBox();

        // TODO: Translate
        box.setContent(new UILabel(errorMessage + ", cannot instantiate object from UI"));
        return box;
    }

    private void clearAndPopulate(Binding<T> binding, ColumnLayout layout, ColumnLayout mainLayout) {
        layout.removeAllWidgets();

        populate(binding, layout, mainLayout);
    }

    protected abstract void populate(Binding<T> binding, ColumnLayout layout, ColumnLayout mainLayout);
}
