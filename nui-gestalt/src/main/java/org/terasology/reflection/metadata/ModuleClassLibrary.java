/*
 * Copyright 2013 MovingBlocks
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

package org.terasology.reflection.metadata;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.gestalt.module.Module;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.gestalt.naming.Name;
import org.terasology.reflection.copy.CopyStrategyLibrary;
import org.terasology.reflection.reflect.ReflectFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract base implement of ClassLibrary.
 *
 */
public abstract class ModuleClassLibrary<T> implements ClassLibrary<T> {

    private static final Logger logger = LoggerFactory.getLogger(ModuleClassLibrary.class);

    protected final CopyStrategyLibrary copyStrategyLibrary;

    private ReflectFactory reflectFactory;
    private ModuleEnvironment environment;

    private Map<Class<? extends T>, ClassMetadata<? extends T, ?>> classLookup = Maps.newHashMap();
    private Table<Name, Name, ClassMetadata<? extends T, ?>> urnLookup = HashBasedTable.create();

    public ModuleClassLibrary(ModuleEnvironment environment, ReflectFactory reflectFactory, CopyStrategyLibrary copyStrategyLibrary) {
        this.environment = environment;
        this.reflectFactory = reflectFactory;
        this.copyStrategyLibrary = copyStrategyLibrary;
    }

    public ModuleClassLibrary(ModuleClassLibrary<T> factory, CopyStrategyLibrary copyStrategies) {
        this.reflectFactory = factory.reflectFactory;
        this.copyStrategyLibrary = copyStrategies;
        for (Table.Cell<Name, Name, ClassMetadata<? extends T, ?>> cell: factory.urnLookup.cellSet()) {
            Name objectName = cell.getRowKey();
            Name moduleName = cell.getColumnKey();
            ClassMetadata<? extends T, ?> oldMetaData = cell.getValue();
            Class<? extends T> clazz = oldMetaData.getType();
            ResourceUrn urn = new ResourceUrn(oldMetaData.getId());
            ClassMetadata<? extends T, ?> metadata = createMetadata(clazz, factory.reflectFactory, copyStrategies, urn);

            if (metadata != null) {
                classLookup.put(clazz, metadata);
                urnLookup.put(objectName, moduleName, metadata);
            } else {
                throw new RuntimeException("Failed to create copy of class library");
            }
        }
    }

    /**
     * @param type A type being registered into the library
     * @param name The name for the type
     * @param <C>  The class of the type
     * @return An instance of ClassMetadata (or a subtype) providing metadata for the given type
     */
    protected abstract <C extends T> ClassMetadata<C, ?> createMetadata(Class<C> type, ReflectFactory factory, CopyStrategyLibrary copyStrategies, ResourceUrn name);

    @Override
    public void register(String uri, Class<? extends T> clazz) {
        register(new ResourceUrn(uri), clazz);
    }

    public void register(ResourceUrn urn, Class<? extends T> clazz) {
        ClassMetadata<? extends T, ?> metadata = createMetadata(clazz, reflectFactory, copyStrategyLibrary, urn);

        if (metadata != null) {
            classLookup.put(clazz, metadata);
            ClassMetadata<? extends T, ?> prev = urnLookup.put(urn.getResourceName(), urn.getModuleName(), metadata);
            if (prev != null && !prev.equals(metadata)) {
                logger.warn("Duplicate entry for '{}': {} and {}", urn, prev.getType(), metadata.getType());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends T> ClassMetadata<U, ?> getMetadata(Class<U> clazz) {
        if (clazz == null) {
            return null;
        }
        return (ClassMetadata<U, ?>) classLookup.get(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends T> ClassMetadata<U, ?> getMetadata(U object) {
        if (object != null) {
            return getMetadata((Class<U>) (object.getClass()));
        }
        return null;
    }

    @Override
    public <TYPE extends T> TYPE copy(TYPE object) {
        ClassMetadata<TYPE, ?> info = getMetadata(object);
        if (info != null) {
            return info.copy(object);
        }
        return null;
    }

    @Override
    public ClassMetadata<? extends T, ?> getMetadata(String urn) {
        return getMetadata(new ResourceUrn(urn));
    }

    public ClassMetadata<? extends T, ?> getMetadata(ResourceUrn urn) {
        return urnLookup.get(urn.getResourceName(), urn.getModuleName());
    }

    @Override
    public Iterator<ClassMetadata<? extends T, ?>> iterator() {
        return classLookup.values().iterator();
    }

    public List<ClassMetadata<? extends T, ?>> getMetadata(Name name) {
        return Lists.newArrayList(urnLookup.row(name).values());
    }

    public ClassMetadata<? extends T, ?> resolve(String name, Name context) {
        Module moduleContext = environment.get(context);
        if (moduleContext != null) {
            return resolve(name, moduleContext);
        }
        return null;
    }

    @Override
    public ClassMetadata<? extends T, ?> resolve(String name) {
        if (ResourceUrn.isValid(name)) {
            return getMetadata(new ResourceUrn(name));
        }
        List<ClassMetadata<? extends T, ?>> possibilities = getMetadata(new Name(name));
        if (possibilities.size() == 1) {
            return possibilities.get(0);
        }
        return null;
    }

    public ClassMetadata<? extends T, ?> resolve(String name, Module context) {
        if (ResourceUrn.isValid(name)) {
            return getMetadata(new ResourceUrn(name));
        }
        List<ClassMetadata<? extends T, ?>> possibilities = getMetadata(new Name(name));
        switch (possibilities.size()) {
            case 0:
                return null;
            case 1:
                return possibilities.get(0);
            default:

                if (context != null) {
                    Set<Name> dependencies = environment.getDependencyNamesOf(context.getId());
                    Iterator<ClassMetadata<? extends T, ?>> iterator = possibilities.iterator();
                    while (iterator.hasNext()) {
                        ClassMetadata<? extends T, ?> metadata = iterator.next();
                        ResourceUrn urn = new ResourceUrn(metadata.getId());
                        if (context.getId().equals(urn.getModuleName())) {
                            return metadata;
                        }
                        if (!dependencies.contains(urn.getModuleName())) {
                            iterator.remove();
                        }
                    }
                    if (possibilities.size() == 1) {
                        return possibilities.get(0);
                    }
                }
                return null;
        }
    }

    public Map<Class<? extends T>, ClassMetadata<? extends T, ?>> getClassLookup() {
        return this.classLookup;
    }
}
