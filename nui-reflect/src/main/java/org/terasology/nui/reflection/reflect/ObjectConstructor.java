// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.reflect;

/**
 * Providers the ability to construct an instance of a type.
 * <br><br>
 * These types must provide a default constructor, which will be invoked.
 *
 * @param <T> The type of the class to construct instances of
 */
@FunctionalInterface
public interface ObjectConstructor<T> {

    /**
     * @return A new instance of the object type
     */
    T construct();
}
