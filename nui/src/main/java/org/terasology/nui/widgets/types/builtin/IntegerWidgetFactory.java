// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.databinding.Binding;

public class IntegerWidgetFactory extends NumberWidgetFactory<Integer> {
    public IntegerWidgetFactory() {
        super(Integer.class, Integer.TYPE);
    }

    @Override
    protected void setToDefaultValue(Binding<Integer> binding) {
        binding.set(0);
    }

    @Override
    protected Integer parse(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }
}
