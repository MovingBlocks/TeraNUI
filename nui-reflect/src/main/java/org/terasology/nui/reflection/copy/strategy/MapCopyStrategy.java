// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.reflection.copy.strategy;

import com.google.common.collect.Maps;
import org.terasology.nui.reflection.copy.CopyStrategy;

import java.util.Map;

/**
 */
public class MapCopyStrategy<K, V> implements CopyStrategy<Map<K, V>> {

    private final CopyStrategy<K> keyStrategy;
    private final CopyStrategy<V> valueStrategy;

    public MapCopyStrategy(CopyStrategy<K> keyStrategy, CopyStrategy<V> valueStrategy) {
        this.keyStrategy = keyStrategy;
        this.valueStrategy = valueStrategy;
    }

    @Override
    public Map<K, V> copy(Map<K, V> map) {
        if (map != null) {
            Map<K, V> result = Maps.newLinkedHashMap();
            for (Map.Entry<K, V> entry : map.entrySet()) {
                result.put(keyStrategy.copy(entry.getKey()), valueStrategy.copy(entry.getValue()));
            }
            return result;
        }
        return null;
    }
}
