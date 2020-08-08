package org.terasology.nui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorTest {

    @Test
    public void testColor(){
        Color c1 = new Color(255,0,0);
        Color c2 = new Color(0,255,0);
        Color c3 = new Color(0,0,255);

        assertEquals(c1.r(),255);
        assertEquals(c1.g(),0);
        assertEquals(c1.b(),0);
        assertEquals(c1, Color.red);

        assertEquals(c2.r(),0);
        assertEquals(c2.g(),255);
        assertEquals(c2.b(),0);
        assertEquals(c1, Color.green);

        assertEquals(c3.r(),0);
        assertEquals(c3.g(),0);
        assertEquals(c3.b(),255);
        assertEquals(c1, Color.blue);
    }
}
