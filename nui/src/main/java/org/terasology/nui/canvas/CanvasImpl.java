// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.nui.canvas;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.joml.Rectanglei;
import org.joml.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.nui.BaseInteractionListener;
import org.terasology.nui.Border;
import org.terasology.nui.Color;
import org.terasology.nui.Colorc;
import org.terasology.nui.FocusManager;
import org.terasology.nui.HorizontalAlign;
import org.terasology.nui.InteractionListener;
import org.terasology.nui.ScaleMode;
import org.terasology.nui.SubRegion;
import org.terasology.nui.TabbingManager;
import org.terasology.nui.UITextureRegion;
import org.terasology.nui.UIWidget;
import org.terasology.nui.VerticalAlign;
import org.terasology.nui.asset.font.Font;
import org.terasology.nui.events.NUIMouseClickEvent;
import org.terasology.nui.events.NUIMouseDoubleClickEvent;
import org.terasology.nui.events.NUIMouseDragEvent;
import org.terasology.nui.events.NUIMouseOverEvent;
import org.terasology.nui.events.NUIMouseReleaseEvent;
import org.terasology.nui.events.NUIMouseWheelEvent;
import org.terasology.nui.input.MouseInput;
import org.terasology.nui.input.device.KeyboardDevice;
import org.terasology.nui.input.device.MouseDevice;
import org.terasology.nui.skin.UISkin;
import org.terasology.nui.skin.UIStyle;
import org.terasology.nui.util.NUIMathUtil;
import org.terasology.nui.util.RectUtility;
import org.terasology.nui.widgets.UILabel;
import org.terasology.nui.widgets.UITooltip;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 */
public class CanvasImpl implements CanvasControl {

    private static final Logger logger = LoggerFactory.getLogger(CanvasImpl.class);

    /**
     * The maximum distance the cursor can move between clicks and still be counted as double clicking
     */
    protected static final int MAX_DOUBLE_CLICK_DISTANCE = 5;
    /**
     * The maximum time (in milliseconds) between clicks that will still be counted as double clicking
     */
    protected static final int DOUBLE_CLICK_TIME = 200;

    /**
     * A sufficiently large value for "unbounded" regions, without risking overflow.
     */
    protected static final int LARGE_INT = Integer.MAX_VALUE / 2;

    protected final KeyboardDevice keyboard;
    protected final MouseDevice mouse;
    protected final FocusManager focusManager;

    protected CanvasState state;

    protected UITextureRegion whiteTexture;

    protected List<DrawOperation> drawOnTopOperations = Lists.newArrayList();

    protected boolean focusDrawn;

    // Interaction region handling
    protected Deque<InteractionRegion> interactionRegions = Queues.newArrayDeque();
    protected Set<InteractionRegion> mouseOverRegions = Sets.newLinkedHashSet();
    protected InteractionRegion topMouseOverRegion;
    protected float tooltipTime;
    protected Vector2i lastTooltipPosition = new Vector2i();
    protected UITooltip tooltipWidget = new UITooltip();

    protected InteractionRegion clickedRegion;

    // Double click handling
    protected long lastClickTime;
    protected MouseInput lastClickButton;
    protected Vector2i lastClickPosition = new Vector2i();

    protected CanvasRenderer renderer;
    protected float uiScale = 1f;

    protected long gameTime;

    public CanvasImpl(CanvasRenderer renderer, FocusManager focusManager, KeyboardDevice keyboard, MouseDevice mouse,
                      UITextureRegion whiteTexture, UISkin defaultSkin, int uiScale) {
        this.renderer = renderer;
        this.focusManager = focusManager;

        this.keyboard = keyboard;
        this.mouse = mouse;
        this.whiteTexture = whiteTexture;

        this.uiScale = uiScale / 100f;

        CanvasState.DEFAULT_SKIN = defaultSkin;
    }

    public void setGameTime(long milliseconds) {
        this.gameTime = milliseconds;
    }

    protected long getGameTimeInMs() {
        return gameTime;
    }

    protected float getGameTimeInSeconds() {
        return gameTime * 1000;
    }

    @Override
    public void preRender() {
        interactionRegions.clear();
        Vector2i size = renderer.getTargetSize();
        size.x = (int) (size.x / uiScale);
        size.y = (int) (size.y / uiScale);

        state = new CanvasState(null, RectUtility.createFromMinAndSize(0, 0, size.x, size.y));
        renderer.preRender();
        renderer.crop(state.cropRegion);
        focusDrawn = false;

    }

