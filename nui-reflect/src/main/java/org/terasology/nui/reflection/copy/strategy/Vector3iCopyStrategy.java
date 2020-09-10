// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.joml.Vector3i;
import org.terasology.nui.reflection.copy.CopyStrategy;
import org.terasology.nui.reflection.copy.RegisterCopyStrategy;

/**
 */
@RegisterCopyStrategy
public class Vector3iCopyStrategy implements CopyStrategy<Vector3i> {

    @Override
    public Vector3i copy(Vector3i value) {
        if (value != null) {
            return new Vector3i(value);
        }
        return null;
    }
}
