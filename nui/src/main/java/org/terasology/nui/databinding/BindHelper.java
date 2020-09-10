// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.databinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.nui.reflection.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 */
public final class BindHelper {

    private static final Logger logger = LoggerFactory.getLogger(BindHelper.class);

    private BindHelper() {
    }

    public static <T> Binding<T> bindBeanProperty(String property, Object source, Class<T> propertyType) {
        Method getter = ReflectionUtil.findGetter(property, source.getClass(), propertyType);
        Method setter = ReflectionUtil.findSetter(property, source.getClass(), propertyType);
        if (getter == null || setter == null) {
            logger.warn("Failed to resolve property {} of type {} - is the getter or setter missing?", property, source.getClass());
            return new DefaultBinding<>();
        }
        return BeanBinding.create(source, getter, setter);
    }

    public static <T> Binding<List<T>> bindBeanListProperty(String property, Object source, Class<T> propertyType) {
        Method getter = ReflectionUtil.findGetter(property, source.getClass(), List.class);
        Method setter = ReflectionUtil.findSetter(property, source.getClass(), List.class);
        if (getter == null || setter == null) {
            logger.warn("Failed to resolve property {} of type {} - is the getter or setter missing?", property, source.getClass());
            return new DefaultBinding<>();
        }
        return BeanBinding.create(source, getter, setter);
    }

    public static <T, U> Binding<T> bindBoundBeanProperty(String property, Binding<U> source, Class<U> boundType, Class<T> propertyType) {
        Method getter = ReflectionUtil.findGetter(property, boundType, propertyType);
        Method setter = ReflectionUtil.findSetter(property, boundType, propertyType);
        if (getter == null || setter == null) {
            logger.warn("Failed to resolve property {} of type {} - is the getter or setter missing?", property, boundType);
            return new DefaultBinding<>();
        }
        return BeanBinding.createBound(source, getter, setter);
    }
}
