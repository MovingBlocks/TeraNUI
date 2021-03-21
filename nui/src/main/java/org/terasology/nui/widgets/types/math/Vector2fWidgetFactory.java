// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.joml.Vector2f;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class Vector2fWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Vector2f.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Vector2f> builder = new Vector2fWidgetBuilder(library)
            .addAllFields();

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class Vector2fWidgetBuilder extends LabeledNumberFieldRowBuilder<Vector2f, Float> {
        public Vector2fWidgetBuilder(TypeWidgetLibrary library) {
            super(Vector2f.class, float.class, library);
        }

        @Override
        protected Vector2f getDefaultValue() {
            return new Vector2f();
        }
    }
}
