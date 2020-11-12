// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.joml.Vector3i;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class Vector3iWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Vector3i.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Vector3i> builder = new Vector3iWidgetBuilder(library)
                                                  .addAllFields();

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class Vector3iWidgetBuilder extends LabeledNumberFieldRowBuilder<Vector3i, Integer> {
        public Vector3iWidgetBuilder(TypeWidgetLibrary library) {
            super(Vector3i.class, int.class, library);
        }

        @Override
        protected Vector3i getDefaultValue() {
            return new Vector3i();
        }
    }
}
