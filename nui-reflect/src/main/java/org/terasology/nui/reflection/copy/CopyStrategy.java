// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy;

/**
 * A strategy for copying an object/type.
 * This may be returning the object unchanged for immutable or otherwise safe to share types.
 * Copy strategies are deep-copies - contents should also be copied where appropriate.
 */
@FunctionalInterface
public interface CopyStrategy<T> {

    /**
     * @param value The value to copy
     * @return A safe to use copy of the given value.
     */
    T copy(T value);

}
