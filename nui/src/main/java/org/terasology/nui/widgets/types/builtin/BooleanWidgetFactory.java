// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.UIWidget;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.widgets.UICheckbox;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

public class BooleanWidgetFactory implements TypeWidgetFactory {
    @SuppressWarnings({"unchecked"})
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Boolean.class.equals(type.getRawType()) && !Boolean.TYPE.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Boolean> createBooleanWidget = BooleanWidgetFactory::createBooleanWidget;
        return Optional.of((TypeWidgetBuilder<T>) createBooleanWidget);
    }

    private static UIWidget createBooleanWidget(Binding<Boolean> binding) {
        if (binding.get() == null) {
            binding.set(false);
        }

        UICheckbox widget = new UICheckbox();

        widget.bindChecked(binding);

        return widget;
    }
}
