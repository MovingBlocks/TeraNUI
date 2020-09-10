// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.joml.Vector3f;
import org.terasology.nui.reflection.copy.CopyStrategy;
import org.terasology.nui.reflection.copy.RegisterCopyStrategy;

/**
 */
@RegisterCopyStrategy
public class Vector3fCopyStrategy implements CopyStrategy<Vector3f> {

    @Override
    public Vector3f copy(Vector3f value) {
        if (value != null) {
            return new Vector3f(value);
        }
        return null;
    }
}
