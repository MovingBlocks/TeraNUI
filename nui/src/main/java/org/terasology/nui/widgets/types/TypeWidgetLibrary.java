// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types;

import org.terasology.nui.UIWidget;
import org.terasology.nui.databinding.Binding;
import org.terasology.reflection.TypeInfo;

import java.util.Optional;

/**
 * Creates {@link UIWidget UIWidgets} to edit objects of various types.
 * <p>
 * Instances can only be accessed via injection (see {@link org.terasology.registry.In}) in
 * screens and overlays.
 */
public interface TypeWidgetLibrary {
    /**
     * Returns a {@link UIWidget} that can be used to edit an object of the given type bound
     * by the given binding.
     *
     * @param binding The {@link Binding} used to get and set the object to be edited.
     * @param type    The {@link TypeInfo} describing the type of the object to be edited.
     * @param <T>     The type of the object to be edited.
     * @return The generated {@link UIWidget}.
     */
    <T> Optional<UIWidget> getWidget(Binding<T> binding, TypeInfo<T> type);

    /**
     * Returns a {@link UIWidget} that can be used to edit an object of the given type bound
     * by the given binding.
     *
     * @see #getWidget(Binding, TypeInfo)
     */
    <T> Optional<UIWidget> getWidget(Binding<T> binding, Class<T> type);

    <T> Optional<UIWidget> getBaseTypeWidget(Binding<T> binding, TypeInfo<T> baseType);

    <T> Optional<TypeWidgetBuilder<T>> getBuilder(TypeInfo<T> type);
}
