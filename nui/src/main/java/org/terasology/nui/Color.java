/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.nui;

import com.google.common.base.Preconditions;
import org.joml.Math;
import org.joml.Vector3fc;
import org.joml.Vector3ic;
import org.joml.Vector4fc;
import org.joml.Vector4ic;
import org.terasology.module.sandbox.API;

import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Objects;

/**
 * Color is a representation of a RGBA color. Color components can be set and accessed via floats ranging from 0-1, or ints ranging from 0-255.
 * Color is immutable and thread safe.
 * <br><br>
 * There are a plethora of Color classes, but none that are quite suitable IMO:
 * <ul>
 * <li>vecmaths - doesn't access with r/g/b/a, separation by representation is awkward, feature bland.</li>
 * <li>Slick2D - ideally will lose dependency on slick utils. Also ties to lwjgl</li>
 * <li>Lwjgl - don't want to be graphics implementation dependant</li>
 * <li>javafx - ew</li>
 * <li>com.sun.prism - double ew. Shouldn't use com.sun classes at all</li>
 * <li>awt - tempting, certainly feature-rich. Has some strange awt-specific functionality though (createContext) and native links</li>
 * </ul>
 *
 */
@API
public class Color implements Colorc{

    @Deprecated
    public static final Color BLACK = new Color(0x000000FF);
    @Deprecated
    public static final Color WHITE = new Color(0xFFFFFFFF);
    @Deprecated
    public static final Color BLUE = new Color(0x0000FFFF);
    @Deprecated
    public static final Color GREEN = new Color(0x00FF00FF);
    @Deprecated
    public static final Color RED = new Color(0xFF0000FF);
    @Deprecated
    public static final Color GREY = new Color(0x888888FF);
    @Deprecated
    public static final Color TRANSPARENT = new Color(0x00000000);
    @Deprecated
    public static final Color YELLOW = new Color(0xFFFF00FF);
    @Deprecated
    public static final Color CYAN = new Color(0x00FFFFFF);
    @Deprecated
    public static final Color MAGENTA = new Color(0xFF00FFFF);

    public static final Colorc black = new Color(0x000000FF);
    public static final Colorc white = new Color(0xFFFFFFFF);
    public static final Colorc blue = new Color(0x0000FFFF);
    public static final Colorc green = new Color(0x00FF00FF);
    public static final Colorc red = new Color(0xFF0000FF);
    public static final Colorc grey = new Color(0x888888FF);
    public static final Colorc transparent = new Color(0x00000000);
    public static final Colorc yellow = new Color(0xFFFF00FF);
    public static final Colorc cyan = new Color(0x00FFFFFF);
    public static final Colorc magenta = new Color(0xFF00FFFF);


    private static final int MAX = 255;
    private static final int RED_OFFSET = 24;
    private static final int GREEN_OFFSET = 16;
    private static final int BLUE_OFFSET = 8;
    private static final int RED_FILTER = 0x00FFFFFF;
    private static final int GREEN_FILTER = 0xFF00FFFF;
    private static final int BLUE_FILTER = 0xFFFF00FF;
    private static final int ALPHA_FILTER = 0xFFFFFF00;

    private int representation;

    /**
     * Creates a color that is black with full alpha.
     */
    public Color() {
        representation = 0x000000FF;
    }

    /**
     * range between 0x00000000 to 0xFFFFFFFF
     * @param representation color in hex format
     */
    public Color(int representation) {
        this.representation = representation;
    }

    /**
     * set the color source
     * @param src color source
     */
    public Color(Colorc src) {
        this.set(src.rgba());
    }

    /**
     * Create a color with the given red/green/blue values. Alpha is initialised as max.
     *
     * @param r red in the range of 0.0f to 1.0f
     * @param g green in the range of 0.0f to 1.0f
     * @param b blue in the range of 0.0f to 1.0f
     */
    public Color(float r, float g, float b) {
        this((byte) (r * MAX), (byte) (g * MAX), (byte) (b * MAX));
    }

