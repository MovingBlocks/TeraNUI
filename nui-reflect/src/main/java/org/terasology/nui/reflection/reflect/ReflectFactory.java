// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.reflect;

import java.lang.reflect.Field;

/**
 * A factory providing reflection abilities, such as object construction and field access.
 *
 */
public interface ReflectFactory {

    <T> ObjectConstructor<T> createConstructor(Class<T> type) throws NoSuchMethodException;

    <T> FieldAccessor<T, ?> createFieldAccessor(Class<T> ownerType, Field field) throws InaccessibleFieldException;

    <T, U> FieldAccessor<T, U> createFieldAccessor(Class<T> ownerType, Field field, Class<U> fieldType) throws InaccessibleFieldException;
}
