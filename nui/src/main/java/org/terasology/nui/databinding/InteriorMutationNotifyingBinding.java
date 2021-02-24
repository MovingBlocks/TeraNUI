// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.databinding;


/**
 * A {@link Binding} that calls the {@link #callback} when the objects inside it is updated.
 * It relies on {@link org.terasology.nui.widgets.types.TypeWidgetFactory TypeWidgetFactories}
 * using {@link Binding#makeChildBinding(Binding)} to create bindings for objects inside the object in
 * this binding.
 */
public class InteriorMutationNotifyingBinding<T> implements Binding<T> {
    private final Binding<T> binding;
    private final Callback callback;

    public InteriorMutationNotifyingBinding(Binding<T> binding, Callback callback) {
        this.binding = binding;
        this.callback = callback;
    }

    @Override
    public T get() {
        return binding.get();
    }

    @Override
    public void set(T value) {
        binding.set(value);
    }

    @Override
    public <C> Binding<C> makeChildBinding(Binding<C> binding) {
        return new InteriorMutationNotifyingBinding<>(
            new NotifyingBinding<C>(binding) {
                @Override
                protected void onSet() {
                    callback.onInteriorValueMutated();
                }
            },
            callback
        );
    }

    public interface Callback {
        void onInteriorValueMutated();
    }
}
