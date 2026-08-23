// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.asset;

import org.terasology.context.annotation.API;
import org.terasology.gestalt.assets.AssetData;
import org.terasology.gestalt.assets.format.AssetDataFile;
import org.terasology.nui.UIWidget;

/**
 * UIData contains a UI widget that has been loaded from a UI asset.
 */
@API
public class UIData implements AssetData {
    private UIWidget rootWidget;

    private transient AssetDataFile source;

    public UIData(UIWidget rootWidget) {
        this.rootWidget = rootWidget;
    }

    /**
     * @return The root widget loaded from the UI asset.
     */
    public UIWidget getRootWidget() {
        return rootWidget;
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
