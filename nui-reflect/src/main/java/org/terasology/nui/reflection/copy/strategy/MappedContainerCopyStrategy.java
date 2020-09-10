// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.terasology.nui.reflection.metadata.ClassMetadata;
import org.terasology.nui.reflection.copy.CopyStrategy;

/**
 */
public class MappedContainerCopyStrategy<T> implements CopyStrategy<T> {

    private final ClassMetadata<T, ?> classMetadata;

    public MappedContainerCopyStrategy(ClassMetadata<T, ?> classMetadata) {
        this.classMetadata = classMetadata;
    }

    @Override
    public T copy(T value) {
        if (value != null) {
            return classMetadata.copy(value);
        }
        return null;
    }
}
