// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.reflection;

import com.google.common.collect.Lists;
import org.terasology.gestalt.module.ModuleEnvironment;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unlike nui-gestalt5/nui-gestalt7's ModuleTypeRegistry, this doesn't build or merge any
 * org.reflections.Reflections instances - gestalt 8 dropped runtime Reflections scanning in
 * favor of a compile-time-generated {@link org.terasology.gestalt.di.index.ClassIndex} per
 * module (via the gestalt-inject-java annotation processor), and {@link ModuleEnvironment}
 * already aggregates those across every loaded module through its own getSubtypesOf()/
 * getTypesAnnotatedWith(). This class just delegates to that instead of re-implementing it.
 */
public class ModuleTypeRegistry extends TypeRegistry {
    private ModuleEnvironment environment;

    public ModuleTypeRegistry(ModuleEnvironment environment) {
        super();
        reload(environment);
    }

    public void reload(ModuleEnvironment environment) {
        this.environment = environment;

        // classLoaders isn't needed for getSubtypesOf()/getTypesAnnotatedWith() below (those go
        // through the module-aggregated ClassIndex instead), but the inherited load(String) still
        // uses it for arbitrary single-class lookups, so it's populated the same way gestalt7's
        // ModuleTypeRegistry did.
        // FIXME: Reflection -- may break with updates to gestalt-module
        ClassLoader finalClassLoader = (ClassLoader) ReflectionUtil.readField(environment, "finalClassLoader");
        List<ClassLoader> allClassLoaders = Lists.newArrayList();
        ClassLoader current = finalClassLoader;
        while (current != null) {
            allClassLoaders.add(current);
            current = current.getParent();
        }
        Collections.reverse(allClassLoaders);
        classLoaders = allClassLoaders.toArray(new ClassLoader[0]);
    }

    /**
     * Note: unlike the base TypeRegistry (used for gestalt 5/7), this doesn't apply
     * {@link TypeRegistry#WHITELISTED_CLASSES}/{@link TypeRegistry#WHITELISTED_PACKAGES}
     * filtering - ModuleEnvironment's per-module ClassIndex is already scoped to just that
     * module's own compiled classes, so the whitelist's original purpose (narrowing an
     * otherwise-too-broad classpath scan) doesn't apply the same way here. Flagging this
     * explicitly since it's a real behavioral difference from the gestalt 5/7 implementation,
     * not something verified against every existing whitelist use in the engine.
     */
    @Override
    public <T> Set<Class<? extends T>> getSubtypesOf(Class<T> type) {
        Set<Class<? extends T>> result = new HashSet<>();
        environment.getSubtypesOf(type).forEach(result::add);
        return result;
    }

    @Override
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotationType) {
        Set<Class<?>> result = new HashSet<>();
        environment.getTypesAnnotatedWith(annotationType).forEach(result::add);
        return result;
    }
}
