// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.joml.Vector4f;
import org.terasology.nui.reflection.copy.CopyStrategy;
import org.terasology.nui.reflection.copy.RegisterCopyStrategy;

/**
 */
@RegisterCopyStrategy
public class Vector4fCopyStrategy implements CopyStrategy<Vector4f> {

    @Override
    public Vector4f copy(Vector4f value) {
        if (value != null) {
            return new Vector4f(value);
        }
        return null;
    }
}
