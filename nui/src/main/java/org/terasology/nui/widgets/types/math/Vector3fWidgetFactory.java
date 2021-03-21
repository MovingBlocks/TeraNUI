// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.joml.Vector3f;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class Vector3fWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Vector3f.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Vector3f> builder = new Vector3fWidgetBuilder(library)
                                                  .addAllFields();

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class Vector3fWidgetBuilder extends LabeledNumberFieldRowBuilder<Vector3f, Float> {
        public Vector3fWidgetBuilder(TypeWidgetLibrary library) {
            super(Vector3f.class, float.class, library);
        }

        @Override
        protected Vector3f getDefaultValue() {
            return new Vector3f();
        }
    }
}
