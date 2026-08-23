// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.skin;

import com.google.common.collect.Maps;
import org.terasology.gestalt.assets.AssetData;
import org.terasology.gestalt.assets.format.AssetDataFile;

import java.util.Map;

/**
 */
public class UISkinData implements AssetData {
    private UISkin uiSkin;

    private transient AssetDataFile source;

    public UISkinData(UISkin uiSkin) {
        this.uiSkin = uiSkin;
    }

    public UIStyleFamily getFamily(String familyName) {
        return uiSkin.getFamily(familyName);
    }

    public UISkin getUiSkin() {
        return uiSkin;
    }

    /**
     * @param source The {@link AssetDataFile} this asset has been loaded from.
     */
    public void setSource(AssetDataFile source) {
        this.source = source;
    }

    /**
     * @return The {@link AssetDataFile} this asset has been loaded from.
     */
    public AssetDataFile getSource() {
        return source;
    }
}
