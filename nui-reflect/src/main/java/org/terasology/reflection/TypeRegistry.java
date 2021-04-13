/*
 * Copyright 2019 MovingBlocks
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
package org.terasology.reflection;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public abstract class TypeRegistry {
    public static Set<String> WHITELISTED_CLASSES = new HashSet<>();
    public static Set<String> WHITELISTED_PACKAGES = new HashSet<>();

    protected ClassLoader[] classLoaders;

    /**
     * Creates an empty {@link TypeRegistry}. No types are loaded when this constructor
     * is called -- to populate the registry use one of the other parameterized constructors.
     */
    public TypeRegistry() {
    }

    public TypeRegistry(ClassLoader classLoader) {
        this();
        initialize(classLoader, loader -> true);
    }

    public TypeRegistry(ClassLoader classLoader, Predicate<ClassLoader> classLoaderFilter) {
        this();
        initialize(classLoader, classLoaderFilter);
    }

    protected static boolean filterWhitelistedTypes(String typeName) {
        if (typeName == null) {
            return false;
        }

        typeName = typeName.replace(".class", "");

        int i = typeName.lastIndexOf('.');
        if (i == -1) {
            return false;
        }

        String packageName = typeName.substring(0, i);

        return WHITELISTED_PACKAGES.contains(packageName) || WHITELISTED_CLASSES.contains(typeName);
    }

    protected abstract void initialize(ClassLoader classLoader, Predicate<ClassLoader> classLoaderFilter);

    public abstract <T> Set<Class<? extends T>> getSubtypesOf(Class<T> type);

    public abstract Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotationType);

    public abstract Optional<Class<?>> load(String name);
}
