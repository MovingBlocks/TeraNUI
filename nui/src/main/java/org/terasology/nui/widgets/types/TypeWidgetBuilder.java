// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets.types;

import org.terasology.nui.UIWidget;
import org.terasology.nui.databinding.Binding;

public interface TypeWidgetBuilder<T> {
    UIWidget build(Binding<T> binding);
}
