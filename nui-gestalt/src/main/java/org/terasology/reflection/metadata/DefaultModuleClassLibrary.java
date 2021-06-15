// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.reflection.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.reflect.ReflectFactory;

public class DefaultModuleClassLibrary<T> extends ModuleClassLibrary<T> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultModuleClassLibrary.class);

    public DefaultModuleClassLibrary(ModuleEnvironment environment, ReflectFactory reflectFactory, CopyStrategyLibrary copyStrategyLibrary) {
        super(environment, reflectFactory, copyStrategyLibrary);
    }

    @Override
    protected <C extends T> ClassMetadata<C, ?> createMetadata(Class<C> type, ReflectFactory factory, CopyStrategyLibrary copyStrategies, ResourceUrn uri) {
        try {
            return new DefaultClassMetadata<>(uri.toString(), type, factory, copyStrategies);
        } catch (NoSuchMethodException e) {
            logger.error("Unable to register class {}: Default Constructor Required", type.getSimpleName(), e);
            return null;

        }
    }
}
