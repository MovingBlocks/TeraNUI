/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.assets.ResourceUrn;
import org.terasology.module.ModuleEnvironment;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.metadata.ClassMetadata;
import org.terasology.reflection.metadata.ModuleClassLibrary;
import org.terasology.reflection.reflect.ReflectFactory;
import org.terasology.nui.UIWidget;

/**
 */
public class WidgetLibrary extends ModuleClassLibrary<UIWidget> {

    private static final Logger logger = LoggerFactory.getLogger(WidgetLibrary.class);

    public WidgetLibrary(ModuleEnvironment environment, ReflectFactory reflectFactory, CopyStrategyLibrary copyStrategyLibrary) {
        super(environment, reflectFactory, copyStrategyLibrary);
    }

    @Override
    protected <C extends UIWidget> ClassMetadata<C, ?> createMetadata(Class<C> type, ReflectFactory factory, CopyStrategyLibrary copyStrategies, ResourceUrn name) {
        try {
            return new WidgetMetadata<>(name.toString(), type, factory, copyStrategies);
        } catch (NoSuchMethodException e) {
            logger.error("Unable to register class {}: Default Constructor Required", type.getSimpleName(), e);
            return null;

        }
    }
}
