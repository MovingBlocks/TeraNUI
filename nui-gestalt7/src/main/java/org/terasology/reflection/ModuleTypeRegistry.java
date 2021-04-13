package org.terasology.reflection;

import com.google.common.collect.Lists;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.terasology.gestalt.module.Module;
import org.terasology.gestalt.module.ModuleEnvironment;
import org.terasology.gestalt.module.sandbox.ModuleClassLoader;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class ModuleTypeRegistry extends TypeRegistry {
    protected Reflections reflections;

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
        initialize(classLoader, loader -> !(loader instanceof ModuleClassLoader));

        for (Module module : environment.getModulesOrderedByDependencies()) {
            if (module.getClasspaths().size() == 0) {
                continue;
            }

            reflections.merge(module.getModuleManifest());
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

        // TODO: Use caches if possible since scanning does not work on Android
        reflections = new Reflections(
            new ConfigurationBuilder()
                .setScanners(
                    new SubTypesScanner(false),
                    new TypeAnnotationsScanner()
                )
                .addClassLoaders(allClassLoaders)
                .addUrls(ClasspathHelper.forClassLoader(
                    allClassLoaders.stream()
                        .filter(classLoaderFilter)
                        .toArray(ClassLoader[]::new)
                ))
                .filterInputsBy(TypeRegistry::filterWhitelistedTypes)
        );

    }

    @Override
    public <T> Set<Class<? extends T>> getSubtypesOf(Class<T> type) {
        Iterable<String> subTypes = reflections.getStore().getAll(SubTypesScanner.class.getSimpleName(), type.getName());
        return ReflectionUtil.loadClasses(subTypes, reflections.getConfiguration().getClassLoaders());
    }

    @Override
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotationType) {
        return reflections.getTypesAnnotatedWith(annotationType);
    }

    @Override
    public Optional<Class<?>> load(String name) {
        return Optional.ofNullable(ReflectionUtils.forName(name, classLoaders));
    }
}
