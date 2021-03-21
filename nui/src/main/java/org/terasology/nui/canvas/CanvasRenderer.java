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
package org.terasology.nui.canvas;

import org.terasology.joml.geom.Rectanglei;
import org.joml.Vector2i;
import org.terasology.nui.Border;
import org.terasology.nui.Colorc;
import org.terasology.nui.HorizontalAlign;
import org.terasology.nui.ScaleMode;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.VerticalAlign;
import org.terasology.nui.asset.font.Font;

/**
 */
public interface CanvasRenderer {

    void preRender();

    void postRender();

    Vector2i getTargetSize();

    void crop(Rectanglei cropRegion);

    void drawLine(int sx, int sy, int ex, int ey, Colorc color);

    void drawTexture(UITextureRegion texture, Colorc color, ScaleMode mode, Rectanglei absoluteRegion, float ux, float uy, float uw, float uh, float alpha);

    void drawText(String text, Font font, HorizontalAlign hAlign, VerticalAlign vAlign, Rectanglei absoluteRegion, Colorc color,
                  Colorc shadowColor, float alpha, boolean underlined);

    void drawTextureBordered(UITextureRegion texture, Rectanglei absoluteRegion, Border border, boolean tile, float ux, float uy, float uw, float uh, float alpha);

    void setUiScale(float uiScale);
}
