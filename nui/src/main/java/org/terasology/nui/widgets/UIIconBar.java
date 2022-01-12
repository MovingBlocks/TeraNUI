/*
 * Copyright 2014 MovingBlocks
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
package org.terasology.nui.widgets;

import org.terasology.joml.geom.Rectanglei;
import org.terasology.nui.util.NUIMathUtil;
import org.joml.Vector2i;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.Canvas;
import org.terasology.nui.CoreWidget;
import org.terasology.nui.LayoutConfig;
import org.terasology.nui.ScaleMode;
import org.terasology.nui.databinding.Binding;
import org.terasology.nui.databinding.DefaultBinding;
import org.terasology.nui.skin.UIStyle;
import org.terasology.nui.util.RectUtility;

/**
 * A value bar that uses Icons
 */
public class UIIconBar extends CoreWidget {

    private static final String ICON_PART = "icon";
    private int animationTimer = 0;
    private AnimationMode animationMode = AnimationMode.NONE;

    @LayoutConfig
    private HalfIconMode halfIconMode = HalfIconMode.SPLIT;

    @LayoutConfig
    private int maxIcons = 10;

    @LayoutConfig
    private int spacing = 1;

    @LayoutConfig
    private UITextureRegion icon;

    @LayoutConfig
    private Binding<Float> value = new DefaultBinding<>(0f);

    @LayoutConfig
    private Binding<Float> maxValue = new DefaultBinding<>(10f);

    @Override
    public void onDraw(Canvas canvas) {
        canvas.setPart(ICON_PART);
        if (icon != null && getMaxValue() > 0) {
            Vector2i iconSize = getIconSize(canvas);
            float ratio = maxIcons * getValue() / getMaxValue();
            int fullIcons = NUIMathUtil.floorToInt(ratio);
            boolean halfIcon = false;
            if (ratio - fullIcons >= 0.5f) {
                fullIcons++;
            } else if (ratio - fullIcons > 0) {
                halfIcon = true;
            }
            Vector2i barOffset = new Vector2i();


            for (int i = 0; i < maxIcons; ++i) {
                // Animations
                Vector2i iconOffset = new Vector2i();
                switch (animationMode) {
                    case SHAKING:
                        double waveY = Math.sin((animationTimer * 1.5f + i) / 2f) * 2;
                        barOffset.y = (int) waveY;
                        break;
                }

                Rectanglei iconRegion = RectUtility.createFromMinAndSize(barOffset, iconSize);
                canvas.drawBackground(iconRegion);
                if (ratio - i >= 0.5f) {
                    canvas.drawTexture(icon, iconRegion);
                } else if (ratio - i > 0f) {
                    switch (halfIconMode) {
                        case SHRINK:
                            Vector2i halfSize = new Vector2i(iconSize);
                            halfSize.x /= 2;
                            halfSize.y /= 2;
                            canvas.drawTexture(icon,
                                    RectUtility.createFromMinAndSize(new Vector2i(barOffset.x + iconOffset.x + halfSize.x / 2,
                                            barOffset.y + iconOffset.y + halfSize.y / 2), halfSize));
                            break;
                        case SPLIT:
                            canvas.drawTextureRaw(icon,
                                    RectUtility.createFromMinAndSize(new Vector2i(barOffset.x + iconOffset.x, barOffset.y + iconOffset.y), new Vector2i(iconSize.x / 2, iconSize.y)),
                                    ScaleMode.STRETCH, 0f, 0f, (float) (iconSize.x / 2) / iconSize.x, 1.0f);
                            break;
                        default:
                            canvas.drawTexture(icon, iconRegion);
                            break;
                    }
                }
                barOffset.x += iconSize.x + spacing;

                if (barOffset.x + iconSize.x > canvas.size().x) {
                    barOffset.x = 0;
                    barOffset.y += iconSize.y + spacing;
                }
            }
        }

        animationTimer++;
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        canvas.setPart(ICON_PART);
        if (icon != null) {
            Vector2i iconSize = getIconSize(canvas);
            int maxHorizontalIcons = sizeHint.x / iconSize.x;
            int rows = ((maxIcons - 1) / maxHorizontalIcons) + 1;
            int columns = Math.min(maxIcons, maxHorizontalIcons);
            return new Vector2i(columns * iconSize.x + (columns - 1) * spacing,
                    rows * iconSize.y + (rows - 1) * spacing);
        } else {
            return new Vector2i();
        }

    }

    private Vector2i getIconSize(Canvas canvas) {
        UIStyle iconStyle = canvas.getCurrentStyle();
        int width = iconStyle.getFixedWidth();
        int height = iconStyle.getFixedHeight();
        if (width == 0) {
            width = iconStyle.getMinWidth();
        }
        if (height == 0) {
            height = iconStyle.getMinHeight();
        }
        if (width == 0) {
            width = icon.getWidth();
        }
        if (height == 0) {
            height = icon.getHeight();
        }
        return new Vector2i(width, height);
    }

    /**
     * @return The icon used.
     */
    public UITextureRegion getIcon() {
        return icon;
    }

    /**
     * @param icon The icon to use in the bar.
     */
    public void setIcon(UITextureRegion icon) {
        this.icon = icon;
    }

    public void bindValue(Binding<Float> binding) {
        value = binding;
    }

    /**
     * @return The current value.
     */
    public float getValue() {
        return value.get();
    }

    /**
     * @param val The value to set it to.
     */
    public void setValue(float val) {
        value.set(val);
    }

    public void bindMaxValue(Binding<Float> binding) {
        maxValue = binding;
    }

    /**
     * @return The maximum value the bar can be set to.
     */
    public float getMaxValue() {
        return maxValue.get();
    }

    /**
     * @param val The new max value the bar can be set to.
     */
    public void setMaxValue(float val) {
        maxValue.set(val);
    }

    /**
     * @param newValue The new max value for the number of icons.
     */
    public void setMaxIcons(int newValue) {
        this.maxIcons = newValue;
    }

    /**
     * @return The current HalfIconMode used.
     */
    public HalfIconMode getHalfIconMode() {
        return halfIconMode;
    }

    /**
     * @param halfIconMode The new mode to use.
     */
    public void setHalfIconMode(HalfIconMode halfIconMode) {
        this.halfIconMode = halfIconMode;
    }

    /**
     * @param animationMode The new type of animation to use.
     */
    public void setAnimationMode(AnimationMode animationMode) {
        this.animationMode = animationMode;
    }

    public enum HalfIconMode {
        NONE,
        SPLIT,
        SHRINK,
    }
    public enum AnimationMode {
        NONE,
        SHAKING
    }
}
