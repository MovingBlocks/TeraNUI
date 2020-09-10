// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.metadata;

import com.google.common.base.Predicates;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.nui.reflection.copy.CopyStrategy;
import org.terasology.nui.reflection.copy.CopyStrategyLibrary;
import org.terasology.nui.reflection.reflect.InaccessibleFieldException;
import org.terasology.nui.reflection.reflect.ReflectFactory;

import java.lang.reflect.Field;

/**
 * A standard class metadata implementation using FieldMetadata.
 *
 */
public class DefaultClassMetadata<T> extends ClassMetadata<T, FieldMetadata<T, ?>> {

    /**
     * Creates a class metatdata
     *
     * @param uri                 The uri that identifies this class
     * @param type                The type to create the metadata for
     * @param factory             A reflection library to provide class construction and field get/set functionality
     * @param copyStrategyLibrary A copy strategy library
     * @throws NoSuchMethodException If the class has no default constructor
     */
    public DefaultClassMetadata(ResourceUrn uri, Class<T> type, ReflectFactory factory, CopyStrategyLibrary copyStrategyLibrary) throws NoSuchMethodException {
        super(uri, type, factory, copyStrategyLibrary, Predicates.alwaysTrue());
    }

    @Override
    protected <V> FieldMetadata<T, V> createField(Field field, CopyStrategy<V> copyStrategy, ReflectFactory factory) throws InaccessibleFieldException {
        return new FieldMetadata<>(this, field, copyStrategy, factory);
    }
}
