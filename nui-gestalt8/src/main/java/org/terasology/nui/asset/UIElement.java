// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.asset;

import org.terasology.context.annotation.API;
import org.terasology.gestalt.assets.Asset;
import org.terasology.gestalt.assets.AssetType;
import org.terasology.gestalt.assets.ResourceUrn;
import org.terasology.gestalt.assets.format.AssetDataFile;
import org.terasology.nui.UIWidget;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@API
public class UIElement extends Asset<UIData> {

    private UIWidget rootWidget;

    private transient AssetDataFile source;

    private final List<Consumer<UIElement>> reloadListeners = new CopyOnWriteArrayList<>();

    public UIElement(ResourceUrn urn, AssetType<?, UIData> assetType, UIData data) {
        super(urn, assetType);
        reload(data);
    }

    /**
     * Subscribe to reload events.
     *
     * @param reloadListener the listener to add
     */
    public void subscribe(Consumer<UIElement> reloadListener) {
        reloadListeners.add(reloadListener);
    }

    /**
     * Unsubscribe from reload events.
     *
     * @param reloadListener the listener to remove. Non-existing entries will be ignored.
     */
    public void unsubscribe(Consumer<UIElement> reloadListener) {
        reloadListeners.remove(reloadListener);
    }

    @Override
    protected void doReload(UIData data) {
        rootWidget = data.getRootWidget();
        source = data.getSource();
        for (Consumer<UIElement> listener : reloadListeners) {
            listener.accept(this);
        }
    }

    public UIWidget getRootWidget() {
        return rootWidget;
    }

    public AssetDataFile getSource() {
        return source;
    }
}
