package org.terasology.nui.util;

import org.joml.Rectanglef;
import org.joml.Rectanglei;
import org.joml.Vector2f;
import org.joml.Vector2i;

public final class RectUtility {

    private RectUtility() {
    }

    public static Rectanglei createFromMinAndSize(int minX, int minY, int width, int height) {
        final int maxX = NUIMathUtil.addClampAtMax(minX, width);
        final int maxY = NUIMathUtil.addClampAtMax(minY, height);
        final Rectanglei rect = new Rectanglei(minX, minY, maxX, maxY);
        return rect.isValid() ? rect : new Rectanglei();
    }

    public static Rectanglei createFromMinAndSize(Vector2i min, Vector2i size) {
        return createFromMinAndSize(min.x, min.y, size.x, size.y);
    }

    public static Rectanglef createFromMinAndSize(float minX, float minY, float width, float height) {
        final float maxX = NUIMathUtil.addClampAtMax(minX, width);
        final float maxY = NUIMathUtil.addClampAtMax(minY, height);
        final Rectanglef rect = new Rectanglef(minX, minY, maxX, maxY);
        return rect.isValid() ? rect : new Rectanglef();
    }

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
