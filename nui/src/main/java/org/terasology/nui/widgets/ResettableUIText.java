// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets;

import org.joml.Rectanglei;
import org.terasology.nui.BaseInteractionListener;
import org.terasology.nui.Canvas;
import org.terasology.nui.InteractionListener;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.events.NUIMouseClickEvent;
import org.terasology.nui.input.MouseInput;
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

    private final InteractionListener clearInteractionListener = new BaseInteractionListener() {
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
