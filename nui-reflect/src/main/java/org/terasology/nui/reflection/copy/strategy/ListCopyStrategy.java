// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.terasology.nui.reflection.copy.CopyStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 */
public class ListCopyStrategy<T> implements CopyStrategy<List<T>> {

    private final CopyStrategy<T> contentStrategy;

    public ListCopyStrategy(CopyStrategy<T> contentStrategy) {
        this.contentStrategy = contentStrategy;
    }

    @Override
    public List<T> copy(List<T> value) {
        if (value != null) {
            return value.stream().map(contentStrategy::copy).collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }
}
