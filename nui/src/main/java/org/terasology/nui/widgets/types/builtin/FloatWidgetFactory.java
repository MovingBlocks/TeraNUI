// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.databinding.Binding;

public class FloatWidgetFactory extends NumberWidgetFactory<Float> {
    public FloatWidgetFactory() {
        super(Float.class, Float.TYPE);
    }

    @Override
    protected void setToDefaultValue(Binding<Float> binding) {
        binding.set(0.0f);
    }

    @Override
    protected Float parse(String value) throws NumberFormatException {
        return Float.parseFloat(value);
    }
}