    /**
     * Creates a color with the given red/green/blue/alpha values.
     *
     * @param r red in the range of 0.0f to 1.0f
     * @param g green in the range of 0.0f to 1.0f
     * @param b blue in the range of 0.0f to 1.0f
     * @param a alpha in the range of 0.0f to 1.0f
     */
    public Color(float r, float g, float b, float a) {
        this((byte) (r * MAX), (byte) (g * MAX), (byte) (b * MAX), (byte) (a * MAX));
    }

    /**
     * Creates a color with the given red/green/blue values. Alpha is initialised as max.
     *
     * @param r red in the range of 0.0f to 1.0f
     * @param g green in the range of 0.0f to 1.0f
     * @param b blue in the range of 0.0f to 1.0f
     */
    public Color(int r, int g, int b) {
        this.set(r, g, b);
    }

    /**
     * Creates a color with the given red/green/blue/alpha values.
     *
     * @param r red in the range of 0 to 255
     * @param g green in the range of 0 to 255
     * @param b blue in the range of 0 to 255
     * @param a alpha in the range of 0 to 255
     */
    public Color(int r, int g, int b, int a) {
        this.set(r, g, b, a);
    }

    @Override
    public int r() {
        return (representation >> RED_OFFSET) & MAX;
    }

    @Override
    public int g() {
        return (representation >> GREEN_OFFSET) & MAX;
    }

    @Override
    public int b() {
        return (representation >> BLUE_OFFSET) & MAX;
    }

    @Override
    public int a() {
        return representation & MAX;
    }

    @Override
    public float rf() {
        return r() / 255.f;
    }

    @Override
    public float bf() {
        return b() / 255.f;
    }

    @Override
    public float gf() {
        return g() / 255.f;
    }

    @Override
    public float af() {
        return a() / 255.f;
    }


    public Color set(Vector3ic representation) {
        return this.set((byte) representation.x(),
            (byte) representation.y(),
            (byte) representation.z());
    }

    public Color set(Vector3fc representation) {
        return this.set((byte) (representation.x() * MAX),
            (byte) (representation.y() * MAX),
            (byte) (representation.z() * MAX));
    }


    public Color set(Vector4fc representation) {
        return this.set((byte) (representation.x() * MAX),
            (byte) (representation.y() * MAX),
            (byte) (representation.z() * MAX),
            (byte) (representation.w() * MAX));
    }

    public Color set(Vector4ic representation) {
        return this.set((byte) representation.x(),
            (byte) representation.y(),
            (byte) representation.z(),
            (byte) representation.w());
    }

    public Color set(int representation){
        this.representation = representation;
        return this;
    }

    public Color set(int r, int g, int b, int a) {
        return this.set(Math.clamp(0, 255, r) << RED_OFFSET |
            Math.clamp(0, 255, g) << GREEN_OFFSET |
            Math.clamp(0, 255, b) << BLUE_OFFSET |
            Math.clamp(0, 255, a));
    }


    public Color set(int r, int g, int b) {
        return this.set(r, g, b, 0xFF);
    }


    /**
     * set the value of the red channel
     * @param value color range between 0-255
     * @return this
     */
    public Color setRed(int value) {
        return this.set(Math.clamp(0, 255, value) << RED_OFFSET | (representation & RED_FILTER));
    }

    /**
     * set the value of the red channel
     * @param value color range between 0.0f to 1.0f
     * @return this
     */
    public Color setRed(float value) {
        return setRed(value * MAX);
    }

    /**
     * set the value of the green channel
     * @param value color range between 0-255
     * @return this
     */
    public Color setGreen(int value) {
        return this.set(Math.clamp(0, 255, value) << GREEN_OFFSET | (representation & GREEN_FILTER));
    }


