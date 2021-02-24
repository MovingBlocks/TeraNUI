// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.UIWidget;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.widgets.UITextEntry;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

public abstract class NumberWidgetFactory<N extends Number> implements TypeWidgetFactory {
    private Class<N> wrapperClass;
    private Class<N> primitiveClass;

    protected NumberWidgetFactory(Class<N> wrapperClass, Class<N> primitiveClass) {
        this.wrapperClass = wrapperClass;
        this.primitiveClass = primitiveClass;
    }

    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!wrapperClass.equals(type.getRawType()) && !primitiveClass.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<N> createTextEntry = this::createTextEntry;
        return Optional.of((TypeWidgetBuilder<T>) createTextEntry);
    }

    private UIWidget createTextEntry(Binding<N> numberBinding) {
        if (numberBinding.get() == null) {
            // TODO: Replace with call to guava Defaults
            setToDefaultValue(numberBinding);
        }

        UITextEntry<N> widget = new UITextEntry<>();

        widget.bindValue(numberBinding);

        widget.setParser(value -> {
            try {
                return parse(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error while parsing value ", e);
            }
        });

        return widget;
    }

    protected abstract void setToDefaultValue(Binding<N> binding);
    protected abstract N parse(String value) throws NumberFormatException;
}
