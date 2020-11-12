// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.databinding;

/**
 */
public interface Binding<T> {

    T get();

    void set(T value);

    /**
     * Makes {@code binding} a child of this {@link Binding}. Child bindings are bound
     * to the same value as the original binding ({@code binding}), but also handle
     * propagation of changes in their value to parent bindings if needed.
     *
     * @param binding The {@link Binding} that must be made a child.
     * @param <C> The type of value bound by {@code binding}.
     * @return The child {@link Binding} that is bound to the same value as {@code binding}.
     */
    default <C> Binding<C> makeChildBinding(Binding<C> binding) {
        return binding;
    }
}
