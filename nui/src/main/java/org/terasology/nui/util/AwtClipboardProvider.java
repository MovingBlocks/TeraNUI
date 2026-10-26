// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.input.device.ClipboardProvider;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * The default {@link ClipboardProvider}: java.awt's system clipboard, available on desktop Java.
 * java.awt genuinely doesn't exist on Android or GWT/HTML, so callers should catch {@link
 * LinkageError} around use of this class rather than assume it always loads - see
 * {@link org.terasology.nui.widgets.UIText#getClipboardContents()}. Platforms without java.awt
 * should call {@link org.terasology.nui.widgets.UIText#setClipboardProvider} with a
 * platform-appropriate implementation instead (e.g. nui-libgdx's LibGDXClipboardProvider).
 */
public class AwtClipboardProvider implements ClipboardProvider {
    private static final Logger logger = LoggerFactory.getLogger(AwtClipboardProvider.class);

    @Override
    public String getContents() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

        try {
            if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) t.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (UnsupportedFlavorException | IOException e) {
            logger.warn("Failed to get data from clipboard", e);
        }

        return "";
    }

    @Override
    public void setContents(String value) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
    }
}
