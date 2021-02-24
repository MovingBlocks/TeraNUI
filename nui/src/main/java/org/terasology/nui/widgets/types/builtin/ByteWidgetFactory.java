// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types.builtin;

import org.terasology.nui.databinding.Binding;

public class ByteWidgetFactory extends NumberWidgetFactory<Byte> {
    public ByteWidgetFactory() {
        super(Byte.class, Byte.TYPE);
    }

    @Override
    protected void setToDefaultValue(Binding<Byte> binding) {
        binding.set((byte) 0);
    }

    @Override
    protected Byte parse(String value) throws NumberFormatException {
        return Byte.parseByte(value);
    }
}
