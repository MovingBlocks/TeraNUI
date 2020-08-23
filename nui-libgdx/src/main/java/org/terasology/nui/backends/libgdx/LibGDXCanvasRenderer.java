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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.terasology.nui.UIMaterial;
import org.terasology.nui.UIMesh;
import org.terasology.nui.util.NUIMathUtil;
import org.terasology.nui.Border;
import org.joml.Rectanglef;
import org.joml.Rectanglei;
import org.joml.Vector2i;
import org.terasology.nui.Color;
import org.terasology.nui.FontColor;
import org.terasology.nui.HorizontalAlign;
import org.terasology.nui.ScaleMode;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.VerticalAlign;
import org.terasology.nui.asset.font.Font;
import org.terasology.nui.canvas.CanvasRenderer;
import org.terasology.nui.util.RectUtility;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LibGDXCanvasRenderer implements CanvasRenderer {
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private int cropCount = 0;
    private int screenWidth;
    private int screenHeight;
    private int scaledWidth;
    private int scaledHeight;
    private float uiScale;
    private boolean controlSpriteBatch;
    private boolean controlShapeRenderer;
    private Matrix4 lastMatrix;
    private Matrix4 lastTransformMatrix;
    private StretchViewport viewport;

    // NOTE: These constants were taken from Terasology's FontMeshBuilder class
    private static final int SHADOW_HORIZONTAL_OFFSET = 1;
    private static final int SHADOW_VERTICAL_OFFSET = 1;

    public LibGDXCanvasRenderer() {
        this(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new SpriteBatch(), new ShapeRenderer(), true, true);
    }

    public LibGDXCanvasRenderer(SpriteBatch spriteBatch) {
        this(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), spriteBatch, new ShapeRenderer(), false, true);
    }

    public LibGDXCanvasRenderer(int width, int height) {
        this(width, height, new SpriteBatch(), new ShapeRenderer(), true, true);
    }

    public LibGDXCanvasRenderer(int width, int height, SpriteBatch spriteBatch) {
        this(width, height, spriteBatch, new ShapeRenderer(), false, true);
    }

    public LibGDXCanvasRenderer(int width, int height, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this(width, height, spriteBatch, shapeRenderer, false, false);
    }

    public LibGDXCanvasRenderer(int width, int height, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, boolean controlSpriteBatch, boolean controlShapeRenderer) {
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        this.controlSpriteBatch = controlSpriteBatch;
        this.controlShapeRenderer = controlShapeRenderer;
        this.uiScale = 1.0f;
        viewport = new StretchViewport(width * uiScale, height * uiScale);

        resize(width, height);
    }

    public void resize(int width, int height) {
        screenWidth = width;
        scaledWidth = (int) (screenWidth * uiScale);
        screenHeight = height;
        scaledHeight = (int) (screenHeight * uiScale);

        viewport.update(width, height);
        viewport.setWorldSize(scaledWidth, scaledHeight);
        Gdx.gl.glViewport(0, 0, width, height);

        if (controlSpriteBatch) {
            viewport.apply(true);
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        }
    }

    @Override
    public void preRender() {
        for (int cropNo = 0; cropNo < cropCount; cropNo++) {
            ScissorStack.popScissors();
        }
        cropCount = 0;

        viewport.apply(true);
        if (controlSpriteBatch) {
            spriteBatch.begin();
        } else {
            lastMatrix = spriteBatch.getProjectionMatrix().cpy();
            lastTransformMatrix = spriteBatch.getTransformMatrix().cpy();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        }
    }

    @Override
    public void postRender() {
        spriteBatch.flush();
        if (controlSpriteBatch) {
            spriteBatch.end();
        } else {
            Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
            spriteBatch.setProjectionMatrix(lastMatrix);
            spriteBatch.setTransformMatrix(lastTransformMatrix);
        }
    }

    @Override
    public Vector2i getTargetSize() {
        return new Vector2i(screenWidth, screenHeight);
    }

    private Vector2i getScaledTargetSize() {
        return new Vector2i(scaledWidth, scaledHeight);
    }

    @Override
    public void crop(Rectanglei cropRegion) {
        if (cropRegion.equals(RectUtility.createFromMinAndSize(new Vector2i(), getScaledTargetSize()))) {
            spriteBatch.flush();
            for (int cropNo = 0; cropNo < cropCount; cropNo++) {
                ScissorStack.popScissors();
            }
            cropCount = 0;
            return;
        }

        spriteBatch.flush();
        if (ScissorStack.pushScissors(new Rectangle(cropRegion.minX / uiScale, screenHeight - (cropRegion.maxY / uiScale),
                cropRegion.lengthX() / uiScale, cropRegion.lengthY() / uiScale))) {
            cropCount++;
        } else {
            // TODO: Error Handling
            return;
        }
    }

    @Override
    public void drawMesh(UIMesh mesh, UIMaterial material, Rectanglei drawRegion, Rectanglei cropRegion, Quaternionfc rotation, Vector3fc offset, float scale, float alpha) {
        //TODO: implement
    }

    @Override
    public void drawLine(int sx, int sy, int ex, int ey, Color color) {
        spriteBatch.flush();
        spriteBatch.end();

        Matrix4 lastMatrix = shapeRenderer.getProjectionMatrix().cpy();
        if (controlShapeRenderer) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.setColor(GdxColorUtil.terasologyToGDXColor(color));
        // NOTE: The constant line width 2 is used in Terasology's rendering code (which NUI's implementation relied on)
        shapeRenderer.rectLine(sx, scaledHeight - sy, ex, scaledHeight - ey, 2);

        shapeRenderer.flush();
        if (controlShapeRenderer) {
            shapeRenderer.end();
        } else {
            shapeRenderer.setProjectionMatrix(lastMatrix);
        }

        spriteBatch.begin();
    }

    @Override
    public void drawTexture(UITextureRegion texture, Color color, ScaleMode mode, Rectanglei absoluteRegion, float ux, float uy, float uw, float uh, float alpha) {
        if (!(texture instanceof LibGDXTexture)) {
            // TODO: Wrong rendering back-end ?
            return;
        }

        LibGDXTexture gdxTexture = (LibGDXTexture) texture;

        spriteBatch.setColor(new com.badlogic.gdx.graphics.Color(color.rf(), color.gf(), color.bf(), color.af() * alpha));

        Texture.TextureWrap previousUWrap = gdxTexture.getGdxTexture().getTexture().getUWrap();
        Texture.TextureWrap previousVWrap = gdxTexture.getGdxTexture().getTexture().getVWrap();

        switch (mode) {
            case STRETCH:
                Vector2 stretchRegion = Scaling.stretch.apply(uw * texture.getWidth(), uh * texture.getHeight(), absoluteRegion.lengthX(), absoluteRegion.lengthY());
                absoluteRegion = RectUtility.createFromMinAndSize(absoluteRegion.minX, absoluteRegion.minY, (int)stretchRegion.x, (int)stretchRegion.y);
                break;
            case TILED:
                gdxTexture.getGdxTexture().getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                break;
            case SCALE_FIT:
                Vector2 fitRegion = Scaling.fit.apply(uw * texture.getWidth(), uh * texture.getHeight(), absoluteRegion.lengthX(), absoluteRegion.lengthY());
                absoluteRegion = RectUtility.createFromMinAndSize(absoluteRegion.minX, absoluteRegion.minY, (int)fitRegion.x, (int)fitRegion.y);
                break;
            case SCALE_FILL:
                Vector2 fillRegion = Scaling.fill.apply(uw * texture.getWidth(), uh * texture.getHeight(), absoluteRegion.lengthX(), absoluteRegion.lengthY());
                absoluteRegion = RectUtility.createFromMinAndSize(absoluteRegion.minX, absoluteRegion.minY, (int)fillRegion.x, (int)fillRegion.y);
                break;
        }

        Rectanglef textureOffset = texture.getRegion();
        spriteBatch.draw(gdxTexture.getGdxTexture().getTexture(), absoluteRegion.minX,
                scaledHeight - absoluteRegion.minY - absoluteRegion.lengthY(),
                absoluteRegion.lengthX(), absoluteRegion.lengthY(),
                (int) (ux * gdxTexture.getWidth()), (int) (uy * gdxTexture.getHeight()),
                (int) (textureOffset.minX + uw * gdxTexture.getWidth()), (int) (textureOffset.minY + uh * gdxTexture.getHeight()), false, false);

        gdxTexture.getGdxTexture().getTexture().setWrap(previousUWrap, previousVWrap);
    }

    @Override
    public void drawText(String text, Font font, HorizontalAlign hAlign, VerticalAlign vAlign, Rectanglei absoluteRegion, Color color, Color shadowColor, float alpha, boolean underlined) {
        if (!(font instanceof LibGDXFont)) {
            return;
        }

        int gdxAlignment = 0;
        switch (hAlign) {
            case LEFT:
                gdxAlignment = Align.left;
                break;
            case RIGHT:
                gdxAlignment = Align.right;
                break;
            case CENTER:
                gdxAlignment = Align.center;
                break;
        }

        switch (vAlign) {
            case TOP:
                gdxAlignment |= Align.top;
                break;
            case MIDDLE:
                gdxAlignment |= Align.center;
                break;
            case BOTTOM:
                gdxAlignment |= Align.bottom;
                break;
        }

        LibGDXFont gdxFont = (LibGDXFont) font;

        // Shadow drawing
        gdxFont.getGlyphLayout().setText(gdxFont.getGdxFont(), text,
                new com.badlogic.gdx.graphics.Color(shadowColor.rf(), shadowColor.gf(), shadowColor.bf(), shadowColor.af() * alpha), absoluteRegion.lengthX(),
                gdxAlignment, true);
        gdxFont.getGdxFont().draw(spriteBatch, gdxFont.getGlyphLayout(),
                absoluteRegion.minX - SHADOW_HORIZONTAL_OFFSET,
                scaledHeight - absoluteRegion.minY - SHADOW_VERTICAL_OFFSET
                        - vAlign.getOffset(Math.abs((int)gdxFont.getGlyphLayout().height), absoluteRegion.lengthY()));

        Deque<Color> colorStack = new LinkedList<Color>();
        colorStack.push(color);

        List<Map.Entry<String, Color>> textSegments = new ArrayList<>();
        StringBuilder currentSegment = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (FontColor.isValid(character)) {
                if (character != FontColor.getReset()) {
                    colorStack.push(FontColor.toColor(character));
                } else if (currentSegment.length() != 0) {
                    textSegments.add(new AbstractMap.SimpleEntry<>(currentSegment.toString(), colorStack.pop()));
                    currentSegment.delete(0, currentSegment.length());
                }
            } else {
                currentSegment.append(character);
            }
        }

        if (currentSegment.length() != 0) {
            textSegments.add(new AbstractMap.SimpleEntry<>(currentSegment.toString(), colorStack.pop()));
        }

        int textX = absoluteRegion.minX;
        int textY = scaledHeight - absoluteRegion.minY
                - vAlign.getOffset(Math.abs((int)gdxFont.getGlyphLayout().height), absoluteRegion.lengthY());

        int lineHeight = Math.abs(gdxFont.getLineHeight());

        for (Map.Entry<String, Color> textSegment : textSegments) {
            String contents = FontColor.stripColor(textSegment.getKey());
            Color segmentColor = textSegment.getValue();

            if (contents.startsWith("\n")) {
                textX = absoluteRegion.minX;
                textY += lineHeight;
            }

            // Standard drawing
            gdxFont.getGlyphLayout().setText(gdxFont.getGdxFont(), contents,
                    new com.badlogic.gdx.graphics.Color(segmentColor.rf(), segmentColor.gf(), segmentColor.bf(), segmentColor.af() * alpha), absoluteRegion.lengthX(),
                    gdxAlignment, true);
            gdxFont.getGdxFont().draw(spriteBatch, gdxFont.getGlyphLayout(), textX, textY);

            textX += gdxFont.getGlyphLayout().width;

            int textHeight = (int) Math.abs(gdxFont.getGlyphLayout().height);
            textY -= Math.max(textHeight, lineHeight);

            if (contents.endsWith("\n")) {
                textX = absoluteRegion.minX;
            }
        }
    }

    @Override
    public void drawTextureBordered(UITextureRegion texture, Rectanglei absoluteRegion, Border border, boolean tile, float ux, float uy, float uw, float uh, float alpha) {
        // See https://github.com/Terasology/TutorialNui/wiki/Skinning#background-options for border rendering information

        Vector2i textureSize = new Vector2i(NUIMathUtil.ceilToInt(texture.getWidth() * uw), NUIMathUtil.ceilToInt(texture.getHeight() * uh));
        // Draw texture without borders
        drawTexture(texture, Color.WHITE, tile ? ScaleMode.TILED : ScaleMode.STRETCH, absoluteRegion,
                ux + (float)border.getLeft() / textureSize.x, uy + (float)border.getTop() / textureSize.y,
                uw - (float)border.getTotalWidth() / textureSize.x,
                uh - (float)border.getTotalHeight() / textureSize.y, alpha);

        // Draw borders around texture

        // Left border
        drawTexture(texture, Color.WHITE, tile ? ScaleMode.TILED : ScaleMode.STRETCH, RectUtility.createFromMinAndSize(absoluteRegion.minX, absoluteRegion.minY, border.getLeft(), absoluteRegion.lengthY()),
                ux, uy,(float)border.getLeft() / textureSize.x, uh, alpha);

        // Right border
        drawTexture(texture, Color.WHITE, tile ? ScaleMode.TILED : ScaleMode.STRETCH, RectUtility.createFromMinAndSize(absoluteRegion.maxX - border.getRight(), absoluteRegion.minY, border.getRight(), absoluteRegion.lengthY()),
                ux + uw - ((float)border.getRight() / textureSize.x), uy,
                (float)border.getRight() / textureSize.x,
                uh, alpha);

        // Top border
        drawTexture(texture, Color.WHITE, tile ? ScaleMode.TILED : ScaleMode.STRETCH, RectUtility.createFromMinAndSize(absoluteRegion.minX, absoluteRegion.minY, absoluteRegion.lengthX(), border.getTop()),
                ux, uy,
                uw,
                (float)border.getTop() / textureSize.y, alpha);

        // Bottom border
        drawTexture(texture, Color.WHITE, tile ? ScaleMode.TILED : ScaleMode.STRETCH, RectUtility.createFromMinAndSize(absoluteRegion.minX, absoluteRegion.maxY - border.getBottom(), absoluteRegion.lengthX(), border.getBottom()),
                ux, uy + uh - ((float)border.getBottom() / textureSize.y),
                uw,
                (float)border.getBottom() / textureSize.y, alpha);
    }

    @Override
    public void setUiScale(float uiScale) {
        this.uiScale = uiScale;
        GDXInputUtil.setUiScale(uiScale);
        resize(screenWidth, screenHeight);
    }
}
