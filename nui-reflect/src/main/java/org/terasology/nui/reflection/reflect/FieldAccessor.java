// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.reflect;

/**
 * Provider get and set access to a field. Where possible this will use getter and setter methods (following the Java Bean standards), but otherwise it will
 * access the field directly.
 *
 * @param <T> The type of the object that holds this field
 * @param <U> The type of the field itself
 */
public interface FieldAccessor<T, U> {

    /**
     * Gets the value from the target object
     *
     * @param target The object to access the field of
     * @return The value of the field for the given target
     */
    U getValue(T target);

    /**
     * Sets the value for the target object
     *
     * @param target The object to access the field of
     * @param value  The value to set the field to
     */
    void setValue(T target, U value);
}
