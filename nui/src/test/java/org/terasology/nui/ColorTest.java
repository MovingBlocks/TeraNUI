package org.terasology.nui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorTest {

    @Test
    public void testColor(){
        Color c1 = new Color(255,0,0);
        Color c2 = new Color(0,255,0);
        Color c3 = new Color(0,0,255);
        Color c4 = new Color(300,0,255);

        assertEquals(255,c1.r());
        assertEquals(0,c1.g());
        assertEquals(0,c1.b());
        assertEquals(255,c1.a());
        assertEquals(Color.red,c1);

        assertEquals(c2.r(),0);
        assertEquals(c2.g(),255);
        assertEquals(c2.b(),0);
        assertEquals(c2.a(),255);
        assertEquals(Color.green,c2);

        assertEquals(c3.r(),0);
        assertEquals(c3.g(),0);
        assertEquals(c3.b(),255);
        assertEquals(Color.blue, c3);

        assertEquals(c4.r(),255);
        assertEquals(c4.g(),0);
        assertEquals(c4.b(),255);
        assertEquals(c4.a(),255);
        assertEquals(Color.magenta, c4);
    }


    @Test
    public void testInvert() {
        Color c1 = new Color(255,0,0, 155);

        c1.invert();

        assertEquals(0, c1.r());
        assertEquals(255, c1.g());
        assertEquals(255, c1.b());
        assertEquals(155, c1.a());
    }
}
