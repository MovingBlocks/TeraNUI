package org.terasology.nui.util;

import org.joml.Rectanglef;
import org.joml.Rectanglei;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public final class RectUtility {

    private RectUtility() {
    }

    /**
     * Create a 2D axis-aligned rectangle at bottom-left anchor position with given size.
     * <p>
     * The result is guaranteed to be valid. If either width or height are negative an empty rectangle is returned.
     * If creating a rectangle of requested size would exceed the integer range the maximal rectangle that still fits
     * into the range is returned.
     *
     * @param minX   the x-coordinate of the bottom-left corner
     * @param minY   the y-coordinate of the bottom-left corner
     * @param width  the width (x-direction)
     * @param height the height (y-direction)
     * @return a 2D axis-aligned rectangle as specified, or an empty rectangle if either width or height are negative
     */
    public static Rectanglei createFromMinAndSize(int minX, int minY, int width, int height) {
        final int maxX = NUIMathUtil.addClampAtMax(minX, width);
        final int maxY = NUIMathUtil.addClampAtMax(minY, height);
        final Rectanglei rect = new Rectanglei(minX, minY, maxX, maxY);
        return rect.isValid() ? rect : new Rectanglei();
    }

    /**
     * Create a 2D axis-aligned rectangle at bottom-left anchor position with given size.
     * <p>
     * The result is guaranteed to be valid. If either width or height are negative an empty rectangle is returned.
     * If creating a rectangle of requested size would exceed the integer range the maximal rectangle that still fits
     * into the range is returned.
     *
     * @param min  the coordinates of the bottom-left corner
     * @param size the size of the rectangle
     * @return a 2D axis-aligned rectangle as specified, or an empty rectangle if either width or height are negative
     */
    public static Rectanglei createFromMinAndSize(Vector2i min, Vector2i size) {
        return createFromMinAndSize(min.x, min.y, size.x, size.y);
    }

    /**
     * Create a 2D axis-aligned rectangle at bottom-left anchor position with given size.
     * <p>
     * The result is guaranteed to be valid. If either width or height are negative an empty rectangle is returned.
     * If creating a rectangle of requested size would exceed the integer range the maximal rectangle that still fits
     * into the range is returned.
     *
     * @param minX   the x-coordinate of the bottom-left corner
     * @param minY   the y-coordinate of the bottom-left corner
     * @param width  the width (x-direction)
     * @param height the height (y-direction)
     * @return a 2D axis-aligned rectangle as specified, or an empty rectangle if either width or height are negative
     */
    public static Rectanglef createFromMinAndSize(float minX, float minY, float width, float height) {
        final float maxX = NUIMathUtil.addClampAtMax(minX, width);
        final float maxY = NUIMathUtil.addClampAtMax(minY, height);
        final Rectanglef rect = new Rectanglef(minX, minY, maxX, maxY);
        return rect.isValid() ? rect : new Rectanglef();
    }

    /**
     * Create a 2D axis-aligned rectangle at bottom-left anchor position with given size.
     * <p>
     * The result is guaranteed to be valid. If either width or height are negative an empty rectangle is returned.
     * If creating a rectangle of requested size would exceed the integer range the maximal rectangle that still fits
     * into the range is returned.
     *
     * @param min  the coordinates of the bottom-left corner
     * @param size the size of the rectangle
     * @return a 2D axis-aligned rectangle as specified, or an empty rectangle if either width or height are negative
     */
    public static Rectanglef createFromMinAndSize(Vector2f min, Vector2f size) {
        return createFromMinAndSize(min.x, min.y, size.x, size.y);
    }

    public static Rectanglef createFromCenterAndSize(Vector2f center, Vector2f size) {
        return createFromCenterAndSize(center.x, center.y, size.x, size.y);
    }

    public static Rectanglef createFromCenterAndSize(float centerX, float centerY, float width, float height) {
        return createFromMinAndSize(centerX - width * 0.5f, centerY - height * 0.5f, width, height);
    }

    public static boolean isEmpty(Rectanglei rect) {
        return rect.lengthX() == 0 || rect.lengthY() == 0;
    }

    public static boolean isEmpty(Rectanglef rect) {
        return rect.lengthX() == 0 || rect.lengthY() == 0;
    }

    public static boolean contains(Rectanglei rect, Vector2i point) {
        return point.x >= rect.minX && point.x < rect.maxX && point.y >= rect.minY && point.y < rect.maxY;
    }

    public static Rectanglei map(Rectanglei from, Rectanglei to, Rectanglei rect, Rectanglei dest) {
        dest.minX = (int) (to.minX + ((float) (rect.minX - from.minX) * ((float) to.lengthX() / (float) from.lengthX())));
        dest.maxX = (int) (to.minX + ((float) (rect.maxX - from.minX) * ((float) to.lengthX() / (float) from.lengthX())));
        dest.minY = (int) (to.minY + ((float) (rect.minY - from.minY) * ((float) to.lengthY() / (float) from.lengthY())));
        dest.maxY = (int) (to.minY + ((float) (rect.maxY - from.minY) * ((float) to.lengthY() / (float) from.lengthY())));
        return dest;
    }

    public static Vector2i map(Rectanglei from, Rectanglei to, Vector2ic point, Vector2i dest) {
        return dest.set(
            (int) (to.minX + ((float) (point.x() - from.minX) * ((float) to.lengthX() / (float) from.lengthX()))),
            (int) (to.minY + ((float) (point.y() - from.minY) * ((float) to.lengthY() / (float) from.lengthY()))));
    }

    public static Rectanglei expand(Rectanglei rect, Vector2i amount) {
        return expand(rect, amount.x, amount.y);
    }

    public static Rectanglei expand(Rectanglei rect, int dx, int dy) {
        int minX = rect.minX - dx;
        int minY = rect.minY - dy;
        int maxX = rect.maxX + dx;
        int maxY = rect.maxY + dy;
        return new Rectanglei(minX, minY, maxX, maxY);
    }

    public static Rectanglef expand(Rectanglef rect, Vector2f amount) {
        return expand(rect, amount.x, amount.y);
    }

    public static Rectanglef expand(Rectanglef rect, float dx, float dy) {
        float minX = rect.minX - dx;
        float minY = rect.minY - dy;
        float maxX = rect.maxX + dx;
        float maxY = rect.maxY + dy;
        return new Rectanglef(minX, minY, maxX, maxY);
    }
}
