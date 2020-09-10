// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.reflection;

import org.terasology.gestalt.module.sandbox.API;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark simple classes that for mapped serialization
 * (this is, they serialize to a map of field name to value).
 * <br><br>
 * These classes must not have recursive nested elements (the flatter they are the better),
 * and must not depend on objects appearing multiple times in their structure
 * being the same object - when deserialized each object will be a separate instance.
 */
@API
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedContainer {
}
