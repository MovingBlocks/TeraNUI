// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.math;

import org.terasology.joml.geom.Rectanglef;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.widgets.types.RegisterTypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetBuilder;
import org.terasology.nui.widgets.types.TypeWidgetFactory;
import org.terasology.nui.widgets.types.TypeWidgetLibrary;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

@RegisterTypeWidgetFactory
public class RectanglefWidgetFactory implements TypeWidgetFactory {
    @Override
    public <T> Optional<TypeWidgetBuilder<T>> create(TypeInfo<T> type, TypeWidgetLibrary library) {
        if (!Rectanglef.class.equals(type.getRawType())) {
            return Optional.empty();
        }

        TypeWidgetBuilder<Rectanglef> builder =
                new RectanglefWidgetBuilder(library)
                        .add("x",
                                rectBinding -> new Binding<Float>() {
                                    @Override
                                    public Float get() {
                                        return rectBinding.get().minX;
                                    }

                                    @Override
                                    public void set(Float value) {
                                        Rectanglef old = rectBinding.get();
                                        old.minX = value;
                                        rectBinding.set(old);
                                    }
                                })
                        .add("y",
                                rectBinding -> new Binding<Float>() {
                                    @Override
                                    public Float get() {
                                        return rectBinding.get().minY;
                                    }

                                    @Override
                                    public void set(Float value) {
                                        Rectanglef old = rectBinding.get();
                                        old.minY = value;
                                        rectBinding.set(old);
                                    }
                                })
                        .add("w",
                                rectBinding -> new Binding<Float>() {
                                    @Override
                                    public Float get() {
                                        Rectanglef rect = rectBinding.get();
                                        return rect.maxX - rect.minX;
                                    }

                                    @Override
                                    public void set(Float value) {
                                        Rectanglef old = rectBinding.get();
                                        old.maxX = old.minX + value;
                                        rectBinding.set(old);
                                    }
                                })
                        .add("h",
                                rectBinding -> new Binding<Float>() {
                                    @Override
                                    public Float get() {
                                        Rectanglef rect = rectBinding.get();
                                        return rect.maxY - rect.minY;
                                    }

                                    @Override
                                    public void set(Float value) {
                                        Rectanglef old = rectBinding.get();
                                        old.maxY = old.minY + value;
                                        rectBinding.set(old);
                                    }
                                });

        return Optional.of((TypeWidgetBuilder<T>) builder);
    }

    private static class RectanglefWidgetBuilder extends LabeledNumberFieldRowBuilder<Rectanglef, Float> {
        public RectanglefWidgetBuilder(TypeWidgetLibrary library) {
            super(Rectanglef.class, float.class, library);
        }

        @Override
        protected Rectanglef getDefaultValue() {
            // Make non-empty so that editing works as intended
            // When the initial rect is empty, editing any of the components will make no difference
            // since one of the size components will always be zero, making the factory methods always
            // return the empty rect
            return new Rectanglef(0, 0);
        }
    }
}
