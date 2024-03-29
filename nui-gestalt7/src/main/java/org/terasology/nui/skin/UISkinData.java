/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