    @Override
    public void postRender() {
        drawOnTopOperations.forEach(DrawOperation::draw);
        drawOnTopOperations.clear();

        if (topMouseOverRegion != null && getGameTimeInSeconds() >= tooltipTime && getSkin() != null) {
            tooltipWidget.setAttachment(topMouseOverRegion.getTooltip());
            drawWidget(tooltipWidget);
        } else {
            tooltipWidget.setAttachment(null);
        }

        renderer.postRender();
        if (!focusDrawn) {
            focusManager.setFocus(null);
        }
    }

    @Override
    public void processMousePosition(Vector2i position) {
        if (clickedRegion != null) {
            Vector2i relPos = new Vector2i(position);
            relPos.sub(clickedRegion.offset);
            clickedRegion.listener.onMouseDrag(new NUIMouseDragEvent(mouse, keyboard, relPos));
        }

        Set<InteractionRegion> newMouseOverRegions = Sets.newLinkedHashSet();
        Iterator<InteractionRegion> iter = interactionRegions.descendingIterator();
        while (iter.hasNext()) {
            InteractionRegion next = iter.next();
            // HACK: There's a bug in JOML where the contains method uses the following faulty logic:
            // (x >= minX && y >= minX && x < maxX && y < maxY) - it should be y >= minY
            // So use a work-around method instead
            if (RectUtility.contains(next.region, position)) {
                Vector2i relPos = new Vector2i(position);
                relPos.sub(next.offset);
                boolean isTopMostElement = newMouseOverRegions.isEmpty();
                next.listener.onMouseOver(new NUIMouseOverEvent(mouse, keyboard, relPos, isTopMostElement));
                newMouseOverRegions.add(next);
            }
        }

        mouseOverRegions.stream().filter(region -> !newMouseOverRegions.contains(region)).forEach(region ->
            region.listener.onMouseLeave());

        if (clickedRegion != null && !interactionRegions.contains(clickedRegion)) {
            clickedRegion = null;
        }

        mouseOverRegions = newMouseOverRegions;

        if (mouseOverRegions.isEmpty()) {
            topMouseOverRegion = null;
        } else {
            InteractionRegion newTopMouseOverRegion = mouseOverRegions.iterator().next();
            if (!newTopMouseOverRegion.equals(topMouseOverRegion)) {
                topMouseOverRegion = newTopMouseOverRegion;
                tooltipTime = getGameTimeInSeconds() + newTopMouseOverRegion.element.getTooltipDelay();
                lastTooltipPosition.set(position);
            } else {
                if (lastTooltipPosition.gridDistance(position) > MAX_DOUBLE_CLICK_DISTANCE) {
                    tooltipTime = getGameTimeInSeconds() + newTopMouseOverRegion.element.getTooltipDelay();
                    lastTooltipPosition.set(position);
                }
            }
        }
    }

    @Override
    public boolean processMouseClick(MouseInput button, Vector2i pos) {
        TabbingManager.focusSetThrough = false;
        TabbingManager.resetCurrentNum();

        boolean possibleDoubleClick = lastClickPosition.gridDistance(pos) < MAX_DOUBLE_CLICK_DISTANCE && lastClickButton == button
            && getGameTimeInMs() - lastClickTime < DOUBLE_CLICK_TIME;
        lastClickPosition.set(pos);
        lastClickButton = button;
        lastClickTime = getGameTimeInMs();

        for (InteractionRegion next : mouseOverRegions) {
            // HACK: There's a bug in JOML where the contains method uses the following faulty logic
            if (RectUtility.contains(next.region, pos)) {
                Vector2i relPos = new Vector2i(pos);
                relPos.sub(next.offset);
                if (possibleDoubleClick && focusManager.getFocus() == next.element) {
                    if (next.listener.onMouseDoubleClick(createDoubleClickEvent(button, relPos))) {
                        clickedRegion = next;
                        return true;
                    }
                } else if (next.listener.onMouseClick(createClickEvent(button, relPos))) {
                    clickedRegion = next;
                    focusManager.setFocus(next.element);
                    return true;
                }
            }
        }
        return false;
    }

    protected NUIMouseClickEvent createClickEvent(MouseInput button, Vector2i relPos) {
        return new NUIMouseClickEvent(mouse, keyboard, relPos, button);
    }

    protected NUIMouseDoubleClickEvent createDoubleClickEvent(MouseInput button, Vector2i relPos) {
        return new NUIMouseDoubleClickEvent(mouse, keyboard, relPos, button);
    }

