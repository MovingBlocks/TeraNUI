// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.terasology.nui.reflection.copy.CopyStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 */
public class SetCopyStrategy<T> implements CopyStrategy<Set<T>> {
    private final CopyStrategy<T> contentStrategy;

    public SetCopyStrategy(CopyStrategy<T> contentStrategy) {
        this.contentStrategy = contentStrategy;
    }

    @Override
    public Set<T> copy(Set<T> value) {
        if (value != null) {
            return value.stream().map(contentStrategy::copy).collect(Collectors.toCollection(HashSet::new));
        }
        return null;
    }
}
