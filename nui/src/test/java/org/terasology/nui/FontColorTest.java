// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FontColorTest {

    @Test
    public void testResetColor() {
        char resetColor = 0xF000;
        assertTrue(FontColor.isValid(resetColor));
    }

    @Test
    public void testFirstColor() {
        char firstColor = 0xE000;
        assertTrue(FontColor.isValid(firstColor));
    }

    @Test
    public void testLastColor() {
        char lastColor = 0xEFFF;
        assertTrue(FontColor.isValid(lastColor));
    }

    @Test
    public void testBetweenColor() {
        char betweenColor = 0xEB8F;
        assertTrue(FontColor.isValid(betweenColor));
    }

    @Test
    public void testInvalidColor() {
        char invalidColor = 0xA10F;
        assertFalse(FontColor.isValid(invalidColor));
    }
}
