// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.joml.Vector2i;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class Vector2iWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Vector2i.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Vector2i> builder = new Vector2iWidgetBuilder(library)
                                                  .addAllFields();

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class Vector2iWidgetBuilder extends LabeledNumberFieldRowBuilder<Vector2i, Integer> {
        public Vector2iWidgetBuilder(TypeWidgetLibrary library) {
            super(Vector2i.class, int.class, library);
        }

        @Override
        protected Vector2i getDefaultValue() {
            return new Vector2i();
        }
    }
}
