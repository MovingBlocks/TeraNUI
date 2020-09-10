// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.nui.reflection.copy.CopyStrategyLibrary;
import org.terasology.nui.reflection.reflect.ReflectFactory;

/**
 * A simple implementation of ClassLibrary. It provides ClassMetadata for a type of class. These classes are identified through their simple name.
 *
 * @param <T> The base type of classes that can be registered in this library
 */
public final class DefaultClassLibrary<T> extends AbstractClassLibrary<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultClassLibrary.class);

    public DefaultClassLibrary(ModuleEnvironment environment, ReflectFactory reflectFactory, CopyStrategyLibrary copyStrategyLibrary) {
        super(environment, reflectFactory, copyStrategyLibrary);
    }

    @Override
    protected <C extends T> ClassMetadata<C, ?> createMetadata(Class<C> type, ReflectFactory factory, CopyStrategyLibrary copyStrategies, ResourceUrn uri) {
        try {
            return new DefaultClassMetadata<>(uri, type, factory, copyStrategies);
        } catch (NoSuchMethodException e) {
            logger.error("Unable to register class {}: Default Constructor Required", type.getSimpleName(), e);
            return null;

        }
    }
}