    /**
     * set the value of the green channel
     * @param value color range between 0.0f to 1.0f
     * @return this
     */
    public Color setGreen(float value) {
        return setGreen(value * MAX);
    }


    /**
     * set the value of the blue channel
     * @param value blue range between 0-255
     * @return this
     */
    public Color setBlue(int value) {
        return this.set(Math.clamp(0, 255, value) << BLUE_OFFSET | (representation & BLUE_FILTER));
    }

    /**
     * set the value of the blue channel
     * @param value blue range between 0.0f to 1.0f
     * @return this
     */
    public Color setBlue(float value) {
        return setBlue(value * MAX);
    }

    /**
     * set the value of the alpha channel
     * @param value alpha range between 0-255
     * @return this
     */
    public Color setAlpha(int value) {
        return this.set(Math.clamp(0, 255, value) | (representation & ALPHA_FILTER));
    }

    /**
     * set the value of the alpha channel
     * @param value alpha range between 0.0f to 1.0f
     * @return this
     */
    public Color setAlpha(float value) {
        return setAlpha(value * MAX);
    }


    /**
     *
     * @param value
     * @return
     * @deprecated use {@link #setRed(int)} instead
     */
    @Deprecated
    public Color alterRed(int value) {
        Preconditions.checkArgument(value >= 0 && value <= MAX, "Color values must be in range 0-255");
        return new Color(value << RED_OFFSET | (representation & RED_FILTER));
    }

    /**
     *
     * @param value
     * @return
     * @deprecated use {@link #setBlue(int)} instead
     */
    @Deprecated
    public Color alterBlue(int value) {
        Preconditions.checkArgument(value >= 0 && value <= MAX, "Color values must be in range 0-255");
        return new Color(value << BLUE_OFFSET | (representation & BLUE_FILTER));
    }

    /**
     *
     * @param value
     * @return
     * @deprecated use {@link #setBlue(int)} instead
     */
    @Deprecated
    public Color alterGreen(int value) {
        Preconditions.checkArgument(value >= 0 && value <= MAX, "Color values must be in range 0-255");
        return new Color(value << GREEN_OFFSET | (representation & GREEN_FILTER));
    }

    /**
     * @param value
     * @return
     * @deprecated use {@link #setAlpha(int)} instead
     */
    @Deprecated
    public Color alterAlpha(int value) {
        Preconditions.checkArgument(value >= 0 && value <= MAX, "Color values must be in range 0-255");
        return new Color(value | (representation & ALPHA_FILTER));
    }

    /**
     * 255 Subtract from all components except alpha
     * @return new instance of inverted {@link Color}
     * @deprecated use {@link #invert()} instead
     */
    @Deprecated
    public Color inverse() {
        return new Color((~representation & ALPHA_FILTER) | a());
    }

    /**
     * 255 Subtract from all components except alpha;
     * @return this
     */
    public Color invert() {
        return this.set((~representation & ALPHA_FILTER) | a());
    }

    @Override
    public int rgba() {
        return representation;
    }

    @Override
    public int rgb() {
        return (representation & ALPHA_FILTER) | 0xFF;
    }

    /**
     * write color to ByteBuffer as int.
     *
     * @param buffer The ByteBuffer
     */
    public void addToBuffer(ByteBuffer buffer) {
        buffer.putInt(representation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Color) {
            Color other = (Color) obj;
            return representation == other.representation;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(representation);
    }

    @Override
    public String toHex() {
        StringBuilder builder = new StringBuilder();
        String hexString = Integer.toHexString(representation);
        for (int i = 0; i < 8 - hexString.length(); ++i) {
            builder.append('0');
        }
        builder.append(hexString.toUpperCase(Locale.ENGLISH));
        return builder.toString();
    }

    @Override
    public String toString() {
        return toHex();
    }

    /**
     * @deprecated  use {@link #rgba()} instead
     */
    @Deprecated
    public int getRepresentation() {
        return representation;
    }
}
