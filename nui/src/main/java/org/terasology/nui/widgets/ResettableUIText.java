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
package org.terasology.nui.widgets;

import org.terasology.input.MouseInput;
import org.terasology.joml.geom.Rectanglei;
import org.terasology.nui.BaseInteractionListener;
import org.terasology.nui.Canvas;
import org.terasology.nui.InteractionListener;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.events.NUIMouseClickEvent;
import org.terasology.nui.util.RectUtility;

/**
 * A text widget with a button to clear the text.
 */
public class ResettableUIText extends UIText {
    public ResettableUIText() {
        super();
    }

    public ResettableUIText(UITextureRegion cursorTexture) {
        super(cursorTexture);
    }

    private InteractionListener clearInteractionListener = new BaseInteractionListener() {
        @Override
        public boolean onMouseClick(NUIMouseClickEvent event) {
            if (event.getMouseButton() == MouseInput.MOUSE_LEFT) {
                setText("");
                return true;
            }
            return false;
        }
    };

    @Override
    public void onDraw(Canvas canvas) {
        Rectanglei clearButtonRegion = RectUtility.createFromMinAndSize(0, 0, 30, canvas.size().y);
        lastWidth = canvas.size().x - clearButtonRegion.lengthX();
        if (isEnabled()) {
            canvas.addInteractionRegion(interactionListener, new Rectanglei(0, 0, canvas.size().x, canvas.size().y));
            canvas.addInteractionRegion(clearInteractionListener, new Rectanglei(canvas.size().x, 0, canvas.size().x +
                    clearButtonRegion.lengthX(), canvas.size().y));
        }
        drawAll(canvas, canvas.size().x - clearButtonRegion.lengthX());
    }
}
