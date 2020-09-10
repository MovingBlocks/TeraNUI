// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.nui.reflection.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection based implementation of ReflectFactory. Uses standard Java reflection to provide the necessary reflection functionality.
 *
 */
public class ReflectionReflectFactory implements ReflectFactory {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionReflectFactory.class);

    @Override
    public <T> ObjectConstructor<T> createConstructor(Class<T> type) throws NoSuchMethodException {
        if (hasConstructor(type)) {
            return new ReflectionConstructor<>(type);
        }
        return null;
    }

    private <T> boolean hasConstructor(Class<T> type) {
        try {
            return !type.isInterface() && type.getDeclaredConstructor() != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public <T> FieldAccessor<T, ?> createFieldAccessor(Class<T> ownerType, Field field) {
        return new ReflectionFieldAccessor<>(field, field.getType());
    }

    @Override
    public <T, U> FieldAccessor<T, U> createFieldAccessor(Class<T> ownerType, Field field, Class<U> fieldType) {
        return new ReflectionFieldAccessor<>(field, fieldType);
    }

    /**
     * ObjectConstructor using a Java Constructor instance to construct the object
     *
     * @param <T>
     */
    private static class ReflectionConstructor<T> implements ObjectConstructor<T> {
        private final Class<T> type;
        private final Constructor<T> constructor;

         ReflectionConstructor(Class<T> type) throws NoSuchMethodException {
            this.type = type;
            constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
        }

        @Override
        public T construct() {
            try {
                return constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                logger.error("Exception instantiating type: {}", type, e);
            }
            return null;
        }
    }

    /**
     * Field accessor using reflection to access the getter and setter methods, or the field directly if necessary.
     *
     * @param <T>
     * @param <U>
     */
    private static class ReflectionFieldAccessor<T, U> implements FieldAccessor<T, U> {

        private final Field field;
        private final Method getter;
        private final Method setter;

         ReflectionFieldAccessor(Field field, Class<U> fieldType) {
            this.field = field;
            getter = ReflectionUtil.findGetter(field.getName(), field.getDeclaringClass(), fieldType);
            setter = ReflectionUtil.findSetter(field.getName(), field.getDeclaringClass(), fieldType);
            if (getter == null || setter == null) {
                field.setAccessible(true);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public U getValue(T target) {
            try {
                if (getter != null) {
                    return (U) (getter.invoke(target));
                }
                return (U) field.get(target);
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.error("Exception during access of {} from {}", field.getName(), target.getClass(), e);
            }
            return null;
        }

        @Override
        public void setValue(T target, U value) {
            try {
                if (setter != null) {
                    setter.invoke(target, value);
                } else {
                    field.set(target, value);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.error("Exception during setting of {} from {}", field.getName(), target.getClass(), e);
            }
        }
    }
}
