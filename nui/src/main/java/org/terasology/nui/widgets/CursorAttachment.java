// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.widgets;

import org.joml.Vector2i;
import org.terasology.nui.BaseInteractionListener;
import org.terasology.nui.Canvas;
import org.terasology.nui.CoreWidget;
import org.terasology.nui.InteractionListener;
import org.terasology.nui.LayoutConfig;
import org.terasology.nui.SubRegion;
import org.terasology.nui.UIWidget;
import org.terasology.nui.events.NUIMouseOverEvent;
import org.terasology.nui.input.device.MouseDevice;
import org.terasology.nui.skin.UIStyle;
import org.terasology.nui.util.NUIMathUtil;
import org.terasology.nui.util.RectUtility;

/**
 */
public class CursorAttachment extends CoreWidget {
    private static final int MOUSE_CURSOR_HEIGHT = 18;

    @LayoutConfig
    private UIWidget attachment;

    private final InteractionListener interactionListener = new BaseInteractionListener() {
        @Override
        public void onMouseOver(NUIMouseOverEvent event) {
            mouse = event.getMouse();
        }

        @Override
        public boolean isMouseOver() {
            // Do not block input from other elements
            return false;
        }
    };
    private MouseDevice mouse;

    public UIWidget getAttachment() {
        return attachment;
    }

    public void setAttachment(UIWidget attachment) {
        this.attachment = attachment;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isEnabled() && mouse == null) {
            canvas.addInteractionRegion(interactionListener, canvas.getRegion());
        }

        if (mouse == null) {
            return;
        }

        UIStyle style = canvas.getCurrentStyle();
        Vector2i attachmentSize = canvas.calculatePreferredSize(attachment);
        attachmentSize.add(style.getMargin().getTotals());

        int top;
        switch (style.getVerticalAlignment()) {
            case TOP:
                top = mouse.getPosition().y - attachmentSize.y;
                break;
            case MIDDLE:
                top = mouse.getPosition().y - attachmentSize.y / 2;
                break;
            default:
                top = mouse.getPosition().y + MOUSE_CURSOR_HEIGHT;
                break;
        }
        top = NUIMathUtil.clamp(top, 0, canvas.size().y - attachmentSize.y);
        int left;
        switch (style.getHorizontalAlignment()) {
            case RIGHT:
                left = mouse.getPosition().x - attachmentSize.x;
                break;
            case CENTER:
                left = mouse.getPosition().x - attachmentSize.x / 2;
                break;
            default:
                left = mouse.getPosition().x;
                break;
        }
        left = NUIMathUtil.clamp(left, 0, canvas.size().x - attachmentSize.x);


        try (SubRegion ignored = canvas.subRegion(RectUtility.createFromMinAndSize(left, top, attachmentSize.x, attachmentSize.y), false)) {
            canvas.drawBackground();
            canvas.drawWidget(attachment, style.getBackgroundBorder().shrink(canvas.getRegion()));
        }
    }

    @Override
    public boolean isSkinAppliedByCanvas() {
        return false;
    }

    @Override
    public Vector2i getPreferredContentSize(Canvas canvas, Vector2i sizeHint) {
        return canvas.calculateRestrictedSize(attachment, sizeHint);
    }

    @Override
    public boolean isVisible() {
        // TODO: onDraw is never called unless isVisible returns true, so there is no reliable method of determining visibility for the moment.
        return super.isVisible() && (mouse == null || mouse.isVisible()) && getAttachment() != null && getAttachment().isVisible();
    }
}
