// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.databinding.Binding;

public class DoubleWidgetFactory extends NumberWidgetFactory<Double> {
    public DoubleWidgetFactory() {
        super(Double.class, Double.TYPE);
    }

    @Override
    protected void setToDefaultValue(Binding<Double> binding) {
        binding.set(0.0);
    }

    @Override
    protected Double parse(String value) throws NumberFormatException {
        return Double.parseDouble(value);
    }
}
