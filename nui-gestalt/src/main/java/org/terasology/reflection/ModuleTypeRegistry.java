package org.terasology.reflection;

import com.google.common.collect.Lists;
import org.terasology.gestalt.di.index.CompoundClassIndex;
import org.terasology.gestalt.di.index.UrlClassIndex;
import org.terasology.gestalt.module.Module;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.gestalt.module.sandbox.ModuleClassLoader;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModuleTypeRegistry extends TypeRegistry {
    private final CompoundClassIndex classIndex = new CompoundClassIndex();

    public ModuleTypeRegistry(ModuleEnvironment environment) {
        super();
        reload(environment);
    }

    public void reload(ModuleEnvironment environment) {
        // FIXME: Reflection -- may break with updates to gestalt-module
        ClassLoader finalClassLoader = (ClassLoader) ReflectionUtil.readField(environment, "finalClassLoader");
        initialize(finalClassLoader, environment);
    }

    protected void initialize(ClassLoader classLoader, ModuleEnvironment environment) {
        initialize(classLoader, loader -> !(loader instanceof ModuleClassLoader));

        for (Module module : environment.getModulesOrderedByDependencies()) {
            if (module.getClasspaths().isEmpty()) {
                continue;
            }
            classIndex.add(module.getClassIndex());
        }
    }

    @Override
    protected void initialize(ClassLoader classLoader, Predicate<ClassLoader> classLoaderFilter) {
        List<ClassLoader> allClassLoaders = Lists.newArrayList();

        while (classLoader != null) {
            allClassLoaders.add(classLoader);
            classLoader = classLoader.getParent();
        }

        // Here allClassLoaders contains child class loaders followed by their parent. The list is
        // reversed so that classes are loaded using the originally declaring/loading class loader,
        // not a child class loader (like a ModuleClassLoader, for example)
        Collections.reverse(allClassLoaders);

        classLoaders = allClassLoaders.toArray(new ClassLoader[0]);
        for (ClassLoader loader : classLoaders) {
            classIndex.add(UrlClassIndex.byClassLoader(loader));
        }
    }

    @Override
    public <T> Set<Class<? extends T>> getSubtypesOf(Class<T> type) {
        return classIndex.getSubtypesOf(type.toString())
                .stream()
                .map(ReflectionUtil::forName)
                .map(c -> (Class<? extends T>) c)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotationType) {
        return classIndex.getTypesAnnotatedWith(annotationType.getName())
                .stream()
                .map(ReflectionUtil::forName)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Class<?>> load(String name) {
        return Optional.ofNullable(ReflectionUtil.forName(name, classLoaders));
    }
}
