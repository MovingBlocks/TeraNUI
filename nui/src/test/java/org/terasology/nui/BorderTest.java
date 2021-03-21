// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.nui;

import org.terasology.joml.geom.Rectanglei;
import org.joml.Vector2i;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.terasology.nui.util.RectUtility;

import static org.junit.jupiter.api.Assertions.*;

class BorderTest {

    private Border border;

    @BeforeEach
    public void initBorder() {
        border = new Border(10, 10, 10, 10);
    }

    @Test
    public void shrinkSameBorderAndRegionSize() {
        assertEquals(new Rectanglei(),
                border.shrink(RectUtility.createFromMinAndSize(10, 10, 10, 10)));
    }

    @Test
    public void shrinkBorder() {
        assertEquals(RectUtility.createFromMinAndSize(40, 40, 10, 10),
                border.shrink(RectUtility.createFromMinAndSize(30, 30, 30, 30)));
    }

    @Test
    public void shrinkVector() {
        assertEquals(new Vector2i(-10, -10), border.shrink(new Vector2i(10, 10)));
    }

    @Test
    public void getTotals() {
        assertEquals(new Vector2i(20, 20), border.getTotals());
    }

    @Test
    public void growVector() {
        assertEquals(new Vector2i(30, 30), border.grow(new Vector2i(10, 10)));
    }

    @Test
    public void growVectorMax() {
        assertEquals(new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE),
                border.grow(new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE)));
    }

    @Test
    public void growBorder() {
        assertEquals(RectUtility.createFromMinAndSize(20, 20, 50, 50),
                border.grow(RectUtility.createFromMinAndSize(30, 30, 30, 30)));
    }

    @Test
    public void growBorderMax() {
        assertEquals(
                RectUtility.createFromMinAndSize(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE),
                border.grow(RectUtility.createFromMinAndSize(10, 10, Integer.MAX_VALUE, Integer.MAX_VALUE)));
    }

}
