// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.joml.Quaternionf;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class QuaternionfWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Quaternionf.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        // TODO: Possibly use euler angles or another easier-to-use format
        TypeWidgetBuilder<Quaternionf> builder = new QuaternionfWidgetBuilder(library)
                                                           .addAllFields();

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class QuaternionfWidgetBuilder extends LabeledNumberFieldRowBuilder<Quaternionf, Float> {
        public QuaternionfWidgetBuilder(TypeWidgetLibrary library) {
            super(Quaternionf.class, float.class, library);
        }

        @Override
        protected Quaternionf getDefaultValue() {
            return new Quaternionf(0, 0, 0, 1);
        }
    }
}
