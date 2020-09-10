// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.joml.Vector2f;
import org.terasology.nui.reflection.copy.CopyStrategy;
import org.terasology.nui.reflection.copy.RegisterCopyStrategy;

/**
 */
@RegisterCopyStrategy
public class Vector2fCopyStrategy implements CopyStrategy<Vector2f> {

    @Override
    public Vector2f copy(Vector2f value) {
        if (value != null) {
            return new Vector2f(value);
        }
        return null;
    }
}
