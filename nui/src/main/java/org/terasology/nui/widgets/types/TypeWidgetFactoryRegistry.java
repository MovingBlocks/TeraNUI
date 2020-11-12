// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.widgets.types;

import java.util.List;

public interface TypeWidgetFactoryRegistry {
    void add(TypeWidgetFactory factory);

    List<TypeWidgetFactory> getFactories();
}
