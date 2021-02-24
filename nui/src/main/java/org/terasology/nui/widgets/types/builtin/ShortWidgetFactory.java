// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.databinding.Binding;

public class ShortWidgetFactory extends NumberWidgetFactory<Short> {

    public ShortWidgetFactory() {
        super(Short.class, Short.TYPE);
    }

    @Override
    protected void setToDefaultValue(Binding<Short> binding) {
        binding.set((short) 0);
    }

    @Override
    protected Short parse(String value) throws NumberFormatException {
        return Short.parseShort(value);
    }
}
