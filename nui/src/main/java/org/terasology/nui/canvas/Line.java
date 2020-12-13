/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.nui.canvas;

import org.joml.primitives.Rectanglei;
import org.joml.Vector2i;

import java.util.Objects;

/**
 *
 */
public final class Line {

    private Line() {

    }

    public static LineCoordinates getLineCoordinates(int startX, int startY, int endX, int endY,
                                                     Rectanglei baseRegion, Rectanglei cropRegion) {
        Rectanglei region = new Rectanglei(Math.min(startX, endX), Math.min(startY, endY),
                Math.max(startX, endX), Math.max(startY, endY));
        Rectanglei absoluteRegion = relativeToAbsolute(region, baseRegion);
        Rectanglei finalRegion = intersect(cropRegion, absoluteRegion);

        // Check for valid rectangle (JOML's built-in method does not accept a width/height of 0)
        if (finalRegion.minX <= finalRegion.maxX && finalRegion.minY <= finalRegion.maxY) {
            int sx = startX > endX ? finalRegion.maxX : finalRegion.minX;
            int sy = startY > endY ? finalRegion.maxY : finalRegion.minY;
            int ex = startX > endX ? finalRegion.minX : finalRegion.maxX;
            int ey = startY > endY ? finalRegion.minY : finalRegion.maxY;
            return new LineCoordinates(new Vector2i(sx, sy), new Vector2i(ex, ey));
        } else {
            return null;
        }
    }

    /**
     * JOML considers Rectangles with a width or height of 0 to be invalid. Lines can have either value as 0 but not
     * both, so the method needs to be redefined without that restriction
     */
    private static Rectanglei intersect(Rectanglei a, Rectanglei b) {
        Rectanglei result = new Rectanglei();
        result.minX = Math.max(a.minX, b.minX);
        result.minY = Math.max(a.minY, b.minY);
        result.maxX = Math.min(a.maxX, b.maxX);
        result.maxY = Math.min(a.maxY, b.maxY);

        return result;
    }

    public static Rectanglei relativeToAbsolute(Rectanglei region, Rectanglei baseRegion) {
        // Explicitly compute the rectangle from min and size here, as we don't want to ensure
        // that the rectangle has positive dimensions and to avoid the fallback to the empty rectangle.
        final int minX = region.minX + baseRegion.minX;
        final int minY = region.minY + baseRegion.minY;
        return new Rectanglei(minX, minY, minX + region.lengthX(), minY + region.lengthY());
    }

    /**
     * Helper class that wraps a line's start and end points.
     */
    public static class LineCoordinates {
        /**
         * The start point.
         */
        private Vector2i start;
        /**
         * The end point.
         */
        private Vector2i end;

        public LineCoordinates(Vector2i start, Vector2i end) {
            this.start = start;
            this.end = end;
        }

        public Vector2i getStart() {
            return this.start;
        }

        public Vector2i getEnd() {
            return this.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.start, this.end);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof LineCoordinates)) {
                return false;
            }
            if (o == this) {
                return true;
            }
            LineCoordinates other = (LineCoordinates) o;
            return this.start.equals(other.start) && this.end.equals(other.end);
        }

        @Override
        public String toString() {
            return String.format("[%s %s]", this.start, this.end);
        }
    }
}
