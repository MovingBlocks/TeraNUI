package org.terasology.reflection;

import org.terasology.module.Module;
import org.terasology.module.ModuleEnvironment;
import org.terasology.module.sandbox.ModuleClassLoader;

public class ModuleTypeRegistry extends TypeRegistry {
    public ModuleTypeRegistry() {
        super();
    }

    public ModuleTypeRegistry(ModuleEnvironment environment) {
        super();
        reload(environment);
    }

    public void reload(ModuleEnvironment environment) {
        // FIXME: Reflection -- may break with updates to gestalt-module
        ClassLoader finalClassLoader = (ClassLoader) ReflectionUtil.readField(environment, "finalClassLoader");
        initializeReflections(finalClassLoader, environment);
    }

    private void initializeReflections(ClassLoader classLoader, ModuleEnvironment environment) {
        initializeReflections(classLoader, loader -> !(loader instanceof ModuleClassLoader));

        for (Module module : environment.getModulesOrderedByDependencies()) {
            if (!module.isCodeModule()) {
                continue;
            }

            reflections.merge(module.getReflectionsFragment());
        }
    }
}
