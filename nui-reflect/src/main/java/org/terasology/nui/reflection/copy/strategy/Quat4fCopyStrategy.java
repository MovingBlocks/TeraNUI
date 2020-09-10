// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import org.joml.Quaternionf;
import org.terasology.nui.reflection.copy.CopyStrategy;
import org.terasology.nui.reflection.copy.RegisterCopyStrategy;

/**
 */
@RegisterCopyStrategy
public class Quat4fCopyStrategy implements CopyStrategy<Quaternionf> {

    @Override
    public Quaternionf copy(Quaternionf value) {
        if (value != null) {
            return new Quaternionf(value);
        }
        return null;
    }
}
