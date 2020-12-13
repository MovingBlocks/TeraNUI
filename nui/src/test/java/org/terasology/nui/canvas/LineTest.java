// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui.canvas;

import org.joml.primitives.Rectanglei;
import org.joml.Vector2i;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasology.nui.util.RectUtility;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    private Rectanglei cropRegion;

    @BeforeEach
    public void setup() {
        cropRegion = new Rectanglei(10, 20, 30, 30);
    }

    @Test
    public void testRelativeToAbsolute() {
        Rectanglei relativeRegion = new Rectanglei(5, 10, 20, 15);

        assertEquals(Line.relativeToAbsolute(relativeRegion, cropRegion),
                RectUtility.createFromMinAndSize(cropRegion.minX + relativeRegion.minX, cropRegion.minY + relativeRegion.minY,
                        relativeRegion.lengthX(), relativeRegion.lengthY()));
    }

    @Test
    public void testLineCoordinatesNoIntersection() {
        //Line is located in the bottom left, outside the region
        int sx = cropRegion.minX - 5;
        int sy = cropRegion.minY - 5;
        int ex = cropRegion.minX - 5;
        int ey = cropRegion.minY - 5;

        assertNull(Line.getLineCoordinates(sx, sy, ex, ey, new Rectanglei(), cropRegion));

        // Top left
        sx = cropRegion.minX - 5;
        sy = cropRegion.maxY + 5;
        ex = cropRegion.minX - 5;
        ey = cropRegion.maxY + 5;

        assertNull(Line.getLineCoordinates(sx, sy, ex, ey, new Rectanglei(), cropRegion));

        // Bottom right
        sx = cropRegion.maxX + 5;
        sy = cropRegion.minY - 5;
        ex = cropRegion.maxX + 5;
        ey = cropRegion.maxY - 5;

        assertNull(Line.getLineCoordinates(sx, sy, ex, ey, new Rectanglei(), cropRegion));

        // Top right
        sx = cropRegion.maxX + 5;
        sy = cropRegion.maxY + 5;
        ex = cropRegion.maxX + 5;
        ey = cropRegion.maxY + 5;

        assertNull(Line.getLineCoordinates(sx, sy, ex, ey, new Rectanglei(), cropRegion));
    }

    @Test
    public void testLineCoordinatesIntersection() {
        // Test several preset intersecting lines
        assertEquals(new Line.LineCoordinates(new Vector2i(10, 30), new Vector2i(30, 20)),
                Line.getLineCoordinates(0, 40, 40, 0, new Rectanglei(), cropRegion));
        assertEquals(new Line.LineCoordinates(new Vector2i(10, 25), new Vector2i(30, 25)),
                Line.getLineCoordinates(5, 25, 35, 25, new Rectanglei(), cropRegion));
        assertEquals(new Line.LineCoordinates(new Vector2i(20, 20), new Vector2i(20, 30)),
                Line.getLineCoordinates(20, 5, 20, 35, new Rectanglei(), cropRegion));
        assertEquals(new Line.LineCoordinates(new Vector2i(20, 25), new Vector2i(30, 30)),
                Line.getLineCoordinates(20, 25, 40, 40, new Rectanglei(), cropRegion));
    }

}
