// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.backends.libgdx;

import com.badlogic.gdx.Gdx;
import org.terasology.input.device.ClipboardProvider;

/**
 * Clipboard access via libGDX's own cross-platform {@link com.badlogic.gdx.utils.Clipboard}
 * abstraction ({@code Gdx.app.getClipboard()}), which is backed by java.awt on desktop,
 * android.content.ClipboardManager on Android, and the browser clipboard on GWT/HTML - unlike
 * org.terasology.nui.util.AwtClipboardProvider (the default), this one actually works on every
 * libGDX backend. Applications running NUI under libGDX should call
 * {@code UIText.setClipboardProvider(new LibGDXClipboardProvider())} during startup.
 */
public class LibGDXClipboardProvider implements ClipboardProvider {
    @Override
    public String getContents() {
        String contents = Gdx.app.getClipboard().getContents();
        return contents != null ? contents : "";
    }

    @Override
    public void setContents(String value) {
        Gdx.app.getClipboard().setContents(value);
    }
}
