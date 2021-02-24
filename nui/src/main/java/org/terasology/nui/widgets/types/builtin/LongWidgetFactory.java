// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.databinding.Binding;

public class LongWidgetFactory extends NumberWidgetFactory<Long> {
    public LongWidgetFactory() {
        super(Long.class, Long.TYPE);
    }

    @Override
    protected void setToDefaultValue(Binding<Long> binding) {
        binding.set(0L);
    }

    @Override
    protected Long parse(String value) throws NumberFormatException {
        return Long.parseLong(value);
    }
}
