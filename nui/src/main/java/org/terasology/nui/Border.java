/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui;

import org.joml.primitives.Rectanglei;
import org.joml.Vector2i;
import org.terasology.nui.util.NUIMathUtil;
import org.terasology.nui.util.RectUtility;

import java.util.Objects;

/**
 * The size of a border, supporting independent widths on each side.
 * <br><br>
 * Immutable
 *
 */
public class Border {
    public static final Border ZERO = new Border(0, 0, 0, 0);

    private final int left;
    private final int right;
    private final int top;
    private final int bottom;

    public Border(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTotalWidth() {
        return left + right;
    }

    public int getTotalHeight() {
        return top + bottom;
    }

    public boolean isEmpty() {
        return left == 0 && right == 0 && top == 0 && bottom == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Border) {
            Border other = (Border) obj;
            return left == other.left && right == other.right
                    && top == other.top && bottom == other.bottom;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, top, bottom);
    }

    public Rectanglei shrink(Rectanglei region) {
        return RectUtility.createFromMinAndSize(region.minX + getLeft(), region.minY + getTop(),
                region.lengthX() - getTotalWidth(), region.lengthY() - getTotalHeight());
    }

    public Vector2i shrink(Vector2i size) {
        return new Vector2i(size.x - getTotalWidth(), size.y - getTotalHeight());
    }

    public Vector2i getTotals() {
        return new Vector2i(getTotalWidth(), getTotalHeight());
    }

    public Vector2i grow(Vector2i size) {
        // Note protection against overflow
        return new Vector2i(NUIMathUtil.addClampAtMax(size.x, getTotalWidth()), NUIMathUtil.addClampAtMax(size.y, getTotalHeight()));
    }

    public Rectanglei grow(Rectanglei region) {
        // Note protection against overflow of the size
        return RectUtility.createFromMinAndSize(region.minX - getLeft(), region.minY - getTop(),
                NUIMathUtil.addClampAtMax(region.lengthX(), getTotalWidth()), NUIMathUtil.addClampAtMax(region.lengthY(), getTotalHeight()));
    }
}
