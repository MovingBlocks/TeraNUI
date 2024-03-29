/*
 * Copyright 2016 MovingBlocks
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
package org.terasology.nui.itemRendering;

import org.terasology.joml.geom.Rectanglei;
import org.joml.Vector2i;
import org.terasology.nui.asset.font.Font;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.Canvas;
import org.terasology.nui.TextLineBuilder;
import org.terasology.nui.util.RectUtility;

import java.util.List;

/**
 * This ItemRenderer displays text preceded by an icon texture based on a given string.
 */

public abstract class StringTextIconRenderer<T> extends AbstractItemRenderer<T> {

    private final int marginTop;
    private final int marginBottom;
    private final int marginLeft;
    private final int marginRight;

    protected StringTextIconRenderer() {
        this(5, 5, 5, 10);
    }

    protected StringTextIconRenderer(int marginTop, int marginBottom, int marginLeft, int marginRight) {
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
    }

    @Override
    public void draw(T value, Canvas canvas) {
        // Drawing the icon
        UITextureRegion texture = getTexture(value);

        if (texture != null) {
            if (marginTop + texture.getHeight() + marginBottom > canvas.size().y) {
                // Icon does not fit within the canvas - vertically shrinking it
                int iconHeight = canvas.size().y - marginTop - marginBottom;
                canvas.drawTexture(texture, RectUtility.createFromMinAndSize(marginLeft, marginTop, texture.getWidth(), iconHeight));
            } else {
                // Icon fits within the canvas - vertically centering it
                int iconVerticalPosition = (canvas.size().y - texture.getHeight()) / 2;
                canvas.drawTexture(texture, RectUtility.createFromMinAndSize(marginLeft, iconVerticalPosition, texture.getWidth(), texture.getHeight()));
            }
        }

        // Drawing the text, adjusting for icon width
        String text = getString(value);

        int iconWidth;
        if (texture != null) {
            iconWidth = marginLeft + texture.getWidth() + marginRight;
        } else {
            iconWidth = 0;
        }

        Rectanglei textRegion = RectUtility.createFromMinAndSize(iconWidth, 0, canvas.getRegion().lengthX() - iconWidth, canvas.getRegion().lengthY());
        canvas.drawText(text, textRegion);
    }

    @Override
    public Vector2i getPreferredSize(T value, Canvas canvas) {
        Font font = canvas.getCurrentStyle().getFont();
        String text = getString(value);

        UITextureRegion texture = getTexture(value);
        if (texture == null) {
            List<String> lines = TextLineBuilder.getLines(font, text, canvas.size().x);
            return font.getSize(lines);
        } else {
            int iconWidth = marginLeft + texture.getWidth() + marginRight;
            List<String> lines = TextLineBuilder.getLines(font, text, canvas.size().x - iconWidth);
            return font.getSize(lines).add(iconWidth, 0);
        }
    }

    public abstract String getString(T value);

    public abstract UITextureRegion getTexture(T value);
}
