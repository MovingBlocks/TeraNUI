// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.input.device;

/**
 * Abstracts system clipboard access so widgets aren't tied to one platform's clipboard API - desktop
 * Java has java.awt, Android has its own android.content.ClipboardManager, GWT/HTML has neither.
 * Implementations are supplied by whatever backend the application is running under (see
 * nui-libgdx's LibGDXClipboardProvider) rather than by this module.
 */
public interface ClipboardProvider {
    /**
     * @return the current textual contents of the clipboard, or an empty string if there are none
     * or the clipboard isn't available on this platform.
     */
    String getContents();

    /**
     * @param value the new textual contents of the clipboard.
     */
    void setContents(String value);
}
