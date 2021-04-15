// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.reflection;

import org.reflections.Reflections;
import org.terasology.gestalt.module.Module;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.gestalt.module.sandbox.ModuleClassLoader;

public class ModuleTypeRegistry extends TypeRegistry {
    public ModuleTypeRegistry(ModuleEnvironment environment) {
        super();
        reload(environment);
    }

    public void reload(ModuleEnvironment environment) {
        // FIXME: Reflection -- may break with updates to gestalt-module
        ClassLoader finalClassLoader = (ClassLoader) ReflectionUtil.readField(environment, "finalClassLoader");
        initializeReflections(finalClassLoader, environment);
    }

    protected void initializeReflections(ClassLoader classLoader, ModuleEnvironment environment) {
        initializeReflections(classLoader, loader -> !(loader instanceof ModuleClassLoader));

        for (Module module : environment.getModulesOrderedByDependencies()) {
            Reflections moduleReflections = module.getModuleManifest();
            if (moduleReflections != null) {
                reflections.merge(moduleReflections);
            }
        }
    }
}