    @Override
    public boolean processMouseRelease(MouseInput button, Vector2i pos) {
        if (clickedRegion != null) {
            Vector2i relPos = new Vector2i(pos);
            relPos.sub(clickedRegion.region.lengths(new Vector2i()));
            clickedRegion.listener.onMouseRelease(new NUIMouseReleaseEvent(mouse, keyboard, relPos, button));
            clickedRegion = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean processMouseWheel(int wheelTurns, Vector2i pos) {
        for (InteractionRegion next : mouseOverRegions) {
            if (RectUtility.contains(next.region, pos)) {
                Vector2i relPos = new Vector2i(pos);
                relPos.sub(new Vector2i(next.region.minX, next.region.minY));
                if (next.listener.onMouseWheel(new NUIMouseWheelEvent(mouse, keyboard, relPos, wheelTurns))) {
                    clickedRegion = next;
                    focusManager.setFocus(next.element);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the scale factor to scale the UI by, relative to the screen size.
     * < 1 is smaller, > 1 is larger and = 1 is the same as the screen size.
     *
     * @return the current UI scale
     */
    @Override
    public float getUiScale() {
        return uiScale;
    }

    /**
     * Sets the UI scale factor, relative to the screen size
     * < 1 is smaller, > 1 is larger and = 1 is the same as the screen size.
     *
     * @param uiScale the scale factor to use
     */
    @Override
    public void setUiScale(float uiScale) {
        this.uiScale = uiScale;
    }

    @Override
    public SubRegion subRegion(Rectanglei region, boolean crop) {
        return new SubRegionImpl(region, crop);
    }

    @Override
    public void setDrawOnTop(boolean drawOnTop) {
        this.state.drawOnTop = drawOnTop;
    }

    @Override
    public Vector2i size() {
        return new Vector2i(state.drawRegion.lengthX(), state.drawRegion.lengthY());
    }

    @Override
    public Rectanglei getRegion() {
        return RectUtility.createFromMinAndSize(0, 0, state.drawRegion.lengthX(), state.drawRegion.lengthY());
    }

    @Override
    public void setAlpha(float value) {
        state.alpha = value;
    }

    @Override
    public void setSkin(UISkin skin) {
        Preconditions.checkNotNull(skin);
        state.skin = skin;
    }

    @Override
    public UISkin getSkin() {
        return state.skin;
    }

    @Override
    public void setFamily(String familyName) {
        state.family = familyName;
    }

    @Override
    public void setMode(String mode) {
        state.mode = mode;
    }

    @Override
    public void setPart(String part) {
        state.part = part;
    }

    @Override
    public UIStyle getCurrentStyle() {
        return state.getCurrentStyle();
    }

    @Override
    public Vector2i calculatePreferredSize(UIWidget widget) {
        return calculateRestrictedSize(widget, new Vector2i(LARGE_INT, LARGE_INT));
    }

    @Override
    public Vector2i calculateRestrictedSize(UIWidget widget, Vector2i sizeRestrictions) {
        if (widget == null) {
            return sizeRestrictions;
        }

        String family = (widget.getFamily() != null) ? widget.getFamily() : state.family;
        UISkin skin = (widget.getSkin() != null) ? widget.getSkin() : state.skin;
        UIStyle elementStyle = skin.getStyleFor(family, widget.getClass(), UIWidget.BASE_PART, widget.getMode());
        Rectanglei region = applyStyleToSize(RectUtility.createFromMinAndSize(new Vector2i(), sizeRestrictions), elementStyle);
        try (SubRegion ignored = subRegionForWidget(widget, region, false)) {
            Vector2i preferredSize = widget.getPreferredContentSize(this, elementStyle.getMargin().shrink(sizeRestrictions));
            preferredSize = elementStyle.getMargin().grow(preferredSize);
            return applyStyleToSize(preferredSize, elementStyle);
        }
    }

    @Override
    public Vector2i calculateMaximumSize(UIWidget widget) {
        if (widget == null) {
            return new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        String family = (widget.getFamily() != null) ? widget.getFamily() : state.family;
        UIStyle elementStyle = state.skin.getStyleFor(family, widget.getClass(), UIWidget.BASE_PART, widget.getMode());
        try (SubRegion ignored = subRegionForWidget(widget, getRegion(), false)) {
            return applyStyleToSize(elementStyle.getMargin().grow(widget.getMaxContentSize(this)), elementStyle);
        }
    }

    @Override
    public void drawWidget(UIWidget widget) {
        drawWidget(widget, getRegion());
    }

    @Override
    public void drawWidget(UIWidget element, Rectanglei region) {
        if (element == null || !element.isVisible()) {
            return;
        }

        if (focusManager.getFocus() == element) {
            focusDrawn = true;
        }
        String family = (element.getFamily() != null) ? element.getFamily() : state.family;
        UISkin skin = (element.getSkin() != null) ? element.getSkin() : state.skin;
        UIStyle newStyle = skin.getStyleFor(family, element.getClass(), UIWidget.BASE_PART, element.getMode());
        Rectanglei regionArea;
        try (SubRegion ignored = subRegionForWidget(element, region, false)) {
            regionArea = applyStyleToSize(region, newStyle, calculateMaximumSize(element));
        }

        try (SubRegion ignored = subRegionForWidget(element, regionArea, false)) {
            if (element.isSkinAppliedByCanvas()) {
                drawBackground();
                try (SubRegion withMargin = subRegionForWidget(element, newStyle.getMargin().shrink(RectUtility.createFromMinAndSize(new Vector2i(), regionArea.lengths(new Vector2i()))), false)) {
                    drawStyledWidget(element);
                }
            } else {
                drawStyledWidget(element);
            }
        }
    }

    protected void drawStyledWidget(UIWidget element) {
        if (element.getTooltip() != null) {
            // Integrated tooltip support - without this, setting a tooltip value does not make a tooltip work
            // unless an interaction listener is explicitly added by the widget.
            addInteractionRegion(new BaseInteractionListener());
        }
        element.onDraw(this);
    }

    protected SubRegion subRegionForWidget(UIWidget widget, Rectanglei region, boolean crop) {
        SubRegion result = subRegion(region, crop);
        state.element = widget;
        if (widget.getSkin() != null) {
            setSkin(widget.getSkin());
        }
        if (widget.getFamily() != null) {
            setFamily(widget.getFamily());
        }
        setPart(UIWidget.BASE_PART);
        setMode(widget.getMode());
        return result;
    }

    @Override
    public void drawText(String text) {
        drawText(text, state.getRelativeRegion());
    }

    @Override
    public void drawText(String text, Rectanglei region) {
        UIStyle style = getCurrentStyle();
        if (style.isTextShadowed()) {
            drawTextRawShadowed(text, style.getFont(), style.getTextColor(), style.getTextShadowColor(), style.isTextUnderlined(), region, style.getHorizontalTextAlignment(),
                style.getVerticalTextAlignment());
        } else {
            drawTextRaw(text, style.getFont(), style.getTextColor(), style.isTextUnderlined(), region, style.getHorizontalTextAlignment(), style.getVerticalTextAlignment());
        }
    }

    @Override
    public void drawTexture(UITextureRegion texture) {
        drawTexture(texture, state.getRelativeRegion());
    }

    @Override
    public void drawTexture(UITextureRegion texture, Colorc color) {
        drawTexture(texture, state.getRelativeRegion(), color);
    }

    @Override
    public void drawTexture(UITextureRegion texture, Rectanglei region) {
        drawTextureRaw(texture, region, getCurrentStyle().getTextureScaleMode());
    }

    @Override
    public void drawTexture(UITextureRegion texture, Rectanglei region, Colorc color) {
        drawTextureRaw(texture, region, color, getCurrentStyle().getTextureScaleMode());
    }

    @Override
    public void drawBackground() {
        Rectanglei region = applyStyleToSize(getRegion());
        drawBackground(region);
    }

    protected Rectanglei applyStyleToSize(Rectanglei region) {
        return applyStyleToSize(region, getCurrentStyle());
    }

    protected Rectanglei applyStyleToSize(Rectanglei region, UIStyle style, Vector2i maxSize) {
        if (!region.isValid()) {
            return region;
        }
        Vector2i size = applyStyleToSize(region.lengths(new Vector2i()), style);
        size.x = Math.min(size.x, maxSize.x);
        size.y = Math.min(size.y, maxSize.y);

        int minX = region.minX + style.getHorizontalAlignment().getOffset(size.x, region.lengthX());
        int minY = region.minY + style.getVerticalAlignment().getOffset(size.y, region.lengthY());

        return RectUtility.createFromMinAndSize(minX, minY, size.x, size.y);
    }

    protected Vector2i applyStyleToSize(Vector2i size, UIStyle style) {
        Vector2i result = new Vector2i(size);
        if (style.getFixedWidth() != 0) {
            result.x = style.getFixedWidth();
        } else {
            result.x = NUIMathUtil.clamp(result.x, style.getMinWidth(), style.getMaxWidth());
        }

        if (style.getFixedHeight() != 0) {
            result.y = style.getFixedHeight();
        } else {
            result.y = NUIMathUtil.clamp(result.y, style.getMinHeight(), style.getMaxHeight());
        }

        return result;
    }

    protected Rectanglei applyStyleToSize(Rectanglei region, UIStyle style) {
        if (!region.isValid()) {
            return region;
        }
        Vector2i size = applyStyleToSize(region.lengths(new Vector2i()), style);

        int minX = region.minX + style.getHorizontalAlignment().getOffset(size.x, region.lengthX());
        int minY = region.minY + style.getVerticalAlignment().getOffset(size.y, region.lengthY());

        return RectUtility.createFromMinAndSize(minX, minY, size.x, size.y);
    }

    @Override
    public void drawBackground(Rectanglei region) {
        if (!region.isValid()) {
            return;
        }
        UIStyle style = getCurrentStyle();
        if (style.getBackground() != null) {
            if (style.getBackgroundBorder().isEmpty()) {
                drawTextureRaw(style.getBackground(), region, style.getBackgroundScaleMode());
            } else {
                drawTextureRawBordered(style.getBackground(), region, style.getBackgroundBorder(), style.getBackgroundScaleMode() == ScaleMode.TILED);
            }
        }
    }

    @Override
    public void drawTextRaw(String text, Font font, Colorc color) {
        drawTextRawShadowed(text, font, color, Color.transparent);
    }

    @Override
    public void drawTextRaw(String text, Font font, Colorc color, Rectanglei region) {
        drawTextRawShadowed(text, font, color, Color.transparent, region);
    }

    @Override
    public void drawTextRaw(String text, Font font, Colorc color, Rectanglei region, HorizontalAlign hAlign, VerticalAlign vAlign) {
        drawTextRawShadowed(text, font, color, Color.transparent, region, hAlign, vAlign);
    }

    @Override
    public void drawTextRaw(String text, Font font, Colorc color, boolean underlined, Rectanglei region, HorizontalAlign hAlign, VerticalAlign vAlign) {
        drawTextRawShadowed(text, font, color, Color.transparent, underlined, region, hAlign, vAlign);
    }

    @Override
    public void drawTextRawShadowed(String text, Font font, Colorc color, Colorc shadowColor) {
        drawTextRawShadowed(text, font, color, shadowColor, state.drawRegion);
    }

    @Override
    public void drawTextRawShadowed(String text, Font font, Colorc color, Colorc shadowColor, Rectanglei region) {
        drawTextRawShadowed(text, font, color, shadowColor, region, HorizontalAlign.LEFT, VerticalAlign.TOP);
    }

    @Override
    public void drawTextRawShadowed(String text, Font font, Colorc color, Colorc shadowColor, Rectanglei region, HorizontalAlign hAlign, VerticalAlign vAlign) {
        drawTextRawShadowed(text, font, color, shadowColor, false, region, hAlign, vAlign);
    }

    @Override
    public void drawTextRawShadowed(String text, Font font, Colorc color, Colorc shadowColor, boolean underline, Rectanglei region, HorizontalAlign hAlign, VerticalAlign vAlign) {
        Rectanglei absoluteRegion = relativeToAbsolute(region);
        Rectanglei cropRegion = absoluteRegion.intersection(state.cropRegion, new Rectanglei());
        if (cropRegion.isValid()) {
            if (state.drawOnTop) {
                drawOnTopOperations.add(new DrawTextOperation(text, font, hAlign, vAlign, absoluteRegion, cropRegion, color, shadowColor, state.getAlpha(), underline));
            } else {
                renderer.drawText(text, font, hAlign, vAlign, absoluteRegion, color, shadowColor, state.getAlpha(), underline);
            }
        }
    }

    @Override
    public void drawTextureRaw(UITextureRegion texture, Rectanglei region, ScaleMode mode) {
        drawTextureRaw(texture, region, mode, 0f, 0f, 1f, 1f);
    }

    @Override
    public void drawTextureRaw(UITextureRegion texture, Rectanglei region, Colorc color, ScaleMode mode) {
        drawTextureRaw(texture, region, color, mode, 0f, 0f, 1f, 1f);
    }

    @Override
    public void drawTextureRaw(UITextureRegion texture, Rectanglei region, ScaleMode mode, int ux, int uy, int uw, int uh) {
        drawTextureRaw(texture, region, mode,
            (float) ux / texture.getWidth(), (float) uy / texture.getHeight(),
            (float) uw / texture.getWidth(), (float) uh / texture.getHeight());
    }

    @Override
    public void drawTextureRaw(UITextureRegion texture, Rectanglei region, ScaleMode mode, float ux, float uy, float uw, float uh) {
        drawTextureRaw(texture, region, Color.WHITE, mode, ux, uy, uw, uh);
    }

    @Override
    public void drawTextureRaw(UITextureRegion texture, Rectanglei region, Colorc color, ScaleMode mode, float ux, float uy, float uw, float uh) {
        if (!state.cropRegion.intersectsRectangle(relativeToAbsolute(region))) {
            return;
        }
        Rectanglei absoluteRegion = relativeToAbsolute(region);
        Rectanglei cropRegion = absoluteRegion.intersection(state.cropRegion, new Rectanglei());
        if (cropRegion.isValid()) {
            if (state.drawOnTop) {
                drawOnTopOperations.add(new DrawTextureOperation(texture, color, mode, absoluteRegion, cropRegion, ux, uy, uw, uh, state.getAlpha()));
            } else {
                renderer.drawTexture(texture, color, mode, absoluteRegion, ux, uy, uw, uh, state.getAlpha());
            }
        }
    }

    @Override
    public void drawTextureRawBordered(UITextureRegion texture, Rectanglei region, Border border, boolean tile) {
        drawTextureRawBordered(texture, region, border, tile, 0f, 0f, 1f, 1f);
    }

    @Override
    public void drawTextureRawBordered(UITextureRegion texture, Rectanglei region, Border border, boolean tile, int ux, int uy, int uw, int uh) {
        drawTextureRawBordered(texture, region, border, tile,
            (float) ux / texture.getWidth(), (float) uy / texture.getHeight(),
            (float) uw / texture.getWidth(), (float) uh / texture.getHeight());
    }

    @Override
    public void drawTextureRawBordered(UITextureRegion texture, Rectanglei region, Border border, boolean tile, float ux, float uy, float uw, float uh) {
        if (!state.cropRegion.intersectsRectangle(relativeToAbsolute(region))) {
            return;
        }
        Rectanglei absoluteRegion = relativeToAbsolute(region);
        Rectanglei cropRegion = absoluteRegion.intersection(state.cropRegion, new Rectanglei());
        if (cropRegion.isValid()) {
            if (state.drawOnTop) {
                drawOnTopOperations.add(new DrawBorderedTextureOperation(texture, absoluteRegion, border, tile, cropRegion, ux, uy, uw, uh, state.getAlpha()));
            } else {
                renderer.drawTextureBordered(texture, absoluteRegion, border, tile, ux, uy, uw, uh, state.getAlpha());
            }
        }
    }

    @Override
    public void addInteractionRegion(InteractionListener listener) {
        addInteractionRegion(listener, (UIWidget) null, getCurrentStyle().getMargin().grow(applyStyleToSize(getRegion())));
    }

    @Override
    public void addInteractionRegion(InteractionListener listener, String tooltip) {
        addInteractionRegion(listener, tooltip, getCurrentStyle().getMargin().grow(applyStyleToSize(getRegion())));
    }

    @Override
    public void addInteractionRegion(InteractionListener listener, String tooltip, Rectanglei region) {
        UIWidget tooltipLabelWidget = (tooltip == null || tooltip.isEmpty()) ? null : new UILabel(tooltip);
        addInteractionRegion(listener, tooltipLabelWidget, region);
    }

    @Override
    public void addInteractionRegion(InteractionListener listener, Rectanglei region) {
        addInteractionRegion(listener, (UIWidget) null, region);
    }

    @Override
    public void addInteractionRegion(InteractionListener listener, UIWidget tooltip) {
        addInteractionRegion(listener, tooltip, getCurrentStyle().getMargin().grow(applyStyleToSize(getRegion())));
    }

    @Override
    public void addInteractionRegion(InteractionListener listener, UIWidget tooltip, Rectanglei region) {
        Vector2i offset = new Vector2i(state.drawRegion.minX, state.drawRegion.minY);
        Rectanglei finalRegion = state.cropRegion.intersection(relativeToAbsolute(region), new Rectanglei());
        if (finalRegion.isValid()) {
            listener.setFocusManager(focusManager);
            if (state.drawOnTop) {
                drawOnTopOperations.add(new DrawInteractionRegionOperation(finalRegion, offset, listener, state.element, tooltip));
            } else {
                interactionRegions.addLast(new InteractionRegion(finalRegion, offset, listener, state.element, tooltip));
            }
        }
    }

    @Override
    public void drawLine(int startX, int startY, int endX, int endY, Colorc color) {
        Line.LineCoordinates lc = Line.getLineCoordinates(startX, startY, endX, endY, state.drawRegion, state.cropRegion);

        if (lc != null) {
            if (state.drawOnTop) {
                drawOnTopOperations.add(new DrawLineOperation(lc.getStart().x, lc.getStart().y, lc.getEnd().x, lc.getEnd().y, color));
            } else {
                renderer.drawLine(lc.getStart().x, lc.getStart().y, lc.getEnd().x, lc.getEnd().y, color);
            }
        }
    }

    @Override
    public void drawFilledRectangle(Rectanglei region, Colorc color) {
        drawTextureRaw(whiteTexture, region, color, ScaleMode.STRETCH);

    }

    protected Rectanglei relativeToAbsolute(Rectanglei region) {
        return Line.relativeToAbsolute(region, state.drawRegion);
    }

    /**
     * The state of the canvas
     */
    protected static class CanvasState {
        private static UISkin DEFAULT_SKIN;
        public UISkin skin = DEFAULT_SKIN;
        public String family = "";
        public UIWidget element;
        public String part = "";
        public String mode = "";

        public Rectanglei drawRegion;
        public Rectanglei cropRegion;

        private float alpha = 1.0f;
        private float baseAlpha = 1.0f;

        private boolean drawOnTop;

        public CanvasState(CanvasState previous, Rectanglei drawRegion) {
            this(previous, drawRegion, (previous != null) ? previous.cropRegion : drawRegion);
        }

        public CanvasState(CanvasState previous, Rectanglei drawRegion, Rectanglei cropRegion) {
            if (previous != null) {
                this.skin = previous.skin;
                this.family = previous.family;
                this.element = previous.element;
                this.part = previous.part;
                this.mode = previous.mode;
                this.drawOnTop = previous.drawOnTop;
                baseAlpha = previous.getAlpha();
            }
            this.drawRegion = drawRegion;
            this.cropRegion = cropRegion;
        }

        public float getAlpha() {
            return alpha * baseAlpha;
        }

        public UIStyle getCurrentStyle() {
            return skin.getStyleFor(family, element.getClass(), part, mode);
        }

        public Rectanglei getRelativeRegion() {
            return RectUtility.createFromMinAndSize(0, 0, drawRegion.lengthX(), drawRegion.lengthY());
        }
    }

    /**
     * A SubRegion implementation for this canvas.
     */
    protected class SubRegionImpl implements SubRegion {

        public boolean croppingRegion;
        private final CanvasState previousState;
        private boolean disposed;

        public SubRegionImpl(Rectanglei region, boolean crop) {
            previousState = state;

            int left = NUIMathUtil.addClampAtMax(region.minX, state.drawRegion.minX);
            int right = NUIMathUtil.addClampAtMax(region.maxX, state.drawRegion.minX);
            int top = NUIMathUtil.addClampAtMax(region.minY, state.drawRegion.minY);
            int bottom = NUIMathUtil.addClampAtMax(region.maxY, state.drawRegion.minY);
            Rectanglei subRegion = new Rectanglei(left, top, right, bottom);
            if (crop) {
                Rectanglei cropRegion = subRegion.intersection(state.cropRegion, new Rectanglei());
                if (!cropRegion.isValid()) {
                    state = new CanvasState(state, subRegion, cropRegion);
                } else if (!cropRegion.equals(state.cropRegion)) {
                    state = new CanvasState(state, subRegion, cropRegion);
                    renderer.crop(cropRegion);
                    croppingRegion = true;
                } else {
                    state = new CanvasState(state, subRegion);
                }
            } else {
                state = new CanvasState(state, subRegion);
            }
        }

        @Override
        public void close() {
            if (!disposed) {
                disposed = true;
                if (croppingRegion) {
                    renderer.crop(previousState.cropRegion);
                }
                state = previousState;
            }
        }
    }

    protected static class InteractionRegion {
        public InteractionListener listener;
        public Rectanglei region;
        public Vector2i offset;
        public UIWidget element;
        public UIWidget tooltipOverride;

        public InteractionRegion(Rectanglei region, Vector2i offset, InteractionListener listener, UIWidget element, UIWidget tooltipOverride) {
            this.listener = listener;
            this.region = region;
            this.offset = offset;
            this.element = element;
            this.tooltipOverride = tooltipOverride;
        }

        public UIWidget getTooltip() {
            if (tooltipOverride == null) {
                return element.getTooltip();
            }
            return tooltipOverride;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof InteractionRegion) {
                InteractionRegion other = (InteractionRegion) obj;
                return Objects.equals(other.listener, listener);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return listener.hashCode();
        }
    }

    protected interface DrawOperation {
        void draw();
    }

    protected final class DrawTextureOperation implements DrawOperation {

        private final Colorc color;
        private final ScaleMode mode;
        private final UITextureRegion texture;
        private final Rectanglei absoluteRegion;
        private final Rectanglei cropRegion;
        private final float ux;
        private final float uy;
        private final float uw;
        private final float uh;
        private final float alpha;

        public DrawTextureOperation(UITextureRegion texture, Colorc color, ScaleMode mode, Rectanglei absoluteRegion,
                                    Rectanglei cropRegion, float ux, float uy, float uw, float uh, float alpha) {
            this.color = color;
            this.mode = mode;
            this.texture = texture;
            this.absoluteRegion = absoluteRegion;
            this.cropRegion = cropRegion;
            this.ux = ux;
            this.uy = uy;
            this.uw = uw;
            this.uh = uh;
            this.alpha = alpha;
        }

        @Override
        public void draw() {
            renderer.crop(cropRegion);
            renderer.drawTexture(texture, color, mode, absoluteRegion, ux, uy, uw, uh, alpha);
            renderer.crop(state.cropRegion);
        }
    }

    protected final class DrawBorderedTextureOperation implements DrawOperation {

        private final UITextureRegion texture;
        private final Border border;
        private final boolean tile;
        private final Rectanglei absoluteRegion;
        private final Rectanglei cropRegion;
        private final float ux;
        private final float uy;
        private final float uw;
        private final float uh;
        private final float alpha;

        public DrawBorderedTextureOperation(UITextureRegion texture, Rectanglei absoluteRegion, Border border,
                                            boolean tile,
                                            Rectanglei cropRegion, float ux, float uy, float uw, float uh,
                                            float alpha) {
            this.texture = texture;
            this.tile = tile;
            this.absoluteRegion = absoluteRegion;
            this.border = border;
            this.cropRegion = cropRegion;
            this.ux = ux;
            this.uy = uy;
            this.uw = uw;
            this.uh = uh;
            this.alpha = alpha;
        }

        @Override
        public void draw() {
            renderer.crop(cropRegion);
            renderer.drawTextureBordered(texture, absoluteRegion, border, tile, ux, uy, uw, uh, alpha);
            renderer.crop(state.cropRegion);
        }
    }

    protected final class DrawLineOperation implements DrawOperation {

        private final int x0;
        private final int y0;
        private final int x1;
        private final int y1;
        private final Colorc color;

        public DrawLineOperation(int x0, int y0, int x1, int y1, Colorc color) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            this.color = color;
        }

        @Override
        public void draw() {
            renderer.drawLine(x0, y0, x1, y1, color);
        }
    }

    protected final class DrawTextOperation implements DrawOperation {
        private final String text;
        private final Font font;
        private final Rectanglei absoluteRegion;
        private final HorizontalAlign hAlign;
        private final VerticalAlign vAlign;
        private final Rectanglei cropRegion;
        private final Colorc shadowColor;
        private final Colorc color;
        private final float alpha;
        private final boolean underline;

        public DrawTextOperation(String text, Font font, HorizontalAlign hAlign, VerticalAlign vAlign, Rectanglei absoluteRegion, Rectanglei cropRegion,
                                  Colorc color, Colorc shadowColor, float alpha, boolean underline) {
            this.text = text;
            this.font = font;
            this.absoluteRegion = absoluteRegion;
            this.hAlign = hAlign;
            this.vAlign = vAlign;
            this.cropRegion = cropRegion;
            this.shadowColor = shadowColor;
            this.color = color;
            this.alpha = alpha;
            this.underline = underline;
        }

        @Override
        public void draw() {
            renderer.crop(cropRegion);
            renderer.drawText(text, font, hAlign, vAlign, absoluteRegion, color, shadowColor, alpha, underline);
            renderer.crop(state.cropRegion);
        }
    }

    protected final class DrawInteractionRegionOperation implements DrawOperation {

        private final Vector2i offset;
        private final Rectanglei region;
        private final InteractionListener listener;
        private final UIWidget currentElement;
        private final UIWidget tooltipOverride;

        public DrawInteractionRegionOperation(Rectanglei region, Vector2i offset, InteractionListener listener, UIWidget currentElement, UIWidget tooltipOverride) {
            this.region = region;
            this.listener = listener;
            this.offset = offset;
            this.currentElement = currentElement;
            this.tooltipOverride = tooltipOverride;
        }

        @Override
        public void draw() {
            interactionRegions.addLast(new InteractionRegion(region, offset, listener, currentElement, tooltipOverride));
        }
    }

}
