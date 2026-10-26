// Copyright 2026 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.widgets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.terasology.input.device.ClipboardProvider;
import org.terasology.nui.util.AwtClipboardProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UITextClipboardTest {

    @AfterEach
    void restoreDefaultClipboardProvider() {
        UIText.setClipboardProvider(new AwtClipboardProvider());
    }

    @Test
    void copiesSelectedTextToClipboard() {
        TestUIText text = new TestUIText();
        text.setText("copy this");
        text.setCursorPosition(0);
        text.setCursorPosition(text.getText().length(), false, false);

        text.copySelection();

        assertEquals("copy this", text.clipboardContents);
    }

    @Test
    void pastesClipboardTextAtCursor() {
        TestUIText text = new TestUIText();
        text.setText("ab");
        text.setCursorPosition(1);
        text.clipboardContents = "XY";

        text.paste();

        assertEquals("aXYb", text.getText());
        assertEquals(3, text.getCursorPosition());
    }

    @Test
    void returnsEmptyTextWhenAwtClipboardIsUnavailable() {
        TestUIText text = new TestUIText();
        text.clipboardUnavailable = true;

        assertEquals("", text.getClipboardContents());
    }

    @Test
    void copyingDoesNotFailWhenAwtClipboardIsUnavailable() {
        TestUIText text = new TestUIText();
        text.setText("copy this");
        text.setCursorPosition(0);
        text.setCursorPosition(text.getText().length(), false, false);
        text.clipboardUnavailable = true;

        assertDoesNotThrow(text::copySelection);
    }

    @Test
    void pastingAddsNothingWhenAwtClipboardIsUnavailable() {
        TestUIText text = new TestUIText();
        text.setText("keep this");
        text.setCursorPosition(4);
        text.clipboardUnavailable = true;

        assertDoesNotThrow(text::paste);
        assertEquals("keep this", text.getText());
    }

    private static final class TestUIText extends UIText {
        private String clipboardContents = "";
        private boolean clipboardUnavailable;

        private TestUIText() {
            UIText.setClipboardProvider(new ClipboardProvider() {
                @Override
                public String getContents() {
                    if (clipboardUnavailable) {
                        throw new NoClassDefFoundError("java/awt/Toolkit");
                    }
                    return clipboardContents;
                }

                @Override
                public void setContents(String value) {
                    if (clipboardUnavailable) {
                        throw new NoClassDefFoundError("java/awt/Toolkit");
                    }
                    clipboardContents = value;
                }
            });
        }
    }
}
