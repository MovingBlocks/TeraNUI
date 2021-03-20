/*
 * Copyright 2020 MovingBlocks
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
package org.terasology.nui.backends.libgdx;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.terasology.gestalt.assets.AssetData;
import org.joml.Vector2i;
import org.terasology.nui.asset.font.Font;

import java.util.List;

public class LibGDXFont implements Font, AssetData {
    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    public LibGDXFont(BitmapFont font) {
        this.bitmapFont = font;
        glyphLayout = new GlyphLayout();
    }

    public BitmapFont getGdxFont() {
        return bitmapFont;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    @Override
    public int getWidth(String text) {
        glyphLayout.reset();
        glyphLayout.setText(bitmapFont, text);
        return (int) glyphLayout.width;
    }

    @Override
    public int getWidth(Character c) {
        glyphLayout.reset();
        glyphLayout.setText(bitmapFont, "" + c);
        return (int) glyphLayout.width;
    }

    @Override
    public int getHeight(String text) {
        glyphLayout.reset();
        glyphLayout.setText(bitmapFont, text);
        return (int) glyphLayout.height;
    }

    @Override
    public int getLineHeight() {
        return (int) bitmapFont.getLineHeight();
    }

    @Override
    public int getBaseHeight() {
        return (int) bitmapFont.getCapHeight();
    }

    @Override
    public Vector2i getSize(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
            builder.append(lines.get(lineNo));
            if (lineNo != lines.size() - 1) {
                builder.append('\n');
            }
        }

        glyphLayout.reset();
        glyphLayout.setText(bitmapFont, builder.toString());

        // NOTE: The line height is calculated like this in Terasology's implementation, so the size is larger than the actual height
        return new Vector2i((int) glyphLayout.width, getLineHeight() * lines.size());
    }

    @Override
    public boolean hasCharacter(Character c) {
        return bitmapFont.getData().getGlyph(c) != null;
    }

    @Override
    public int getUnderlineOffset() {
        // TODO: These constants appear to be used in Terasology, yet I could not find anywhere where they are modified
        return 2;
    }

    @Override
    public int getUnderlineThickness() {
        // TODO: These constants appear to be used in Terasology, yet I could not find anywhere where they are modified
        return 1;
    }
}
