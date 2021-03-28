// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.reflection.copy.strategy;

import org.joml.Vector4i;
import org.terasology.reflection.copy.CopyStrategy;

public class Vector4iCopyStrategy implements CopyStrategy<Vector4i> {
    @Override
    public Vector4i copy(Vector4i value) {
        if (value != null) {
            return new Vector4i(value);
        }
        return null;
    }
}
