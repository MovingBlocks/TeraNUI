// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.joml.Rectanglei;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.util.RectUtility;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class RectangleiWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Rectanglei.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Rectanglei> builder =
            new RectangleiWidgetBuilder(library)
                .add("x",
                    rectBinding -> new Binding<Integer>() {
                        @Override
                        public Integer get() {
                            return rectBinding.get().minX;
                        }

                        @Override
                        public void set(Integer value) {
                            Rectanglei old = rectBinding.get();
                            rectBinding.set(RectUtility.createFromMinAndSize(value, old.minY, old.maxX - old.minX, old.maxY - old.minY));
                        }
                    })
                .add("y",
                    rectBinding -> new Binding<Integer>() {
                        @Override
                        public Integer get() {
                            return rectBinding.get().minY;
                        }

                        @Override
                        public void set(Integer value) {
                            Rectanglei old = rectBinding.get();
                            rectBinding.set(RectUtility.createFromMinAndSize(old.minX, value, old.maxX - old.minX, old.maxY - old.minY));
                        }
                    })
                .add("w",
                    rectBinding -> new Binding<Integer>() {
                        @Override
                        public Integer get() {
                            Rectanglei rect = rectBinding.get();
                            return rect.maxX - rect.minX;
                        }

                        @Override
                        public void set(Integer value) {
                            Rectanglei old = rectBinding.get();
                            rectBinding.set(RectUtility.createFromMinAndSize(old.minX, old.minY, old.minX + value, old.maxY - old.minY));
                        }
                    })
                .add("h",
                    rectBinding -> new Binding<Integer>() {
                        @Override
                        public Integer get() {
                            Rectanglei rect = rectBinding.get();
                            return rect.maxY - rect.minY;
                        }

                        @Override
                        public void set(Integer value) {
                            Rectanglei old = rectBinding.get();
                            rectBinding.set(RectUtility.createFromMinAndSize(old.minX, old.minY, old.maxX - old.minX,  old.minY + value));
                        }
                    });

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class RectangleiWidgetBuilder extends LabeledNumberFieldRowBuilder<Rectanglei, Integer> {
        public RectangleiWidgetBuilder(TypeWidgetLibrary library) {
            super(Rectanglei.class, int.class, library);
        }

        @Override
        protected Rectanglei getDefaultValue() {
            // Make non-empty so that editing works as intended
            // When the initial rect is empty, editing any of the components will make no difference
            // since one of the size components will always be zero, making the factory methods always
            // return the empty rect
            return RectUtility.createFromMinAndSize(0, 0, 1, 1);
        }
    }
}
