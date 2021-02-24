// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.widgets.types;

import java.util.List;

/**
 * This registry is basically a (shared) list of {@link TypeWidgetFactory}s.
 *
 * In contrast to the {@link TypeWidgetLibrary} the registry does not have any logic for creating widgets.
 */
public interface TypeWidgetFactoryRegistry {
    void add(TypeWidgetFactory factory);

    List<TypeWidgetFactory> getFactories();
}
