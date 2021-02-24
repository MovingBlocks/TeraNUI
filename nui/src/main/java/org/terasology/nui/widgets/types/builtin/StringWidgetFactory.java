// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.UIWidget;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.widgets.UIText;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

public class StringWidgetFactory implements TypeWidgetFactory {
    // TODO: Possibly use I18n prompter

    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!String.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<String> builder = StringWidgetFactory::createStringWidget;

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static UIWidget createStringWidget(Binding<String> binding) {
        if (binding.get() == null) {
            binding.set("");
        }

        UIText widget = new UIText();
        widget.setMultiline(true);

        widget.bindText(binding);

        return widget;
    }
}
