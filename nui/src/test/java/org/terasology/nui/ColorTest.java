// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColorTest {

    private Color color;

    private static Stream<Arguments> nextGameNamesProvider() {
        return Stream.of(
            Arguments.arguments("Custom", "Custom 1"),
            Arguments.arguments("Custom 1", "Custom 2"),
            Arguments.arguments("Custom 2", "Custom 3"),
            Arguments.arguments("Custom 9", "Custom 10"),
            Arguments.arguments("Custom 19", "Custom 20")
        );
    }

    @BeforeEach
    public void setUp() {
        color = new Color(1, 10, 60, 255);
    }

    @Test
    public void testColorToHash() {
        assertEquals("010A3CFF", color.toHex());
    }

    // -- RED -----------------------------------------------------------------

    @Test
    public void testGetRed() {
        assertEquals(1, color.r());
    }

    @Test
    public void testAlterRed() {
        color = color.alterRed(72);
        assertEquals(72, color.r());

        assertThrows(IllegalArgumentException.class,
            () -> color.alterRed(-1),
            "alter-method throws exception when color value is below lower bound");

        assertThrows(IllegalArgumentException.class,
            () -> color.alterRed(256),
            "alter-method throws exception when color value is above upper bound");
    }

    @Test
    public void testSetRed() {
        color = color.setRed(72);
        assertEquals(72, color.r());

        color = color.setRed(-1);
        assertEquals(0, color.r(), "set-method clamps when color value is below lower bound");

        color = color.setRed(256);
        assertEquals(255, color.r(), "set-method clamps when color value is above upper bound");
    }

    // -- GREEN ---------------------------------------------------------------

    @Test
    public void testGetGreen() {
        assertEquals(10, color.g());
    }

    @Test
    public void testAlterGreen() {
        color = color.alterGreen(72);
        assertEquals(72, color.g());

        assertThrows(IllegalArgumentException.class,
            () -> color.alterGreen(-1),
            "alter-method throws exception when color value is below lower bound");

        assertThrows(IllegalArgumentException.class,
            () -> color.alterGreen(256),
            "alter-method throws exception when color value is above upper bound");
    }

    @Test
    public void testSetGreen() {
        color = color.setGreen(72);
        assertEquals(72, color.g());

        color = color.setGreen(-1);
        assertEquals(0, color.g(), "set-method clamps when color value is below lower bound");

        color = color.setGreen(256);
        assertEquals(255, color.g(), "set-method clamps when color value is above upper bound");
    }

    // -- BLUE ----------------------------------------------------------------

    @Test
    public void testGetBlue() {
        assertEquals(60, color.b());
    }

    @Test
    public void testAlterBlue() {
        color = color.alterBlue(72);
        assertEquals(72, color.b());

        assertThrows(IllegalArgumentException.class,
            () -> color.alterBlue(-1),
            "alter-method throws exception when color value is below lower bound");

        assertThrows(IllegalArgumentException.class,
            () -> color.alterBlue(256),
            "alter-method throws exception when color value is above upper bound");
    }

    @Test
    public void testSetBlue() {
        color = color.setBlue(72);
        assertEquals(72, color.b());

        color = color.setBlue(-1);
        assertEquals(0, color.b(), "set-method clamps when color value is below lower bound");

        color = color.setBlue(256);
        assertEquals(255, color.b(), "set-method clamps when color value is above upper bound");
    }

    // -- ALPHA ----------------------------------------------------------------

    @Test
    public void testGetAlpha() {
        assertEquals(255, color.a());
    }

    @Test
    public void testAlterAlpha() {
        color = color.alterAlpha(72);
        assertEquals(72, color.a());

        assertThrows(IllegalArgumentException.class,
            () -> color.alterAlpha(-1),
            "alter-method throws exception when color value is below lower bound");

        assertThrows(IllegalArgumentException.class,
            () -> color.alterAlpha(256),
            "alter-method throws exception when color value is above upper bound");
    }

    @Test
    public void testSetAlpha() {
        color = color.setAlpha(72);
        assertEquals(72, color.a());

        color = color.setAlpha(-1);
        assertEquals(0, color.a(), "set-method clamps when color value is below lower bound");

        color = color.setAlpha(256);
        assertEquals(255, color.a(), "set-method clamps when color value is above upper bound");
    }

    @Test
    public void testColorR() {
        Color c1 = new Color(255, 0, 0);

        assertEquals(255, c1.r());
        assertEquals(0, c1.g());
        assertEquals(0, c1.b());
        assertEquals(255, c1.a());
        assertEquals(Color.red, c1);
    }

    @Test
    public void testColorG() {
        Color c2 = new Color(0, 255, 0);

        assertEquals(c2.r(), 0);
        assertEquals(c2.g(), 255);
        assertEquals(c2.b(), 0);
        assertEquals(c2.a(), 255);
        assertEquals(Color.green, c2);
    }

    @Test
    public void testColorB() {
        Color c3 = new Color(0, 0, 255);

        assertEquals(c3.r(), 0);
        assertEquals(c3.g(), 0);
        assertEquals(c3.b(), 255);
        assertEquals(Color.blue, c3);
    }

    @Test
    public void testColorRWithOverflow() {
        Color c4 = new Color(300, 0, 255);

        assertEquals(c4.r(), 255);
        assertEquals(c4.g(), 0);
        assertEquals(c4.b(), 255);
        assertEquals(c4.a(), 255);
        assertEquals(Color.magenta, c4);
    }

    @Test
    public void testFloatColorR() {
        Color c1 = new Color(1.0f, 0, 0);

        assertEquals(255, c1.r());
        assertEquals(0, c1.g());
        assertEquals(0, c1.b());
        assertEquals(255, c1.a());
        assertEquals(Color.red, c1);
    }


    @Test
    public void testFloatColorG() {
        Color c2 = new Color(0, 1.0f, 0);

        assertEquals(c2.r(), 0);
        assertEquals(c2.g(), 255);
        assertEquals(c2.b(), 0);
        assertEquals(c2.a(), 255);
        assertEquals(Color.green, c2);
    }

    @Test
    public void testFloatColorB() {
        Color c3 = new Color(0, 0, 1.0f);

        assertEquals(c3.r(), 0);
        assertEquals(c3.g(), 0);
        assertEquals(c3.b(), 255);
        assertEquals(Color.blue, c3);
    }

    @Test
    public void testFloatColorRWithOverflow() {
        Color c4 = new Color(1.5f, 0, 1.0f);

        assertEquals(c4.r(), 255);
        assertEquals(c4.g(), 0);
        assertEquals(c4.b(), 255);
        assertEquals(c4.a(), 255);
        assertEquals(Color.magenta, c4);
    }

    @Test
    public void testFloatHalfRedColor() {
        Color c5 = new Color(0, .5f, 1.0f);


        assertEquals(c5.r(), 0);
        assertEquals(c5.g(), 127);
        assertEquals(c5.b(), 255);
        assertEquals(c5.a(), 255);
    }

    @Test
    public void testFloatAlpha() {
        Color c5 = new Color(0, .5f, 1.0f, .5f);

        assertEquals(c5.r(), 0);
        assertEquals(c5.g(), 127);
        assertEquals(c5.b(), 255);
        assertEquals(c5.a(), 127);
    }

    @Test
    public void testInvert() {
        Color c1 = new Color(255, 0, 0, 155);

        c1.invert();

        assertEquals(0, c1.r());
        assertEquals(255, c1.g());
        assertEquals(255, c1.b());
        assertEquals(155, c1.a());
    }
}
