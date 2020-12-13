package org.terasology.nui;

import org.joml.primitives.Rectanglei;
import org.joml.Vector2i;
import org.junit.jupiter.api.Test;
import org.terasology.nui.util.RectUtility;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RectUtilityTest {

    @Test
    public void testRectMapPoint() {
        Rectanglei source = new Rectanglei(1, 2, 11, 22); // 10, 20
        Rectanglei target = new Rectanglei(7, 4, 17, 24); // 10, 20

        assertEquals(RectUtility.map(source, target, new Vector2i(4, 8), new Vector2i()), new Vector2i(10, 10));
        assertEquals(RectUtility.map(source, target, new Vector2i(11, 22), new Vector2i()), new Vector2i(17, 24));
        assertEquals(RectUtility.map(source, target, new Vector2i(1, 2), new Vector2i()), new Vector2i(7, 4));
    }


    @Test
    public void testRectMapPointScaled() {
        Rectanglei source = new Rectanglei(0, 0, 10, 10); // 10, 10
        Rectanglei target = new Rectanglei(20, 20, 120, 120); // 100, 100

        assertEquals(RectUtility.map(source, target, new Vector2i(0, 0), new Vector2i()), new Vector2i(20, 20));
        assertEquals(RectUtility.map(source, target, new Vector2i(5, 5), new Vector2i()), new Vector2i(70, 70));
        assertEquals(RectUtility.map(source, target, new Vector2i(10, 10), new Vector2i()), new Vector2i(120, 120));

    }

    @Test
    public void testRectMapRect() {
        Rectanglei source = new Rectanglei(1, 2, 11, 22); // 10, 20
        Rectanglei target = new Rectanglei(7, 4, 17, 24); // 10, 20

        assertEquals(new Rectanglei(7, 4, 17, 24), RectUtility.map(source, target, new Rectanglei(1, 2, 11, 22), new Rectanglei()));
        assertEquals(new Rectanglei(9, 5, 10, 6), RectUtility.map(source, target, new Rectanglei(3, 3, 4, 4), new Rectanglei()));
    }


    @Test
    public void testRectMapRectScaled() {
        Rectanglei source = new Rectanglei(0, 0, 10, 10); // 10, 20
        Rectanglei target = new Rectanglei(20, 20, 120, 120); // 10, 20

        assertEquals(new Rectanglei(20, 20, 70, 70), RectUtility.map(source, target, new Rectanglei(0, 0, 5, 5), new Rectanglei()));
        assertEquals(new Rectanglei(70, 70, 120, 120), RectUtility.map(source, target, new Rectanglei(5, 5, 10, 10), new Rectanglei()));
    }
}
