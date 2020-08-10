package org.terasology.nui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColorTest {

    private Color color;

    @BeforeEach
    public void setUp() {
        color = new Color(1, 10, 60, 255);
    }

    @Test
    public void testColorToHash() {
        assertEquals("010A3CFF", color.toHex());
    }

    @Test
    public void testGetRed() {
        assertEquals(1, color.r());
    }

    @Test
    public void testAlterRed() {
        color = color.alterRed(72);
        assertEquals(72, color.r());
    }

    @Test
    public void testAlterRedThrowsWhenColorLessThanLowerBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterRed(-1));
    }

    @Test
    public void testAlterRedThrowsWhenColorLargerThanUpperBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterRed(256));
    }

    @Test
    public void testGetGreen() {
        assertEquals(10, color.g());
    }

    @Test
    public void testAlterGreen() {
        color = color.alterGreen(72);
        assertEquals(72, color.g());
    }

    @Test
    public void testAlterGreenThrowsWhenColorLessThanLowerBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterGreen(-1));
    }

    @Test
    public void testAlterGreenThrowsWhenColorLargerThanUpperBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterGreen(256));
    }

    @Test
    public void testGetBlue() {
        assertEquals(60, color.b());
    }

    @Test
    public void testAlterBlue() {
        color = color.alterBlue(72);
        assertEquals(72, color.b());
    }

    @Test
    public void testAlterBlueThrowsWhenColorLessThanLowerBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterBlue(-1));
    }

    @Test
    public void testAlterBlueThrowsWhenColorLargerThanUpperBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterBlue(256));
    }

    @Test
    public void testGetAlpha() {
        assertEquals(255, color.a());
    }

    @Test
    public void testAlterAlpha() {
        color = color.alterAlpha(72);
        assertEquals(72, color.a());
    }

    @Test
    public void testAlterAlphaThrowsWhenColorLessThanLowerBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterAlpha(-1));
    }

    @Test
    public void testAlterAlphaThrowsWhenColorLargerThanUpperBound() {
        assertThrows(IllegalArgumentException.class,
                () -> color.alterAlpha(256));
    }

    @Test
    public void testColor() {
        Color c1 = new Color(255, 0, 0);
        Color c2 = new Color(0, 255, 0);
        Color c3 = new Color(0, 0, 255);
        Color c4 = new Color(300, 0, 255);

        assertEquals(255, c1.r());
        assertEquals(0, c1.g());
        assertEquals(0, c1.b());
        assertEquals(255, c1.a());
        assertEquals(Color.red, c1);

        assertEquals(c2.r(), 0);
        assertEquals(c2.g(), 255);
        assertEquals(c2.b(), 0);
        assertEquals(c2.a(), 255);
        assertEquals(Color.green, c2);

        assertEquals(c3.r(), 0);
        assertEquals(c3.g(), 0);
        assertEquals(c3.b(), 255);
        assertEquals(Color.blue, c3);

        assertEquals(c4.r(), 255);
        assertEquals(c4.g(), 0);
        assertEquals(c4.b(), 255);
        assertEquals(c4.a(), 255);
        assertEquals(Color.magenta, c4);
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


    @Test
    public void testColorSet() {
        Color c1 = new Color(255, 0, 0);
        assertEquals(Color.red, c1);
        c1.setGreen(155)
            .setRed(155)
            .setBlue(155);
        assertNotEquals(Color.red, c1);
        assertEquals(c1.r(), 155);
        assertEquals(c1.g(), 155);
        assertEquals(c1.b(), 155);
        assertEquals(c1.a(), 255);
    }
}
